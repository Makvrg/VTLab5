package com.example.input.commands;

import com.example.input.readers.IReader;
import com.example.output.IPrinter;
import com.example.service.CollectionService;
import lombok.Getter;

public class RemoveHeadCommand implements ICommand {

    @Getter
    private final String commandSignature = "remove_head";
    @Getter
    private final String commandDescription =
            "вывести первый элемент коллекции и удалить его";

    private final CollectionService collectionService;
    private final IPrinter printer;

    public RemoveHeadCommand(CollectionService collectionService,
                             IPrinter printer) {
        this.collectionService = collectionService;
        this.printer = printer;
    }

    @Override
    public void execute(String[] ignoredArgs, IReader ignoredReader) {
        printer.forcePrintln(collectionService.removeHead());
    }

}
