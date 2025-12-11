package ru.ifmo.se.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.service.CollectionService;

@RequiredArgsConstructor
public class InfoCommand implements Command {

    @Getter
    private final String commandSignature = "info";
    @Getter
    private final String commandDescription =
            "вывести в стандартный поток вывода информацию о коллекции "
                    + "(тип, дата инициализации, тип и количество элементов)";

    private final CollectionService collectionService;
    private final Printer printer;

    @Override
    public void execute(String[] ignoredArgs, Reader ignoredReader) {
        printer.forcePrintln(collectionService.info());
    }

}
