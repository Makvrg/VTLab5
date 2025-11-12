package com.example.input;

import com.example.controller.CollectionController;
import com.example.input.commands.*;
import com.example.input.readers.IReader;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandDistributor {

    private final Map<String, Function<String[], ICommand>> commands;

    public CommandDistributor(CollectionController collectionController,
                              IReader terminalReader) {

        commands = buildMapOfCommands(collectionController,
                                      terminalReader);
    }

    public void distribute(String[] inputArgs) {
        commands.getOrDefault(inputArgs[0],
                              UnknownCommand::new)
                .apply(inputArgs)
                .execute();
    }

    private Map<String, Function<String[], ICommand>> buildMapOfCommands(
            CollectionController collectionController,
            IReader terminalReader) {
        Map<String, Function<String[], ICommand>> commands = new HashMap<>();

        commands.put("help", _ -> new HelpCommand(collectionController));
        commands.put("exit", _ -> new ExitCommand(collectionController));
        commands.put("info", _ -> new InfoCommand(collectionController));
        commands.put("add", _ -> new AddCommand(collectionController,
                                                       terminalReader));
        commands.put("show", _ -> new ShowCommand(collectionController));
        commands.put("remove_by_id",
                     args -> new RemoveByIdCommand(collectionController,
                                                          args));
        commands.put("clear", _ -> new ClearCommand(collectionController));
        commands.put("head", _ -> new HeadCommand(collectionController));
        commands.put("remove_head", _ -> new RemoveHeadCommand(collectionController));

        return commands;
    }

}
