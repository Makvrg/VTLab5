package com.example.input.commands;

import com.example.input.readers.IReader;
import com.example.output.IPrinter;
import lombok.Getter;

import java.util.Collection;

public class HelpCommand implements ICommand {

    @Getter
    private final String commandSignature = "help";
    @Getter
    private final String commandDescription =
            "вывести справку по доступным командам";

    private final IPrinter printer;
    private final Collection<ICommand> commands;

    public HelpCommand(IPrinter printer,
                       Collection<ICommand> commands) {
        this.printer = printer;
        this.commands = commands;
    }

    @Override
    public void execute(String[] ignoredArgs, IReader ignoredReader) {
        StringBuilder helpText = new StringBuilder();
        helpText.append("Справка по командам приложения:\n");
        for (ICommand command : commands) {
            if (!command.getCommandSignature().equals("unknown")) {
                helpText.append(command.getCommandSignature())
                        .append(" : ")
                        .append(command.getCommandDescription())
                        .append("\n\n");
            }
        }
        helpText.delete(helpText.length() - 2, helpText.length());
        printer.forcePrintln(helpText.toString());
    }

}
