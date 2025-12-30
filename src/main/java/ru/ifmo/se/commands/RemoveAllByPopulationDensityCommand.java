package ru.ifmo.se.commands;

import lombok.RequiredArgsConstructor;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.service.CollectionService;
import ru.ifmo.se.validator.CommandValidator;
import ru.ifmo.se.validator.exceptions.RemoveAllByPopulationDensityValidationException;

@RequiredArgsConstructor
public class RemoveAllByPopulationDensityCommand implements Command {

    private static final String COMMAND_SIGNATURE =
            "remove_all_by_population_density populationDensity";

    private static final String COMMAND_DESCRIPTION =
            "удалить из коллекции все элементы, значение поля "
                    + "populationDensity которого эквивалентно заданному";

    private final CollectionService collectionService;
    private final CommandValidator commandValidator;
    private final Printer printer;

    @Override
    public String getCommandSignature() {
        return COMMAND_SIGNATURE;
    }

    @Override
    public String getCommandDescription() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    public void execute(String[] inputArgs, Reader ignoredReader) {
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
