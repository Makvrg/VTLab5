package ru.ifmo.se.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.Printer;
import ru.ifmo.se.io.output.formatter.OutputStringFormatter;
import ru.ifmo.se.service.CollectionService;

@RequiredArgsConstructor
public class HeadCommand implements Command {

    @Getter
    private final String commandSignature = "head";
    @Getter
    private final String commandDescription =
            "вывести первый элемент коллекции";

    private final CollectionService collectionService;
    private final Printer printer;
    private final OutputStringFormatter formatter;

    @Override
    public void execute(String[] ignoredArgs, Reader ignoredReader) {
        printer.forcePrintln(collectionService.head()
                .map(city -> "Первый элемент коллекции:\n"
                        + formatter.formatCity(city))
                .orElse("Коллекция пуста"));
    }

}
