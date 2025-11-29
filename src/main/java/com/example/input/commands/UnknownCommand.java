package com.example.input.commands;

import com.example.output.IPrinter;

public class UnknownCommand implements ICommand {

    private final String[] inputArgs;
    private final IPrinter printer;

    public UnknownCommand(String[] inputArgs,
                          IPrinter printer) {
        this.inputArgs = inputArgs;
        this.printer = printer;
    }

    @Override
    public void execute() {
        printer.forcePrintln(
                String.format(
                        "Передана неизвестная команда: %s", inputArgs[0]
                )
        );
    }

}
