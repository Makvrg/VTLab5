package com.example.input;

import com.example.controller.CollectionController;
import com.example.input.commands.*;
import com.example.input.readers.IReader;

import java.util.Map;
import java.util.function.Function;

public class CommandDistributor {

    private final Map<String, Function<String[], ICommand>> commands;

    public CommandDistributor(CollectionController collectionController,
                              IReader terminalReader) {

        // TODO Можно сделать через enum
        this.commands =
                Map.of(
                        "help",
                        args -> new HelpCommand(collectionController),
                        "exit",
                        args -> new ExitCommand(collectionController),
                        "info",
                        args -> new InfoCommand(collectionController),
                        "add",
                        args -> new AddCommand(collectionController,
                                                      terminalReader)
                );
    }

    public void distribute(String[] inputArgs) {
        commands.getOrDefault(inputArgs[0],
                              UnknownCommand::new)
                .apply(inputArgs)
                .execute();
    }

}
