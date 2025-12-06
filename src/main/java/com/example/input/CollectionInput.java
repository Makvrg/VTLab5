package com.example.input;

import com.example.entity.City;
import com.example.entity.Coordinates;
import com.example.entity.Human;
import com.example.event.IShutdownListener;
import com.example.input.dto.json.CityFromJsonDto;
import com.example.input.env.IEnvironmentProvider;
import com.example.input.json.IJsonParser;
import com.example.input.json.JsonValidationException;
import com.example.input.readers.IReader;
import com.example.input.readers.file.IInputStreamProvider;
import com.example.input.readers.terminal.Processor;
import com.example.output.IPrinter;
import com.example.output.dto.CityForJsonDto;
import com.example.output.json.IJsonWriter;
import com.example.service.CollectionService;
import com.example.service.exceptions.CreationDateIsAfterNowException;
import com.example.service.exceptions.NonUniqueIdException;
import com.example.typer.DataTyper;
import com.example.validator.CommandValidator;
import com.example.validator.exceptions.InputFieldValidationException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CollectionInput implements IRunnable, IShutdownListener {

    private final List<IReader> readers = new ArrayList<>();
    private final IPrinter printer;
    private final CollectionService collectionService;
    private final CommandValidator commandValidator;
    private final DataTyper dataTyper;
    private final IEnvironmentProvider environmentProvider;
    private final IJsonWriter<List<CityForJsonDto>> fileWriter;
    private final IInputStreamProvider inputStreamProvider;
    private final IJsonParser<List<CityFromJsonDto>> initCitiesParser;
    private boolean shutdown = false;

    public CollectionInput(IReader reader,
                           IPrinter printer,
                           CollectionService collectionService,
                           CommandValidator commandValidator,
                           DataTyper dataTyper,
                           IEnvironmentProvider environmentProvider,
                           IJsonWriter<List<CityForJsonDto>> fileWriter,
                           IInputStreamProvider inputStreamProvider,
                           IJsonParser<List<CityFromJsonDto>> initCitiesParser) {
        this.readers.addLast(reader);
        this.printer = printer;
        this.collectionService = collectionService;
        this.commandValidator = commandValidator;
        this.dataTyper = dataTyper;
        this.environmentProvider = environmentProvider;
        this.fileWriter = fileWriter;
        this.inputStreamProvider = inputStreamProvider;
        this.initCitiesParser = initCitiesParser;
    }

    @Override
    public void run() {
        printer.on();
        printer.forcePrintln("Приложение запускается");

        try {
            initialize();
        } catch (IOException e) {
            printer.forcePrintln(
                    "Произошла ошибка открытия файла при инициализации коллекции: "
                    + e.getMessage());
        }

        CommandDistributor commandDistributor =
                new CommandDistributor(
                        collectionService,
                        commandValidator,
                        dataTyper,
                        readers,
                        printer,
                        environmentProvider,
                        fileWriter
                );

        while (!shutdown) {
            printer.printIfOn("> ");
            try {
                String inputLine = readers.getLast().read();

                if (inputLine == null) {
                    readers.removeLast();
                    printer.forcePrintln("Активен режим чтения предыдущего источника");

                    if (readers.size() == 1) {
                        printer.on();
                    }
                    continue;
                }

                String[] inputArgs = Processor.processTerminalCommand(
                        inputLine
                );
                commandDistributor.distribute(inputArgs);
            } catch (IOException e) {
                printer.forcePrintln(e.getMessage());
                printer.forcePrintln(
                        "Файл с указанным названием не найден или к нему нет доступа"
                );
                readers.removeLast();
                printer.forcePrintln("Активен режим чтения предыдущего источника");

                if (readers.size() == 1) {
                    printer.on();
                }

            }
        }
    }

    private void initialize() throws IOException {
        String fileName = environmentProvider.getFileName();

        if (fileName == null) {
            printer.forcePrintln("Не найдена переменная окружения с названием файла");
            return;
        }
        try (InputStreamReader reader = inputStreamProvider.open(fileName)) {
            List<CityFromJsonDto> cities;
            try {
                cities = initCitiesParser.parse(reader);
            } catch (JsonValidationException e) {
                printer.forcePrintln("Произошла ошибка инициализации коллекции:");
                printer.forcePrintln(e.getMessage());
                return;
            }
            int counter = 1;
            for (CityFromJsonDto cityFromJsonDto : cities) {

                try {
                    commandValidator.validateTypedIdInput(
                            cityFromJsonDto.getId()
                    );
                    commandValidator.validateTypedXCoordInput(
                            cityFromJsonDto.getCoordinates().getX()
                    );
                    commandValidator.validateTypedYCoordInput(
                            cityFromJsonDto.getCoordinates().getY()
                    );
                    commandValidator.validateTypedAreaInput(
                            cityFromJsonDto.getArea()
                    );
                    commandValidator.validateTypedPopulationInput(
                            cityFromJsonDto.getPopulation()
                    );
                    commandValidator.validateTypedMetersAboveSeaLevelInput(
                            cityFromJsonDto.getMetersAboveSeaLevel()
                    );
                    commandValidator.validateTypedPopulationDensityInput(
                            cityFromJsonDto.getPopulationDensity()
                    );
                    commandValidator.validateTypedHeightInput(
                            cityFromJsonDto.getGovernor().getHeight()
                    );
                } catch (InputFieldValidationException e) {
                    printer.forcePrintln(
                            new StringBuilder()
                                    .append("При инициализации коллекции данными из файла ")
                                    .append("произошла ошибка валидации объекта City с ")
                                    .append("порядковым номером: ")
                                    .append(counter)
                                    .toString()
                    );
                    printer.forcePrintln("Выявленная в нём ошибка: " + e.getMessage());
                    return;
                }
                City city = new City(cityFromJsonDto);

                try {
                    if (collectionService.initializationAdd(city)) {
                        counter++;
                    } else {
                        printer.forcePrintln(
                                new StringBuilder()
                                        .append("При инициализации коллекции данными из файла ")
                                        .append("по неизвестной причине не удалось ")
                                        .append("добавить в коллекцию объект City с ")
                                        .append("порядковым номером: ")
                                        .append(counter)
                                        .toString()
                        );
                        return;
                    }
                } catch (NonUniqueIdException | CreationDateIsAfterNowException e) {
                    printer.forcePrintln(
                            new StringBuilder()
                                    .append("При инициализации коллекции данными из файла ")
                                    .append("произошла ошибка добавления объекта City с ")
                                    .append("порядковым номером: ")
                                    .append(counter)
                                    .toString()
                    );
                    printer.forcePrintln("Ошибка добавления в коллекцию: " + e.getMessage());
                    return;
                }
            }
            printer.forcePrintln(
                    "Инициализация коллекции объектами City из файла завершена успешно");
        }
    }

    @Override
    public void onShutdown() {
        shutdown = true;
    }

}
