package com.example.input.commands;

import com.example.input.readers.IReader;
import com.example.output.IPrinter;
import com.example.service.CollectionService;
import lombok.Getter;

public class HeadCommand implements ICommand {

    @Getter
    private final String commandSignature = "head";
    @Getter
    private final String commandDescription =
            "вывести первый элемент коллекции";

    private final CollectionService collectionService;
    private final IPrinter printer;

    public HeadCommand(CollectionService collectionService,
                       IPrinter printer) {
        this.collectionService = collectionService;
        this.printer = printer;
    }

    @Override
    public void execute(String[] ignoredArgs, IReader ignoredReader) {
        printer.forcePrintln(collectionService.head());
    }

}
