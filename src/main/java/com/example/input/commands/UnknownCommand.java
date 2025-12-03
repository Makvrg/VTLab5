package com.example.input.commands;

import com.example.input.readers.IReader;
import com.example.output.IPrinter;

public class UnknownCommand implements ICommand {

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
