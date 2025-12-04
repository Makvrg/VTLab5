package com.example.input.commands;

import com.example.input.readers.IReader;
import com.example.output.IPrinter;
import com.example.service.CollectionService;
import lombok.Getter;

public class ClearCommand implements ICommand {

    @Getter
    private final String commandSignature = "clear";
    @Getter
    private final String commandDescription = "очистить коллекцию";

    private final CollectionService collectionService;
    private final IPrinter printer;

    public ClearCommand(CollectionService collectionService,
                        IPrinter printer) {
        this.collectionService = collectionService;
        this.printer = printer;
    }

    @Override
    public void execute(String[] ignoredArgs, IReader ignoredReader) {
        if (collectionService.clear()) {
            printer.forcePrintln("Коллекция успешно очищена");
        } else {
            printer.forcePrintln("Коллекция уже пуста");
        }
    }

}
