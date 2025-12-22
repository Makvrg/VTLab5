package ru.ifmo.se.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.service.CollectionService;

@RequiredArgsConstructor
public class ClearCommand implements Command {

    @Getter
    private final String commandSignature = "clear";
    @Getter
    private final String commandDescription = "очистить коллекцию";

    private final CollectionService collectionService;
    private final Printer printer;

    @Override
    public void execute(String[] ignoredArgs, Reader ignoredReader) {
        if (collectionService.clear()) {
            printer.forcePrintln("Коллекция успешно очищена");
        } else {
            printer.forcePrintln("Коллекция уже пуста");
        }
    }
}
