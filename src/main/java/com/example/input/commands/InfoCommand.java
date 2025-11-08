package com.example.input.commands;

import com.example.controller.CollectionController;

public class InfoCommand implements ICommand {

    private final CollectionController collectionController;

    public InfoCommand(CollectionController collectionController) {
        this.collectionController = collectionController;
    }

    @Override
    public void execute() {
        collectionController.info();
    }

}
