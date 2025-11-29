package com.example.input.commands;

import com.example.output.IPrinter;
import com.example.service.CollectionService;
import com.example.validator.CommandValidator;
import com.example.validator.exceptions.RemoveAllByPopulationDensityValidationException;

public class RemoveAllByPopulationDensityCommand implements ICommand {

    private final CollectionService collectionService;
    private final CommandValidator commandValidator;
    private final IPrinter printer;
    private final String populationDensity;

    public RemoveAllByPopulationDensityCommand(
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
            commandValidator.validateRemoveAllByPopulationDensity(
                    populationDensity
            );
            if (collectionService.removeAllByPopulationDensityCommand(
                    Long.parseLong(populationDensity))) {
                printer.forcePrintln(
                        "Все City с заданными population density удалены из коллекции");
            } else {
                printer.forcePrintln("City с заданными population density не найдены в коллекции");
            }
        } catch (RemoveAllByPopulationDensityValidationException e) {
            printer.forcePrintln(e.getMessage());
        }
    }

}
