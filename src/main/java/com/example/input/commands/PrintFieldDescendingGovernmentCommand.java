package com.example.input.commands;

import com.example.input.readers.IReader;
import com.example.output.IPrinter;
import com.example.service.CollectionService;
import lombok.Getter;

public class PrintFieldDescendingGovernmentCommand implements ICommand {

    @Getter
    private final String commandSignature = "print_field_descending_government";
    @Getter
    private final String commandDescription =
            "вывести значения поля government всех элементов в порядке убывания";

    private final CollectionService collectionService;
    private final IPrinter printer;

    public PrintFieldDescendingGovernmentCommand(CollectionService collectionService,
                                                 IPrinter printer) {
        this.collectionService = collectionService;
        this.printer = printer;
    }

    @Override
    public void execute(String[] ignoredArgs, IReader ignoredReader) {
        printer.forcePrint(collectionService.printFieldDescendingGovernment());
    }

}
