package com.example.input;

import com.example.CityValidationException;
import com.example.entity.City;
import com.example.event.IShutdownListener;
import com.example.input.dto.CityRawRequestDto;
import com.example.input.dto.CityTypedRequestDto;
import com.example.input.env.EnvironmentProvider;
import com.example.input.json.JsonParser;
import com.example.input.readers.IReader;
import com.example.input.readers.file.InputStreamProvider;
import com.example.input.readers.terminal.Processor;
import com.example.output.IPrinter;
import com.example.service.CollectionService;
import com.example.typer.DataTyper;
import com.example.validator.CommandValidator;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CollectionInput implements IRunnable, IShutdownListener {

    private final List<IReader> readers = new ArrayList<>();
    private final IPrinter printer;
    private final CollectionService collectionService;
    private final CommandValidator commandValidator;
    private final DataTyper dataTyper;
    private final EnvironmentProvider environmentProvider;
    private final InputStreamProvider inputStreamProvider;
    private final JsonParser<List<CityRawRequestDto>> parser;
    private boolean shutdown = false;

    public CollectionInput(IReader reader,
                           IPrinter printer,
                           CollectionService collectionService,
                           CommandValidator commandValidator,
                           DataTyper dataTyper,
                           EnvironmentProvider environmentProvider,
                           InputStreamProvider inputStreamProvider,
                           JsonParser<List<CityRawRequestDto>> parser) {
        this.readers.addLast(reader);
        this.printer = printer;
        this.collectionService = collectionService;
        this.commandValidator = commandValidator;
        this.dataTyper = dataTyper;
        this.environmentProvider = environmentProvider;
        this.inputStreamProvider = inputStreamProvider;
        this.parser = parser;
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
            collectionService.exit();
        }

        CommandDistributor commandDistributor =
                new CommandDistributor(
                        collectionService,
                        commandValidator,
                        dataTyper,
                        readers,
                        printer
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
            collectionService.exit();
            return;
        }
        try (InputStreamReader reader = inputStreamProvider.open(fileName)) {
            List<CityRawRequestDto> cities = parser.parse(reader);
            int counter = 1;
            for (CityRawRequestDto cityRawRequestDto : cities) {
                try {
                    CityTypedRequestDto cityTypedRequestDto =
                            dataTyper.typifyCityRawRequestDto(cityRawRequestDto);
                    commandValidator.validateCityTypedRequestDto(cityTypedRequestDto);

                    City city = dataTyper.typifyCityTypedRequestDtoToCity(cityTypedRequestDto);

                    collectionService.add(city);
                } catch (CityValidationException e) {
                    printer.forcePrintln(
                            "При инициализации коллекции данными из файла "
                            + "произошла ошибка валидации объекта City с "
                            + "порядковым номером: " + counter
                    );
                    printer.forcePrintln("Ошибки валидации в нём:");
                    e.getErrorsWithMessages()
                     .values()
                     .forEach(printer::forcePrintln);
                    collectionService.exit();
                    return;
                }
                counter++;
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
