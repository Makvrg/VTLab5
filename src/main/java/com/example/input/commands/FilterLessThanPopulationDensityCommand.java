package com.example.input.commands;

import com.example.service.CollectionService;
import com.example.validator.CommandValidator;
import com.example.validator.exceptions.FilterLessThanPopulationDensityValidationException;

public class FilterLessThanPopulationDensityCommand implements ICommand {

    private final CollectionService collectionService;
    private final CommandValidator commandValidator;
    private final String populationDensity;

    public FilterLessThanPopulationDensityCommand(
            CollectionService collectionService,
            CommandValidator commandValidator,
            String[] args) {
        this.collectionService = collectionService;
        this.commandValidator = commandValidator;
        populationDensity = (args.length > 1) ? args[1] : null;
    }

    @Override
    public void execute() {
        try {
            commandValidator.validateFilterLessThanPopulationDensity(
                    populationDensity
            );
            System.out.println(collectionService.filterLessThanPopulationDensity(
                    Long.parseLong(populationDensity)
                    )
            );
        } catch (FilterLessThanPopulationDensityValidationException e) {
            System.out.println(e.getMessage());
        }
    }

}
