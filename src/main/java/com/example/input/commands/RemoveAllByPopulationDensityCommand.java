package com.example.input.commands;

import com.example.controller.CollectionController;

public class RemoveAllByPopulationDensityCommand implements ICommand {

    private final CollectionController collectionController;
    private final String populationDensity;

    public RemoveAllByPopulationDensityCommand(
            CollectionController collectionController,
            String[] args) {
        this.collectionController = collectionController;
        populationDensity = (args.length > 1) ? args[1] : null;
    }

    @Override
    public void execute() {
        collectionController.removeAllByPopulationDensityCommand(
                populationDensity
        );
    }

}
