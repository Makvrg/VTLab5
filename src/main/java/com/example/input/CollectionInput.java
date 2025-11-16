package com.example.input;

import com.example.CityValidationException;
import com.example.controller.CollectionController;
import com.example.event.IShutdownListener;
import com.example.input.dto.CityInputRequestDto;
import com.example.input.dto.InputActionData;
import com.example.input.env.EnvironmentProvider;
import com.example.input.json.JsonParser;
import com.example.input.readers.IReader;
import com.example.input.readers.file.InputStreamProvider;
import com.example.input.readers.terminal.Processor;
import com.example.service.InputMode;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CollectionInput implements IRunnable, IShutdownListener {

    private final IReader reader;
    private final CollectionController collectionController;
    private final EnvironmentProvider environmentProvider;
    private final InputStreamProvider inputStreamProvider;
    private final JsonParser<List<CityInputRequestDto>> parser;
    private boolean shutdown = false;

    public CollectionInput(IReader reader,
                           CollectionController collectionController,
                           EnvironmentProvider environmentProvider,
                           InputStreamProvider inputStreamProvider,
                           JsonParser<List<CityInputRequestDto>> parser) {
        this.reader = reader;
        this.collectionController = collectionController;
        this.environmentProvider = environmentProvider;
        this.inputStreamProvider = inputStreamProvider;
        this.parser = parser;
    }

    @Override
    public void run() {
        System.out.println("Приложение запускается");

        try {
            initialize();
        } catch (IOException e) {
            System.out.println(
                    "Произошла ошибка инициализации коллекции: "
                    + e.getMessage());
            collectionController.exit();
        }

        CommandDistributor commandDistributor =
                new CommandDistributor(collectionController,
                                       reader);

        while (!shutdown) {
            System.out.print("> ");
            try {
                String[] inputArgs = Processor.processTerminalCommand(
                        reader.read());
                commandDistributor.distribute(inputArgs);
            } catch (IOException e) {
                System.out.println(
                        "Файл с указанным названием не найден или к нему нет доступа"
                );
                collectionController.exit();
            }
        }
    }

    private void initialize() throws IOException {
        String fileName = environmentProvider.getFileName();

        if (fileName == null) {
            System.out.println(
                    "Не найдена переменная окружения с названием файла");
            collectionController.exit();
            return;
        }
        try (InputStreamReader reader = inputStreamProvider.open(fileName)) {
            List<CityInputRequestDto> cities = parser.parse(reader);
            int counter = 1;
            for (CityInputRequestDto cityInputRequestDto : cities) {
                try {
                    collectionController.controlInputCity(cityInputRequestDto,
                                                          InputMode.ALWAYS,
                                                          new InputActionData()
                    );
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
                    collectionController.exit();
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
