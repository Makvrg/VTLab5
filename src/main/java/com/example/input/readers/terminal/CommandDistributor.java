package com.example.input.readers.terminal;

import com.example.controller.CollectionController;
import com.example.input.commands.*;


import java.util.Map;
import java.util.function.Function;

public class CommandDistributor {

    private final CollectionController collectionController;
    private final Map<String, Function<String[], ICommand>> commands;

    public CommandDistributor(CollectionController collectionController) {
        this.collectionController = collectionController;

        this.commands =
                Map.of(
                        "help",
                        args -> new HelpCommand(collectionController),
                        "exit",
                        args -> new ExitCommand(collectionController),
                        "info",
                        args -> new InfoCommand(collectionController)
                );
    }

    public void distribute(String[] inputArgs) {
        commands.getOrDefault(inputArgs[0],
                        UnknownCommand::new)
                .apply(inputArgs)
                .execute();
    }

}
