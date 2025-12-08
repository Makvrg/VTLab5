package ru.ifmo.se.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.Printer;

@RequiredArgsConstructor
public class UnknownCommand implements Command {

    @Getter
    private final String commandSignature = "unknown";
    @Getter
    private final String commandDescription =
            "вызывается автоматически при вводе команды, которая не поддерживается программой";

    private final Printer printer;

    @Override
    public void execute(String[] inputArgs, Reader ignoredReader) {
        printer.forcePrintln(
                String.format(
                        "Передана неизвестная команда: %s", inputArgs[0]
                )
        );
    }

}
