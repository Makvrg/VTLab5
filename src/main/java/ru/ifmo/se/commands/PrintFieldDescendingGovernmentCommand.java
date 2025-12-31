package ru.ifmo.se.commands;

import ru.ifmo.se.entity.Government;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.formatter.OutputStringFormatter;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.service.CollectionService;

import java.util.List;

public class PrintFieldDescendingGovernmentCommand extends Command {

    private final CollectionService collectionService;
    private final Printer printer;
    private final OutputStringFormatter formatter;

    public PrintFieldDescendingGovernmentCommand(
            CollectionService collectionService,
            Printer printer,
            OutputStringFormatter formatter) {
        super("print_field_descending_government",
                "вывести значения поля government всех элементов в порядке убывания"
        );
        this.collectionService = collectionService;
        this.printer = printer;
        this.formatter = formatter;
    }

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
