package ru.ifmo.se.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ifmo.se.entity.Government;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.formatter.OutputStringFormatter;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.service.CollectionService;

import java.util.List;

@RequiredArgsConstructor
public class PrintFieldDescendingGovernmentCommand implements Command {

    @Getter
    private final String commandSignature = "print_field_descending_government";
    @Getter
    private final String commandDescription =
            "вывести значения поля government всех элементов в порядке убывания";

    private final CollectionService collectionService;
    private final Printer printer;
    private final OutputStringFormatter formatter;

    @Override
    public void execute(String[] ignoredArgs, Reader ignoredReader) {
        List<Government> govList = collectionService.printFieldDescendingGovernment();
        if (!govList.isEmpty()) {
            printer.forcePrintln("Все упорядоченные по убыванию типы правления из коллекции:\n"
                    + formatter.formatGovernmentList(govList)
            );
        } else {
            printer.forcePrintln("Коллекция пуста");
        }
    }

}
