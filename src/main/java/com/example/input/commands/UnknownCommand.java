package com.example.input.commands;

import com.example.input.readers.IReader;
import com.example.output.IPrinter;
import lombok.Getter;

public class UnknownCommand implements ICommand {

    @Getter
    private final String commandSignature = "unknown";
    @Getter
    private final String commandDescription =
            "вызывается автоматически при вводе команды, которая не поддерживается программой";

    private final IPrinter printer;

    public UnknownCommand(IPrinter printer) {
        this.printer = printer;
    }

    @Override
    public void execute(String[] inputArgs, IReader ignoredReader) {
        printer.forcePrintln(
                String.format(
                        "Передана неизвестная команда: %s", inputArgs[0]
                )
        );
    }

}
