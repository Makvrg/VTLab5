package com.example.input;

import com.example.CityValidationException;
import com.example.controller.CollectionController;
import com.example.event.IShutdownListener;
import com.example.input.dto.CityRawRequestDto;
import com.example.input.dto.ParamRawData;
import com.example.input.env.EnvironmentProvider;
import com.example.input.json.JsonParser;
import com.example.input.readers.IReader;
import com.example.input.readers.file.InputStreamProvider;
import com.example.input.readers.terminal.Processor;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CollectionInput implements IRunnable, IShutdownListener {

    @Setter
    private IReader reader;

    private final IReader terminalReader;

    private final CollectionController collectionController;
    private final EnvironmentProvider environmentProvider;
    private final InputStreamProvider inputStreamProvider;
    private final JsonParser<List<CityRawRequestDto>> parser;
    private boolean shutdown = false;

    public CollectionInput(IReader reader,
                           CollectionController collectionController,
                           EnvironmentProvider environmentProvider,
                           InputStreamProvider inputStreamProvider,
                           JsonParser<List<CityRawRequestDto>> parser) {
        this.reader = reader;
        terminalReader = reader;
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
                                       reader,
                                       this::setReader);

        while (!shutdown) {
            System.out.print("> ");
            try {
                String inputLine = reader.read();

                if (inputLine == null) {
                    reader = terminalReader;
                    commandDistributor.setReader(terminalReader);
                    System.out.println("Активен режим чтения терминала");
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
                reader = terminalReader;
                commandDistributor.setReader(terminalReader);
                System.out.println("Активен режим чтения терминала");
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
            List<CityRawRequestDto> cities = parser.parse(reader);
            int counter = 1;
            for (CityRawRequestDto cityRawRequestDto : cities) {
                try {
                    collectionController.add(cityRawRequestDto,
                                             new ParamRawData()
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
