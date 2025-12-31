package ru.ifmo.se.commands;

import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.formatter.OutputStringFormatter;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.service.CollectionService;

public class HeadCommand extends Command {

    private final CollectionService collectionService;
    private final Printer printer;
    private final OutputStringFormatter formatter;

    public HeadCommand(CollectionService collectionService,
                       Printer printer,
                       OutputStringFormatter formatter) {
        super("head", "вывести первый элемент коллекции");
        this.collectionService = collectionService;
        this.printer = printer;
        this.formatter = formatter;
    }

    @Override
    public void execute(String[] ignoredArgs, Reader ignoredReader) {
        printer.forcePrintln(collectionService.head()
                .map(city -> "Первый элемент коллекции:\n"
                        + formatter.formatCity(city))
                .orElse("Коллекция пуста"));
    }
}
