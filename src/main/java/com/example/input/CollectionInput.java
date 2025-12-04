package com.example.input;

import com.example.entity.City;
import com.example.event.IShutdownListener;
import com.example.input.dto.json.CityFromJsonDto;
import com.example.input.env.IEnvironmentProvider;
import com.example.input.json.IJsonParser;
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
                    "Произошла ошибка инициализации коллекции: "
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
            List<CityFromJsonDto> cities = initCitiesParser.parse(reader);
            List<Consumer<String>> inputValidateMethods = buildInputValidateMethods();
            int counter = 1;
            for (CityFromJsonDto cityFromJsonDto : cities) {

                try {
                    inputValidateMethods.get(0).accept(cityFromJsonDto.getId());
                    inputValidateMethods.get(1).accept(cityFromJsonDto.getName());
                    inputValidateMethods.get(2).accept(cityFromJsonDto.getCoordinates().getX());
                    inputValidateMethods.get(3).accept(cityFromJsonDto.getCoordinates().getY());
                    inputValidateMethods.get(4).accept(cityFromJsonDto.getCreationDate());
                    inputValidateMethods.get(5).accept(cityFromJsonDto.getArea());
                    inputValidateMethods.get(6).accept(cityFromJsonDto.getPopulation());
                    inputValidateMethods.get(7).accept(cityFromJsonDto.getMetersAboveSeaLevel());
                    inputValidateMethods.get(8).accept(cityFromJsonDto.getPopulationDensity());
                    inputValidateMethods.get(9).accept(cityFromJsonDto.getAgglomeration());
                    inputValidateMethods.get(10).accept(cityFromJsonDto.getGovernment());
                    inputValidateMethods.get(11).accept(cityFromJsonDto.getGovernor().getHeight());
                    inputValidateMethods.get(12).accept(cityFromJsonDto.getGovernor().getBirthday());
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
                City city = dataTyper.typifyCityFromJsonDtoToCity(cityFromJsonDto);

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

    private List<Consumer<String>> buildInputValidateMethods() {
        List<Consumer<String>> inputValidateMethods = new ArrayList<>();

        inputValidateMethods.add(commandValidator::validateIdInput);
        inputValidateMethods.add(commandValidator::validateNameInput);
        inputValidateMethods.add(commandValidator::validateXCoordInput);
        inputValidateMethods.add(commandValidator::validateYCoordInput);
        inputValidateMethods.add(commandValidator::validateCreationDateInput);
        inputValidateMethods.add(commandValidator::validateAreaInput);
        inputValidateMethods.add(commandValidator::validatePopulationInput);
        inputValidateMethods.add(commandValidator::validateMetersAboveSeaLevelInput);
        inputValidateMethods.add(commandValidator::validatePopulationDensityInput);
        inputValidateMethods.add(commandValidator::validateAgglomerationInput);
        inputValidateMethods.add(commandValidator::validateGovernmentInput);
        inputValidateMethods.add(commandValidator::validateHeightInput);
        inputValidateMethods.add(commandValidator::validateBirthdayInput);

        return inputValidateMethods;
    }

    @Override
    public void onShutdown() {
        shutdown = true;
    }

}
