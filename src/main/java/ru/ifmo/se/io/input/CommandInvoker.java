package ru.ifmo.se.io.input;

import ru.ifmo.se.commands.*;
import ru.ifmo.se.entity.City;
import ru.ifmo.se.io.input.env.EnvironmentProvider;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.Printer;
import ru.ifmo.se.io.output.formatter.OutputStringFormatter;
import ru.ifmo.se.io.output.json.JsonWriter;
import ru.ifmo.se.service.CollectionService;
import ru.ifmo.se.typer.DataTyper;
import ru.ifmo.se.validator.CommandValidator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommandInvoker {

    private final List<Reader> collectionInputReaders;
    private final OutputStringFormatter formatter;
    private final Map<String, Command> commands;

    public CommandInvoker(CollectionService collectionService,
                          CommandValidator commandValidator,
                          DataTyper dataTyper,
                          List<Reader> readers,
                          OutputStringFormatter formatter,
                          Printer printer,
                          EnvironmentProvider environmentProvider,
                          JsonWriter<List<City>> fileWriter) {
        this.collectionInputReaders = readers;
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

    public void distribute(String[] inputArgs) {
        if (commands.containsKey(inputArgs[0])) {
            commands.get(inputArgs[0])
                    .execute(inputArgs,
                             collectionInputReaders.get(
                                     collectionInputReaders.size() - 1
                             )
                    );
        } else {
            commands.get("unknown")
                    .execute(inputArgs, null);
        }
    }

    private Map<String, Command> buildMapOfCommands(
            CollectionService collectionService,
            CommandValidator commandValidator,
            DataTyper dataTyper,
            Printer printer,
            EnvironmentProvider environmentProvider,
            JsonWriter<List<City>> fileWriter) {
        Map<String, Command> commands = new LinkedHashMap<>();

        commands.put("unknown", new UnknownCommand(printer));
        commands.put("help", new HelpCommand(
                printer,
                commands.values()
                )
        );
        commands.put("exit", new ExitCommand(collectionService, printer));
        commands.put("info", new InfoCommand(collectionService, printer));
        commands.put("add", new AddCityCommand(
                collectionService,
                commandValidator,
                dataTyper,
                printer
                )
        );
        commands.put("show", new ShowCommand(
                collectionService,
                printer,
                formatter
                )
        );
        commands.put("remove_by_id", new RemoveByIdCommand(
                collectionService,
                commandValidator,
                printer
                )
        );
        commands.put("clear", new ClearCommand(collectionService, printer));
        commands.put("save", new SaveCommand(
                collectionService,
                printer,
                environmentProvider,
                fileWriter
                )
        );
        commands.put("head", new HeadCommand(
                collectionService,
                printer,
                formatter
                )
        );
        commands.put("remove_head", new RemoveHeadCommand(
                collectionService,
                printer,
                formatter
                )
        );
        commands.put("add_if_max", new AddIfMaxCityCommand(
                collectionService,
                commandValidator,
                dataTyper,
                printer
                )
        );
        commands.put("remove_all_by_population_density",
                new RemoveAllByPopulationDensityCommand(
                        collectionService,
                        commandValidator,
                        printer
                )
        );
        commands.put("filter_less_than_population_density",
                new FilterLessThanPopulationDensityCommand(
                        collectionService,
                        commandValidator,
                        printer,
                        formatter
                )
        );
        commands.put("print_field_descending_government",
                new PrintFieldDescendingGovernmentCommand(
                        collectionService,
                        printer,
                        formatter
                )
        );
        commands.put("update", new UpdateByIdCityCommand(
                collectionService,
                commandValidator,
                dataTyper,
                printer
                )
        );
        commands.put("execute_script", new ExecuteScriptCommand(
                commandValidator,
                collectionInputReaders,
                printer
                )
        );
        return commands;
    }

}
