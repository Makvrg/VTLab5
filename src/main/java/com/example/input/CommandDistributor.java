package com.example.input;

import com.example.input.commands.*;
import com.example.input.dto.ParamRawData;
import com.example.input.readers.IReader;
import com.example.output.IPrinter;
import com.example.service.CollectionService;
import com.example.typer.DataTyper;
import com.example.validator.CommandValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CommandDistributor {

    private final Map<String, Function<String[], ICommand>> commandFactories;

    private final List<IReader> collectionInputReaders;
    private final IPrinter printer;

    public CommandDistributor(CollectionService collectionService,
                              CommandValidator commandValidator,
                              DataTyper dataTyper,
                              List<IReader> readers,
                              IPrinter printer) {
        commandFactories = buildMapOfCommands(collectionService,
                                      commandValidator,
                                      dataTyper);
        this.collectionInputReaders = readers;
        this.printer = printer;
    }

    public void distribute(String[] inputArgs) {
        commandFactories.getOrDefault(inputArgs[0],
                        args -> new UnknownCommand(args, printer))
                .apply(inputArgs)
                .execute();
    }

    private Map<String, Function<String[], ICommand>> buildMapOfCommands(
            CollectionService collectionService,
            CommandValidator commandValidator,
            DataTyper dataTyper) {
        Map<String, Function<String[], ICommand>> commands = new HashMap<>();

        commands.put("help", _ -> new HelpCommand(printer));
        commands.put("exit", _ -> new ExitCommand(collectionService, printer));
        commands.put("info", _ -> new InfoCommand(collectionService, printer));
        commands.put("add", _ -> new AddCityCommand(
                collectionService,
                commandValidator,
                dataTyper,
                collectionInputReaders.getLast(),
                printer,
                new ParamRawData())
        );
        commands.put("show", _ -> new ShowCommand(collectionService, printer));
        commands.put("remove_by_id",
                     args -> new RemoveByIdCommand(
                             collectionService,
                             commandValidator,
                             printer,
                             args)
        );
        commands.put("clear", _ -> new ClearCommand(collectionService, printer));
        commands.put("head", _ -> new HeadCommand(collectionService, printer));
        commands.put("remove_head", _ -> new RemoveHeadCommand(collectionService,
                                                               printer));
        commands.put("add_if_max", _ -> new AddIfMaxCityCommand(
                collectionService,
                commandValidator,
                dataTyper,
                collectionInputReaders.getLast(),
                printer,
                new ParamRawData())
        );
        commands.put("remove_all_by_population_density",
                     args -> new RemoveAllByPopulationDensityCommand(
                             collectionService,
                             commandValidator,
                             printer,
                             args)
        );
        commands.put("filter_less_than_population_density",
                args -> new FilterLessThanPopulationDensityCommand(
                        collectionService,
                        commandValidator,
                        printer,
                        args)
        );
        commands.put("print_field_descending_government",
                _ -> new PrintFieldDescendingGovernmentCommand(
                        collectionService,
                        printer)
        );
        commands.put("update",
                args -> new UpdateByIdCityCommand(
                        collectionService,
                        commandValidator,
                        dataTyper,
                        collectionInputReaders.getLast(),
                        printer,
                        new ParamRawData()
                                // TODO Можно реализовать Билдер
                                .setId((args.length == 1 || args[1] == null)
                                        ? ""
                                        : args[1]))
        );
        commands.put("execute_script",
                args -> new ExecuteScriptCommand(
                        commandValidator,
                        args,
                        collectionInputReaders,
                        printer)
        );
        return commands;
    }

}
