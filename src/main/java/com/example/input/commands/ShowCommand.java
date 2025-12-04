package com.example.input.commands;

import com.example.input.readers.IReader;
import com.example.output.IPrinter;
import com.example.service.CollectionService;
import lombok.Getter;


public class ShowCommand implements ICommand{

    @Getter
    private final String commandSignature = "show";
    @Getter
    private final String commandDescription =
            "вывести в стандартный поток вывода все элементы "
                    + "коллекции в строковом представлении";

    private final CollectionService collectionService;
    private final IPrinter printer;

    public ShowCommand(CollectionService collectionService,
                       IPrinter printer) {
        this.collectionService = collectionService;
        this.printer = printer;
    }

    @Override
    public void execute(String[] ignoredArgs, IReader ignoredReader) {
        printer.forcePrint(collectionService.show());
    }

}
