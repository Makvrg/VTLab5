package ru.ifmo.se.io.input;

import ru.ifmo.se.commands.*;
import ru.ifmo.se.io.input.env.EnvironmentProvider;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.input.readers.factory.ReaderFactory;
import ru.ifmo.se.io.input.readers.file.InputStreamProvider;
import ru.ifmo.se.io.output.formatter.OutputStringFormatter;
import ru.ifmo.se.io.output.json.CityJsonWriter;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.service.CollectionService;
import ru.ifmo.se.typer.DataTyper;
import ru.ifmo.se.validator.CommandValidator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CommandInvoker {

    private final InputStreamProvider inputStreamProvider;
    private final ReaderFactory readerFactory;
    private final List<Reader> commandInputReaders;
    private final OutputStringFormatter formatter;
    private final Map<String, Command> commands;
    private String keyOfUnknownCommand;

    public CommandInvoker(InputStreamProvider inputStreamProvider,
                          ReaderFactory readerFactory,
                          CollectionService collectionService,
                          CommandValidator commandValidator,
                          DataTyper dataTyper,
                          List<Reader> readers,
                          OutputStringFormatter formatter,
                          Printer printer,
                          EnvironmentProvider environmentProvider,
                          CityJsonWriter fileWriter) {
        this.inputStreamProvider = inputStreamProvider;
        this.readerFactory = readerFactory;
        this.commandInputReaders = readers;
        this.formatter = formatter;
        commands = buildMapOfCommands(
                collectionService,
                commandValidator,
                dataTyper,
                printer,
                environmentProvider,
                fileWriter
        );
    }

    public void invokeCommand(String[] inputArgs) {
        if (commands.containsKey(inputArgs[0])) {
            commands.get(inputArgs[0])
                    .execute(inputArgs,
                             commandInputReaders.get(
                                     commandInputReaders.size() - 1
                             )
                    );
        } else {
            commands.get(keyOfUnknownCommand)
                    .execute(inputArgs, null);
        }
    }

    private Map<String, Command> buildMapOfCommands(
            CollectionService collectionService,
            CommandValidator commandValidator,
            DataTyper dataTyper,
            Printer printer,
            EnvironmentProvider environmentProvider,
            CityJsonWriter fileWriter) {
        Map<String, Command> commands = new LinkedHashMap<>();
        Command currentCommand;
        Function<Command, String> getCommandName = 
                cmd -> cmd.getCommandSignature().split(" ")[0];
        
        currentCommand = new UnknownCommand(printer);
        commands.put(getCommandName.apply(currentCommand), currentCommand);

        keyOfUnknownCommand = getCommandName.apply(currentCommand);
        
        currentCommand = new HelpCommand(printer, commands.values());
        commands.put(getCommandName.apply(currentCommand), currentCommand);

        currentCommand = new ExitCommand(collectionService, printer);
        commands.put(getCommandName.apply(currentCommand), currentCommand);

        currentCommand = new InfoCommand(collectionService, printer);
        commands.put(getCommandName.apply(currentCommand), currentCommand);

        currentCommand = new AddCityCommand(
                collectionService, commandValidator,
                dataTyper, printer
        );
        commands.put(getCommandName.apply(currentCommand), currentCommand);

        currentCommand = new ShowCommand(collectionService, printer, formatter);
        commands.put(getCommandName.apply(currentCommand), currentCommand);

        currentCommand = new RemoveByIdCommand(
                collectionService, commandValidator, printer
        );
        commands.put(getCommandName.apply(currentCommand), currentCommand);

        currentCommand = new ClearCommand(collectionService, printer);
        commands.put(getCommandName.apply(currentCommand), currentCommand);

        currentCommand = new SaveCommand(
                collectionService, printer, environmentProvider, fileWriter
        );
        commands.put(getCommandName.apply(currentCommand), currentCommand);

        currentCommand = new HeadCommand(collectionService, printer, formatter);
        commands.put(getCommandName.apply(currentCommand), currentCommand);

        currentCommand = new RemoveHeadCommand(collectionService, printer, formatter);
        commands.put(getCommandName.apply(currentCommand), currentCommand);

        currentCommand = new AddIfMaxCityCommand(
                collectionService, commandValidator,
                dataTyper, printer
        );
        commands.put(getCommandName.apply(currentCommand), currentCommand);

        currentCommand = new RemoveAllByPopulationDensityCommand(
                collectionService, commandValidator, printer
        );
        commands.put(getCommandName.apply(currentCommand), currentCommand);

        currentCommand = new FilterLessThanPopulationDensityCommand(
                collectionService, commandValidator, printer, formatter
        );
        commands.put(getCommandName.apply(currentCommand), currentCommand);

        currentCommand = new PrintFieldDescendingGovernmentCommand(
                collectionService, printer, formatter);
        commands.put(getCommandName.apply(currentCommand), currentCommand);

        currentCommand = new UpdateByIdCityCommand(
                collectionService, commandValidator,
                dataTyper, printer
        );
        commands.put(getCommandName.apply(currentCommand), currentCommand);

        currentCommand = new ExecuteScriptCommand(
                commandValidator, inputStreamProvider,
                readerFactory, commandInputReaders, printer
        );
        commands.put(getCommandName.apply(currentCommand), currentCommand);
        return commands;
    }
}
