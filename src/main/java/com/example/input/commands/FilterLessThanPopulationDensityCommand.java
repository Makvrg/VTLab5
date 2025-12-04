package com.example.input.commands;

import com.example.input.readers.IReader;
import com.example.output.IPrinter;
import com.example.service.CollectionService;
import com.example.validator.CommandValidator;
import com.example.validator.exceptions.FilterLessThanPopulationDensityValidationException;
import lombok.Getter;

public class FilterLessThanPopulationDensityCommand implements ICommand {

    @Getter
    private final String commandSignature =
            "filter_less_than_population_density populationDensity";
    @Getter
    private final String commandDescription =
            "вывести элементы, значение поля populationDensity которых меньше заданного";

    private final CollectionService collectionService;
    private final CommandValidator commandValidator;
    private final IPrinter printer;

    public FilterLessThanPopulationDensityCommand(
            CollectionService collectionService,
            CommandValidator commandValidator,
            IPrinter printer) {
        this.collectionService = collectionService;
        this.commandValidator = commandValidator;
        this.printer = printer;
    }

    @Override
    public void execute(String[] inputArgs, IReader ignoredReader) {
        String populationDensity = (inputArgs.length > 1) ? inputArgs[1] : null;
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
