package com.example.input.commands;

import com.example.output.IPrinter;
import com.example.service.CollectionService;

public class HeadCommand implements ICommand {

    private final CollectionService collectionService;
    private final IPrinter printer;

    public HeadCommand(CollectionService collectionService,
                       IPrinter printer) {
        this.collectionService = collectionService;
        this.printer = printer;
    }

    @Override
    public void execute() {
        printer.forcePrintln(collectionService.head());
    }

}
