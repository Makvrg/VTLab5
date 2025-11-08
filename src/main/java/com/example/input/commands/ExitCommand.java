package com.example.input.commands;

import com.example.controller.CollectionController;

public class ExitCommand implements ICommand {

    private final CollectionController collectionController;

    public ExitCommand(CollectionController collectionController) {
        this.collectionController = collectionController;
    }

    @Override
    public void execute() {
        collectionController.exit();
    }

}
