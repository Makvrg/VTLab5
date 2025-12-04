package com.example.input.commands;

import com.example.input.readers.IReader;
import com.example.output.IPrinter;
import com.example.service.CollectionService;
import lombok.Getter;

public class InfoCommand implements ICommand {

    @Getter
    private final String commandSignature = "info";
    @Getter
    private final String commandDescription =
            "вывести в стандартный поток вывода информацию о коллекции "
                    + "(тип, дата инициализации, тип и количество элементов)";

    private final CollectionService collectionService;
    private final IPrinter printer;

    public InfoCommand(CollectionService collectionService,
                       IPrinter printer) {
        this.collectionService = collectionService;
        this.printer = printer;
    }

    @Override
    public void execute(String[] ignoredArgs, IReader ignoredReader) {
        printer.forcePrintln(collectionService.info());
    }

}
