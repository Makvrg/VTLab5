package com.example.input.commands;

import com.example.controller.CollectionController;

public class ClearCommand implements ICommand {

    private final CollectionController collectionController;

    public ClearCommand(CollectionController collectionController) {
        this.collectionController = collectionController;
    }

    @Override
    public void execute() {
        collectionController.clear();
    }

}
