package com.example.input.commands;

import com.example.input.readers.IReader;
import com.example.output.IPrinter;
import com.example.service.CollectionService;

public class ExitCommand implements ICommand {

    private final CollectionService collectionService;
    private final IPrinter printer;

    public ExitCommand(CollectionService collectionService,
                       IPrinter printer) {
        this.collectionService = collectionService;
        this.printer = printer;
    }

    @Override
    public void execute(String[] ignoredArgs, IReader ignoredReader) {
        if (collectionService.exit()) {
            printer.forcePrintln("Закрытие приложения");
        }
    }

}
