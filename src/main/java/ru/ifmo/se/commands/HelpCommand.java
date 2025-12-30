package ru.ifmo.se.commands;

import lombok.RequiredArgsConstructor;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.print.Printer;

import java.util.Collection;

@RequiredArgsConstructor
public class HelpCommand implements Command {

    private static final String COMMAND_SIGNATURE = "help";

    private static final String COMMAND_DESCRIPTION =
            "вывести справку по доступным командам";

    private final Printer printer;
    private final Collection<Command> commands;

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
        StringBuilder helpText = new StringBuilder();
        helpText.append("Справка по командам приложения:\n");
        for (Command command : commands) {
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
