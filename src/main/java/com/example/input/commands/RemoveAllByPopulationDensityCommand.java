package com.example.input.commands;

import com.example.input.readers.IReader;
import com.example.output.IPrinter;
import com.example.service.CollectionService;
import com.example.validator.CommandValidator;
import com.example.validator.exceptions.RemoveAllByPopulationDensityValidationException;

public class RemoveAllByPopulationDensityCommand implements ICommand {

    private final CollectionService collectionService;
    private final CommandValidator commandValidator;
    private final IPrinter printer;

    public RemoveAllByPopulationDensityCommand(
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
