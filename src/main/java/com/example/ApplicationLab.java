package com.example;

import com.example.controller.CityRawRequestDtoValidator;
import com.example.controller.CollectionController;
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
import com.example.repository.CollectionRepository;
import com.example.service.CityTypedRequestDtoValidator;
import com.example.service.CollectionService;

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
        CityTypedRequestDtoValidator cityTypedRequestDtoValidator = new CityTypedRequestDtoValidator();
        CollectionService collectionService =
                new CollectionService(collectionRepository,
                        cityTypedRequestDtoValidator);

        CityRawRequestDtoValidator cityRawRequestDtoValidator = new CityRawRequestDtoValidator();
        CollectionController collectionController =
                new CollectionController(collectionService,
                        cityRawRequestDtoValidator);

        IReader terminalReader = new TerminalReader();

        EnvironmentProvider environmentProvider = new EnvVariableProvider("CITY_FILE");
        InputStreamProvider inputStreamProvider = new FileInputStreamProvider();
        JsonParser<List<CityRawRequestDto>> parser = new CityJsonParser();
        CollectionInput collectionInput =
                new CollectionInput(terminalReader,
                                    collectionController,
                                    environmentProvider,
                                    inputStreamProvider,
                                    parser
                );

        collectionService.addShutdownListener(collectionInput);

        return collectionInput;
    }

}
