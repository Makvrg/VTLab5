package com.example.input.commands;

import com.example.controller.CollectionController;

public class RemoveByIdCommand implements ICommand {

    private final CollectionController collectionController;
    private final String id;

    public RemoveByIdCommand(CollectionController collectionController,
                             String[] args) {
        this.collectionController = collectionController;
        id = (args.length > 1) ? args[1] : null;
    }

    @Override
    public void execute() {
        collectionController.removeById(id);
    }

}
