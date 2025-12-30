package ru.ifmo.se.commands;

import lombok.RequiredArgsConstructor;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.service.CollectionService;

@RequiredArgsConstructor
public class ExitCommand implements Command {

    private static final String COMMAND_SIGNATURE = "exit";

    private static final String COMMAND_DESCRIPTION =
            "завершить программу (без сохранения в файл)";

    private final CollectionService collectionService;
    private final Printer printer;

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
        if (collectionService.exit()) {
            printer.forcePrintln("Закрытие приложения");
        }
    }
}
