package ru.ifmo.se.commands;

import lombok.RequiredArgsConstructor;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.formatter.OutputStringFormatter;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.service.CollectionService;

@RequiredArgsConstructor
public class HeadCommand implements Command {

    private static final String COMMAND_SIGNATURE = "head";

    private static final String COMMAND_DESCRIPTION =
            "вывести первый элемент коллекции";

    private final CollectionService collectionService;
    private final Printer printer;
    private final OutputStringFormatter formatter;

    @Override
    public String getCommandSignature() {
        return COMMAND_SIGNATURE;
    }

    @Override
    public String getCommandDescription() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    public void execute(String[] ignoredArgs, Reader ignoredReader) {
        printer.forcePrintln(collectionService.head()
                .map(city -> "Первый элемент коллекции:\n"
                        + formatter.formatCity(city))
                .orElse("Коллекция пуста"));
    }
}
