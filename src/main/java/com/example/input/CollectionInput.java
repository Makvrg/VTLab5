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
        System.out.println("Приложение запускается");
        printer.on();

        try {
            initialize();
        } catch (IOException e) {
            System.out.println(
                    "Произошла ошибка инициализации коллекции: "
                    + e.getMessage());
            collectionService.exit();
        }

        CommandDistributor commandDistributor =
                new CommandDistributor(
                        collectionService,
                        commandValidator,
                        dataTyper,
                        readers
                );

        while (!shutdown) {
            System.out.print("> ");
            try {
                String inputLine = readers.getLast().read();

                if (inputLine == null) {
                    readers.removeLast();
                    System.out.println("Активен режим чтения предыдущего источника");
                    continue;
                }

                String[] inputArgs = Processor.processTerminalCommand(
                        inputLine
                );
                commandDistributor.distribute(inputArgs);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println(
                        "Файл с указанным названием не найден или к нему нет доступа"
                );
                readers.removeLast();
                System.out.println("Активен режим предыдущего источника");
            }
        }
    }

    private void initialize() throws IOException {
        String fileName = environmentProvider.getFileName();

        if (fileName == null) {
            System.out.println(
                    "Не найдена переменная окружения с названием файла");
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
                    System.out.println(
                            "При инициализации коллекции данными из файла "
                            + "произошла ошибка валидации объекта City с "
                            + "порядковым номером: " + counter
                    );
                    System.out.println("Ошибки валидации в нём:");
                    e.getErrorsWithMessages()
                     .values()
                     .forEach(System.out::println);
                    collectionService.exit();
                    return;
                }
                counter++;
            }
            System.out.println(
                    "Инициализация коллекции объектами City из файла завершена успешно");
        }
    }

    @Override
    public void onShutdown() {
        shutdown = true;
    }

}
