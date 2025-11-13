package com.example;

import com.example.controller.CityAddRequestDtoValidator;
import com.example.controller.CollectionController;
import com.example.entity.City;
import com.example.input.CollectionInput;
import com.example.input.readers.IReader;
import com.example.input.readers.file.FileReader;
import com.example.input.readers.terminal.TerminalReader;
import com.example.repository.CollectionRepository;
import com.example.service.CityAddDtoValidator;
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
        CityAddDtoValidator cityAddDtoValidator = new CityAddDtoValidator();
        CollectionService collectionService =
                new CollectionService(collectionRepository,
                                      cityAddDtoValidator);

        CityAddRequestDtoValidator cityAddRequestDtoValidator = new CityAddRequestDtoValidator();
        CollectionController collectionController =
                new CollectionController(collectionService,
                        cityAddRequestDtoValidator);

        IReader terminalReader = new TerminalReader();
        IReader fileReader = new FileReader();

        CollectionInput collectionInput =
                new CollectionInput(terminalReader,
                                    fileReader,
                                    collectionController);

        collectionService.addShutdownListener(collectionInput);

        return collectionInput;
    }

}
