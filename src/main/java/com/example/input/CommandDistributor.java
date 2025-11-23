package com.example.input;

import com.example.input.commands.*;
import com.example.input.dto.ParamRawData;
import com.example.input.readers.IReader;
import com.example.service.CollectionService;
import com.example.typer.DataTyper;
import com.example.validator.CommandValidator;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class CommandDistributor {

    private final Map<String, Function<String[], ICommand>> commands;

    @Setter
    private IReader reader;

    public CommandDistributor(CollectionService collectionService,
                              CommandValidator commandValidator,
                              DataTyper dataTyper,
                              IReader reader,
                              Consumer<IReader> setCollectionInputReader) {
        this.reader = reader;
        commands = buildMapOfCommands(collectionService,
                                      commandValidator,
                                      dataTyper,
                                      setCollectionInputReader);
    }

    public void distribute(String[] inputArgs) {
        commands.getOrDefault(inputArgs[0],
                              UnknownCommand::new)
                .apply(inputArgs)
                .execute();
    }

    private Map<String, Function<String[], ICommand>> buildMapOfCommands(
            CollectionService collectionService,
            CommandValidator commandValidator,
            DataTyper dataTyper,
            Consumer<IReader> setCollectionInputReader) {
        Map<String, Function<String[], ICommand>> commands = new HashMap<>();

        commands.put("help", _ -> new HelpCommand());
        commands.put("exit", _ -> new ExitCommand(collectionService));
        commands.put("info", _ -> new InfoCommand(collectionService));
        commands.put("add", _ -> new AddCityCommand(
                collectionService,
                commandValidator,
                dataTyper,
                reader,
                new ParamRawData())
        );
        commands.put("show", _ -> new ShowCommand(collectionService));
        commands.put("remove_by_id",
                     args -> new RemoveByIdCommand(
                             collectionService,
                             commandValidator,
                             args)
        );
        commands.put("clear", _ -> new ClearCommand(collectionService));
        commands.put("head", _ -> new HeadCommand(collectionService));
        commands.put("remove_head", _ -> new RemoveHeadCommand(collectionService));
        commands.put("add_if_max", _ -> new AddIfMaxCityCommand(
                collectionService,
                commandValidator,
                dataTyper,
                reader,
                new ParamRawData())
        );
        commands.put("remove_all_by_population_density",
                     args -> new RemoveAllByPopulationDensityCommand(
                             collectionService,
                             commandValidator,
                             args)
        );
        commands.put("filter_less_than_population_density",
                args -> new FilterLessThanPopulationDensityCommand(
                        collectionService,
                        commandValidator,
                        args)
        );
        commands.put("print_field_descending_government",
                _ -> new PrintFieldDescendingGovernmentCommand(
                        collectionService)
        );
        commands.put("update",
                args -> new UpdateByIdCityCommand(
                        collectionService,
                        commandValidator,
                        dataTyper,
                        reader,
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
                        setCollectionInputReader,
                        this::setReader)
        );
        return commands;
    }

}
