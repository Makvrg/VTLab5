package com.example.input.commands;

import com.example.output.IPrinter;
import com.example.service.CollectionService;


public class ShowCommand implements ICommand{

    private final CollectionService collectionService;
    private final IPrinter printer;

    public ShowCommand(CollectionService collectionService,
                       IPrinter printer) {
        this.collectionService = collectionService;
        this.printer = printer;
    }

    @Override
    public void execute() {
        printer.forcePrint(collectionService.show());
    }

}
