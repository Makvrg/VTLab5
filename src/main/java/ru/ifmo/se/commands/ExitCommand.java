package ru.ifmo.se.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.service.CollectionService;

@RequiredArgsConstructor
public class ExitCommand implements Command {

    @Getter
    private final String commandSignature = "exit";
    @Getter
    private final String commandDescription =
            "завершить программу (без сохранения в файл)";

    private final CollectionService collectionService;
    private final Printer printer;

    @Override
    public void execute(String[] ignoredArgs, Reader ignoredReader) {
        if (collectionService.exit()) {
            printer.forcePrintln("Закрытие приложения");
        }
    }

}
