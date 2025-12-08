package ru.ifmo.se.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.Printer;

import java.util.Collection;

@RequiredArgsConstructor
public class HelpCommand implements Command {

    @Getter
    private final String commandSignature = "help";
    @Getter
    private final String commandDescription =
            "вывести справку по доступным командам";

    private final Printer printer;
    private final Collection<Command> commands;

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
