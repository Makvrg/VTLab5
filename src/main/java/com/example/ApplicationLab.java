package com.example;

import com.example.entity.City;
import com.example.input.CollectionInput;
import com.example.input.dto.CityRawRequestDto;
import com.example.input.env.EnvVariableProvider;
import com.example.input.env.EnvironmentProvider;
import com.example.input.json.CityJsonParser;
import com.example.input.json.JsonParser;
import com.example.input.readers.IReader;
import com.example.input.readers.file.FileInputStreamProvider;
import com.example.input.readers.file.InputStreamProvider;
import com.example.input.readers.terminal.TerminalReader;
import com.example.output.IPrinter;
import com.example.output.OutputPrinter;
import com.example.repository.CollectionRepository;
import com.example.service.CollectionService;
import com.example.typer.DataTyper;
import com.example.validator.CommandValidator;

import java.util.ArrayList;
import java.util.List;

public class ApplicationLab {

    public static void main(String[] args) {

        List<City> collection = new ArrayList<>();
        String collectionType = "ArrayList";
        String elementsType = "City";

        CollectionRepository collectionRepository =
                new CollectionRepository(collection,
                                         collectionType,
                                         elementsType);

        CollectionInput collectionInput = getCollectionInput(collectionRepository);

        collectionInput.run();
    }

    private static CollectionInput getCollectionInput(CollectionRepository collectionRepository) {
        CollectionService collectionService =
                new CollectionService(collectionRepository);

        IReader terminalReader = new TerminalReader();

        CommandValidator commandValidator = new CommandValidator();
        DataTyper dataTyper = new DataTyper();

        EnvironmentProvider environmentProvider = new EnvVariableProvider("CITY_FILE");
        InputStreamProvider inputStreamProvider = new FileInputStreamProvider();
        JsonParser<List<CityRawRequestDto>> parser = new CityJsonParser();

        IPrinter printer = new OutputPrinter();

        CollectionInput collectionInput =
                new CollectionInput(terminalReader,
                                    printer,
                                    collectionService,
                                    commandValidator,
                                    dataTyper,
                                    environmentProvider,
                                    inputStreamProvider,
                                    parser
                );

        collectionService.addShutdownListener(collectionInput);

        return collectionInput;
    }

}
