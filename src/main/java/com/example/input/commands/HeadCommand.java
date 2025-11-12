package com.example.input.commands;

import com.example.controller.CollectionController;

public class HeadCommand implements ICommand {

    private final CollectionController collectionController;

    public HeadCommand(CollectionController collectionController) {
        this.collectionController = collectionController;
    }

    @Override
    public void execute() {
        collectionController.head();
    }

}
