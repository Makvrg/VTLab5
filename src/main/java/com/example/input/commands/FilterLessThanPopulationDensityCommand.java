package com.example.input.commands;

import com.example.output.IPrinter;
import com.example.service.CollectionService;
import com.example.validator.CommandValidator;
import com.example.validator.exceptions.FilterLessThanPopulationDensityValidationException;

public class FilterLessThanPopulationDensityCommand implements ICommand {

    private final CollectionService collectionService;
    private final CommandValidator commandValidator;
    private final IPrinter printer;
    private final String populationDensity;

    public FilterLessThanPopulationDensityCommand(
            CollectionService collectionService,
            CommandValidator commandValidator,
            IPrinter printer,
            String[] args) {
        this.collectionService = collectionService;
        this.commandValidator = commandValidator;
        this.printer = printer;
        populationDensity = (args.length > 1) ? args[1] : null;
    }

    @Override
    public void execute() {
        try {
            commandValidator.validateFilterLessThanPopulationDensity(
                    populationDensity
            );
            printer.forcePrint(
                    collectionService.filterLessThanPopulationDensity(
                            Long.parseLong(populationDensity)
                    )
            );
        } catch (FilterLessThanPopulationDensityValidationException e) {
            printer.forcePrintln(e.getMessage());
        }
    }

}
