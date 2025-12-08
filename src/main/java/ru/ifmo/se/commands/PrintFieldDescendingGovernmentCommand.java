package ru.ifmo.se.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.Printer;
import ru.ifmo.se.service.CollectionService;

@RequiredArgsConstructor
public class PrintFieldDescendingGovernmentCommand implements Command {

    @Getter
    private final String commandSignature = "print_field_descending_government";
    @Getter
    private final String commandDescription =
            "вывести значения поля government всех элементов в порядке убывания";

    private final CollectionService collectionService;
    private final Printer printer;

    @Override
    public void execute(String[] ignoredArgs, Reader ignoredReader) {
        printer.forcePrint(collectionService.printFieldDescendingGovernment());
    }

}
