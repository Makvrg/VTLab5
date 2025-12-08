package ru.ifmo.se.io.input;

import ru.ifmo.se.entity.City;
import ru.ifmo.se.event.ShutdownListener;
import ru.ifmo.se.io.input.dto.json.CityFromJsonDto;
import ru.ifmo.se.io.input.env.EnvironmentProvider;
import ru.ifmo.se.io.input.json.JsonParser;
import ru.ifmo.se.io.input.json.JsonValidationException;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.input.readers.file.InputStreamProvider;
import ru.ifmo.se.io.input.readers.InputTextHandler;
import ru.ifmo.se.io.output.Printer;
import ru.ifmo.se.io.output.dto.CityForJsonDto;
import ru.ifmo.se.io.output.json.JsonWriter;
import ru.ifmo.se.service.CollectionService;
import ru.ifmo.se.service.exceptions.CreationDateIsAfterNowException;
import ru.ifmo.se.service.exceptions.NonUniqueIdException;
import ru.ifmo.se.typer.DataTyper;
import ru.ifmo.se.validator.CommandValidator;
import ru.ifmo.se.validator.exceptions.InputFieldValidationException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Runnable;
import java.util.ArrayList;
import java.util.List;

public class CommandInput implements Runnable, ShutdownListener {

    private final List<Reader> readers = new ArrayList<>();
    private final Printer printer;
    private final CollectionService collectionService;
    private final CommandValidator commandValidator;
    private final DataTyper dataTyper;
    private final EnvironmentProvider environmentProvider;
    private final JsonWriter<List<CityForJsonDto>> fileWriter;
    private final InputStreamProvider inputStreamProvider;
    private final JsonParser<List<CityFromJsonDto>> initCitiesParser;
    private boolean shutdown = false;

    public CommandInput(Reader reader,
                        Printer printer,
                        CollectionService collectionService,
                        CommandValidator commandValidator,
                        DataTyper dataTyper,
                        EnvironmentProvider environmentProvider,
                        JsonWriter<List<CityForJsonDto>> fileWriter,
                        InputStreamProvider inputStreamProvider,
                        JsonParser<List<CityFromJsonDto>> initCitiesParser) {
        this.readers.add(reader);
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

        CommandInvoker commandInvoker =
                new CommandInvoker(
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
                String inputLine = readers.get(readers.size() - 1).readLine();

                if (inputLine == null) {
                    readers.remove(readers.size() - 1);
                    printer.forcePrintln("Активен режим чтения предыдущего источника");

                    if (readers.size() == 1) {
                        printer.on();
                    }
                    continue;
                }

                String[] inputArgs = InputTextHandler.parseArguments(
                        inputLine
                );
                commandInvoker.distribute(inputArgs);
            } catch (IOException e) {
                printer.forcePrintln(e.getMessage());
                printer.forcePrintln(
                        "Файл с указанным названием не найден или к нему нет доступа"
                );
                readers.remove(readers.size() - 1);
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
