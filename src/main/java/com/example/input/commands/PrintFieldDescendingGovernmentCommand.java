package com.example.input.commands;

import com.example.controller.CollectionController;

public class PrintFieldDescendingGovernmentCommand implements ICommand {

    private final CollectionController collectionController;

    public PrintFieldDescendingGovernmentCommand(
            CollectionController collectionController) {
        this.collectionController = collectionController;
    }

    @Override
    public void execute() {
        collectionController.printFieldDescendingGovernment();
    }

}
