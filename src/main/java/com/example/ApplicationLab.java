package com.example;

import com.example.controller.CollectionController;
import com.example.input.CollectionInput;
import com.example.input.readers.IReader;
import com.example.input.readers.file.FileReader;
import com.example.input.readers.terminal.TerminalReader;
import com.example.repository.CollectionRepository;
import com.example.service.CollectionService;

import java.util.ArrayList;
import java.util.List;

public class ApplicationLab {

    public static void main(String[] args) {

        List<String> collection = new ArrayList<>();

        CollectionRepository collectionRepository =
                new CollectionRepository(collection);

        CollectionService collectionService =
                new CollectionService(collectionRepository);

        CollectionController collectionController =
                new CollectionController(collectionService);

        IReader terminalReader = new TerminalReader();
        IReader fileReader = new FileReader();
        CollectionInput collectionInput =
                new CollectionInput(terminalReader,
                                    fileReader,
                                    collectionController);

        collectionService.addShutdownListener(collectionInput);

        collectionInput.run();
    }

}
