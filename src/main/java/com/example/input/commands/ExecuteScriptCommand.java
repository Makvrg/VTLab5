package com.example.input.commands;

import com.example.controller.CollectionController;

public class ExecuteScriptCommand implements ICommand {

    private final CollectionController collectionController;
    private final String fileName;

    public ExecuteScriptCommand(
            CollectionController collectionController,
            String[] args) {
        this.collectionController = collectionController;
        fileName = (args.length > 1) ? args[1] : null;
    }

    @Override
    public void execute() {
//        collectionController.(
//                fileName
//        );
    }

}
