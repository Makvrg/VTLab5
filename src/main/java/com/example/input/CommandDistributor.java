package com.example.input;

import com.example.controller.CollectionController;
import com.example.input.commands.*;
import com.example.input.dto.ParamRawData;
import com.example.input.readers.IReader;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class CommandDistributor {

    private final Map<String, Function<String[], ICommand>> commands;

    @Setter
    private IReader reader;

    public CommandDistributor(CollectionController collectionController,
                              IReader reader,
                              Consumer<IReader> setCollectionInputReader) {
        this.reader = reader;
        commands = buildMapOfCommands(collectionController,
                                      setCollectionInputReader);
    }

    public void distribute(String[] inputArgs) {
        commands.getOrDefault(inputArgs[0],
                              UnknownCommand::new)
                .apply(inputArgs)
                .execute();
    }

    private Map<String, Function<String[], ICommand>> buildMapOfCommands(
            CollectionController collectionController,
            Consumer<IReader> setCollectionInputReader) {
        Map<String, Function<String[], ICommand>> commands = new HashMap<>();

        commands.put("help", _ -> new HelpCommand(collectionController));
        commands.put("exit", _ -> new ExitCommand(collectionController));
        commands.put("info", _ -> new InfoCommand(collectionController));
        commands.put("add", _ -> new AddCityCommand(collectionController,
                                                    reader,
                                                    new ParamRawData())
        );
        commands.put("show", _ -> new ShowCommand(collectionController));
        commands.put("remove_by_id",
                     args -> new RemoveByIdCommand(collectionController,
                                                   args));
        commands.put("clear", _ -> new ClearCommand(collectionController));
        commands.put("head", _ -> new HeadCommand(collectionController));
        commands.put("remove_head", _ -> new RemoveHeadCommand(collectionController));
        commands.put("add_if_max", _ -> new AddIfMaxCityCommand(collectionController,
                                                                reader,
                                                                new ParamRawData())
        );
        commands.put("remove_all_by_population_density",
                     args -> new RemoveAllByPopulationDensityCommand(
                             collectionController,
                             args)
        );
        commands.put("filter_less_than_population_density",
                args -> new FilterLessThanPopulationDensityCommand(
                        collectionController,
                        args)
        );
        commands.put("print_field_descending_government",
                _ -> new PrintFieldDescendingGovernmentCommand(
                        collectionController)
        );
        commands.put("update",
                args -> new UpdateByIdCityCommand(
                        collectionController,
                        reader,
                        new ParamRawData()
                                // TODO Можно реализовать Билдер
                                .setId((args.length == 1 || args[1] == null)
                                        ? ""
                                        : args[1]))
        );
        commands.put("execute_script",
                args -> new ExecuteScriptCommand(collectionController,
                                                        args,
                                                        setCollectionInputReader,
                                                        this::setReader)
        );
        return commands;
    }

}
