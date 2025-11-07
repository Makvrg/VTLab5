package com.example.input.terminal;

import com.example.controller.CollectionController;
import com.example.input.commands.Command;
import com.example.input.commands.HelpCommand;


import java.util.Map;

public class CommandRegulator {

    private String[] inputArgs;
    private final CollectionController collectionController;
    private final Map<String, Command> commands;

    public CommandRegulator(String[] inputArgs,
                            CollectionController collectionController) {
        this.inputArgs = inputArgs;
        this.collectionController = collectionController;

        this.commands =
                Map.of(
                        "help", new HelpCommand(collectionController)
                );
    }

    public void regulate() {
        commands.getOrDefault(inputArgs[0],
                        () -> System.out.println("Введена неизвестная команда"))
                .execute();
    }

}
