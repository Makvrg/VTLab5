package com.example.input.commands;

import com.example.controller.CollectionController;

public class ShowCommand implements ICommand{

    private final CollectionController collectionController;

    public ShowCommand(CollectionController collectionController) {
        this.collectionController = collectionController;
    }

    @Override
    public void execute() {
        collectionController.show();
    }

}
