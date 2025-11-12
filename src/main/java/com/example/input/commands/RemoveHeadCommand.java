package com.example.input.commands;

import com.example.controller.CollectionController;

public class RemoveHeadCommand implements ICommand {

    private final CollectionController collectionController;

    public RemoveHeadCommand(CollectionController collectionController) {
        this.collectionController = collectionController;
    }

    @Override
    public void execute() {
        collectionController.removeHead();
    }

}
