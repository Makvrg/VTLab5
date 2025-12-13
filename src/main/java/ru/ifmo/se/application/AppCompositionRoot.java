package ru.ifmo.se.application;

import lombok.Getter;
import ru.ifmo.se.collection.CollectionWithInfo;
import ru.ifmo.se.entity.City;
import ru.ifmo.se.io.input.CommandInput;
import ru.ifmo.se.io.input.env.EnvVariableProvider;
import ru.ifmo.se.io.input.env.EnvironmentProvider;
import ru.ifmo.se.io.input.json.CityJsonParser;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.input.readers.factory.ReaderFactory;
import ru.ifmo.se.io.input.readers.file.FileInputStreamProvider;
import ru.ifmo.se.io.input.readers.file.InputStreamProvider;
import ru.ifmo.se.io.output.formatter.OutputStringFormatter;
import ru.ifmo.se.io.output.json.CityJsonWriter;
import ru.ifmo.se.io.output.print.OutputPrinter;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.repository.CollectionRepository;
import ru.ifmo.se.service.CollectionService;
import ru.ifmo.se.typer.DataTyper;
import ru.ifmo.se.validator.CommandValidator;

import java.util.ArrayList;
import java.util.List;

public final class AppCompositionRoot {

    private static final String ENV_VAR_NAME = "CITY_FILE";
    private static final String BACKUP_FILE_NAME =
            "backup_collection_save_file_wl64fI983T";

    private final List<City> collection = new ArrayList<>();

    private final CollectionWithInfo collectionWithInfo =
            new CollectionWithInfo(
                    collection,
                    collection.getClass(),
                    City.class
            );

    @Getter
    private final Printer printer = new OutputPrinter();

    private final ReaderFactory readerFactory = new ReaderFactory();
    private final Reader reader = readerFactory.createTerminalReader("Main Terminal");

    private final CommandValidator validator = new CommandValidator();
    private final DataTyper dataTyper = new DataTyper();

    private final EnvironmentProvider environmentProvider =
            new EnvVariableProvider(ENV_VAR_NAME);
    private final CityJsonWriter jsonWriter =
            new CityJsonWriter(BACKUP_FILE_NAME);
    private final InputStreamProvider inputStreamProvider =
            new FileInputStreamProvider();
    private final CityJsonParser jsonParser =
            new CityJsonParser();

    private final CollectionRepository collectionRepository =
            new CollectionRepository(collectionWithInfo);

    private final OutputStringFormatter formatter = new OutputStringFormatter();
    @Getter
    private final CollectionService collectionService =
            new CollectionService(collectionRepository, formatter);

    @Getter
    private final CommandInput commandInput = new CommandInput(
            reader,
            readerFactory,
            printer,
            collectionService,
            validator,
            dataTyper,
            formatter,
            environmentProvider,
            jsonWriter,
            inputStreamProvider,
            jsonParser
    );

}
