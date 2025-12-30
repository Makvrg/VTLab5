package ru.ifmo.se.commands;

import lombok.RequiredArgsConstructor;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.print.Printer;

@RequiredArgsConstructor
public class UnknownCommand implements Command {

    private static final String COMMAND_SIGNATURE = "unknown";

    private static final String COMMAND_DESCRIPTION =
            "вызывается автоматически при вводе команды, которая не поддерживается программой";

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
    public void execute(String[] inputArgs, Reader ignoredReader) {
        printer.forcePrintln(
                String.format(
                        "Передана неизвестная команда: %s", inputArgs[0]
                )
        );
    }
}
