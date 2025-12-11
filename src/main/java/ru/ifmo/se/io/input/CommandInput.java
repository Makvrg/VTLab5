package ru.ifmo.se.io.input;

import ru.ifmo.se.entity.City;
import ru.ifmo.se.event.ShutdownListener;
import ru.ifmo.se.io.input.env.EnvironmentProvider;
import ru.ifmo.se.io.input.json.CityJsonParser;
import ru.ifmo.se.io.input.json.JsonValidationException;
import ru.ifmo.se.io.input.readers.InputTextHandler;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.input.readers.file.InputStreamProvider;
import ru.ifmo.se.io.output.formatter.OutputStringFormatter;
import ru.ifmo.se.io.output.json.CityJsonWriter;
import ru.ifmo.se.io.output.print.Messages;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.service.CollectionService;
import ru.ifmo.se.service.exceptions.CreationDateIsAfterNowException;
import ru.ifmo.se.service.exceptions.NonUniqueIdException;
import ru.ifmo.se.typer.DataTyper;
import ru.ifmo.se.validator.CommandValidator;
import ru.ifmo.se.validator.exceptions.InputFieldValidationException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandInput implements Runnable, ShutdownListener {

    private final List<Reader> readers = new ArrayList<>();
    private final Printer printer;
    private final CollectionService collectionService;
    private final CommandValidator commandValidator;
    private final EnvironmentProvider environmentProvider;
    private final InputStreamProvider inputStreamProvider;
    private final CityJsonParser initCitiesParser;
    private final CommandInvoker commandInvoker;
    private boolean shutdown = false;

    public CommandInput(Reader reader,
                        Printer printer,
                        CollectionService collectionService,
                        CommandValidator commandValidator,
                        DataTyper dataTyper,
                        OutputStringFormatter formatter,
                        EnvironmentProvider environmentProvider,
                        CityJsonWriter fileWriter,
                        InputStreamProvider inputStreamProvider,
                        CityJsonParser initCitiesParser) {
        this.readers.add(reader);
        this.printer = printer;
        this.collectionService = collectionService;
        this.commandValidator = commandValidator;
        this.environmentProvider = environmentProvider;
        this.inputStreamProvider = inputStreamProvider;
        this.initCitiesParser = initCitiesParser;
        this.commandInvoker =
                new CommandInvoker(
                        collectionService,
                        commandValidator,
                        dataTyper,
                        readers,
                        formatter,
                        printer,
                        environmentProvider,
                        fileWriter
                );
    }

    @Override
    public void run() {
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

    public void initialize() throws IOException {
        printer.on();
        printer.forcePrintln("Приложение запускается");

        String fileName = environmentProvider.getFileName();

        if (fileName == null) {
            printer.forcePrintln("Не найдена переменная окружения с названием файла");
            return;
        }
        try (InputStreamReader reader = inputStreamProvider.open(fileName)) {
            List<City> cities;
            try {
                cities = initCitiesParser.parse(reader);
            } catch (JsonValidationException e) {
                printer.forcePrintln("Произошла ошибка инициализации коллекции:");
                printer.forcePrintln(e.getMessage());
                return;
            }

            if (checkCitiesFromFile(cities)) {
                if (addAllCitiesFromFile(cities)) {
                    printer.forcePrintln(
                            "Инициализация коллекции объектами City из файла завершена успешно");
                } else {
                    collectionService.clear();
                }
            }
        }
    }

    private boolean checkCitiesFromFile(List<City> cities) {
        int counter = 1;
        for (City city : cities) {

            try {
                commandValidator.validateTypedIdInput(city.getId());
                commandValidator.validateNameInput(city.getName());
                commandValidator.validateTypedCreationDateInput(
                        city.getCreationDate());
                commandValidator.validateTypedXCoordInput(
                        city.getCoordinates().getX()
                );
                commandValidator.validateTypedYCoordInput(
                        city.getCoordinates().getY()
                );
                commandValidator.validateTypedAreaInput(city.getArea());
                commandValidator.validateTypedPopulationInput(city.getPopulation());
                commandValidator.validateTypedMetersAboveSeaLevelInput(
                        city.getMetersAboveSeaLevel()
                );
                commandValidator.validateTypedPopulationDensityInput(
                        city.getPopulationDensity()
                );
                commandValidator.validateTypedGovernmentInput(city.getGovernment());
                commandValidator.validateTypedHeightInput(
                        city.getGovernor().getHeight()
                );
            } catch (InputFieldValidationException e) {
                printer.forcePrintln(
                        String.format(Messages.CITY_INIT_VALID_EXC,
                                city.getId(), counter)
                );
                printer.forcePrintln("Выявленная в нём ошибка: " + e.getMessage());
                return false;
            }
        }
        return true;
    }

    private boolean addAllCitiesFromFile(List<City> cities) {
        int counter = 1;
        for (City city : cities) {
            try {
                if (collectionService.addInitCity(city)) {
                    counter++;
                } else {
                    printer.forcePrintln(
                            String.format(Messages.CITY_INIT_UNKNOWN_EXC,
                                    city.getId(), counter)
                    );
                    return false;
                }
            } catch (NonUniqueIdException | CreationDateIsAfterNowException e) {
                printer.forcePrintln(
                        String.format(Messages.CITY_INIT_ADD_EXC, city.getId(), counter)
                );
                printer.forcePrintln("Ошибка добавления в коллекцию: " + e.getMessage());
                return false;
            }
        }
        return true;
    }

    @Override
    public void onShutdown() {
        shutdown = true;
    }

}
