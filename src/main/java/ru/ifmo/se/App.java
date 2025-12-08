package ru.ifmo.se;

import ru.ifmo.se.collection.CollectionWithInfo;
import ru.ifmo.se.entity.City;
import ru.ifmo.se.io.input.CommandInput;
import ru.ifmo.se.io.input.dto.json.CityFromJsonDto;
import ru.ifmo.se.io.input.env.EnvVariableProvider;
import ru.ifmo.se.io.input.env.EnvironmentProvider;
import ru.ifmo.se.io.input.json.CityJsonParser;
import ru.ifmo.se.io.input.json.JsonParser;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.input.readers.file.FileInputStreamProvider;
import ru.ifmo.se.io.input.readers.file.InputStreamProvider;
import ru.ifmo.se.io.input.readers.terminal.TerminalReader;
import ru.ifmo.se.io.output.Printer;
import ru.ifmo.se.io.output.OutputPrinter;
import ru.ifmo.se.io.output.dto.CityForJsonDto;
import ru.ifmo.se.io.output.json.CityJsonWriter;
import ru.ifmo.se.io.output.json.JsonWriter;
import ru.ifmo.se.repository.CollectionRepository;
import ru.ifmo.se.service.CollectionService;
import ru.ifmo.se.typer.DataTyper;
import ru.ifmo.se.validator.CommandValidator;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {

        List<City> collection = new ArrayList<>();
        String collectionType = collection.getClass().getSimpleName();
        String elementsType = City.class.getSimpleName();

        CollectionWithInfo collectionWithInfo =
                new CollectionWithInfo(
                        collection,
                        collectionType,
                        elementsType
                );
        CollectionRepository collectionRepository =
                new CollectionRepository(collectionWithInfo);

        CommandInput commandInput = getCollectionInput(collectionRepository);

        commandInput.run();
    }

    private static CommandInput getCollectionInput(CollectionRepository collectionRepository) {
        CollectionService collectionService = new CollectionService(collectionRepository);

        Reader terminalReader = new TerminalReader();

        CommandValidator commandValidator = new CommandValidator();
        DataTyper dataTyper = new DataTyper();

        EnvironmentProvider environmentProvider = new EnvVariableProvider("CITY_FILE");
        JsonWriter<List<CityForJsonDto>> fileWriter = new CityJsonWriter();
        InputStreamProvider inputStreamProvider = new FileInputStreamProvider();
        JsonParser<List<CityFromJsonDto>> parser = new CityJsonParser();

        Printer printer = new OutputPrinter();

        CommandInput commandInput =
                new CommandInput(terminalReader,
                                    printer,
                                    collectionService,
                                    commandValidator,
                                    dataTyper,
                                    environmentProvider,
                                    fileWriter,
                                    inputStreamProvider,
                                    parser
                );

        collectionService.addShutdownListener(commandInput);

        return commandInput;
    }

}
