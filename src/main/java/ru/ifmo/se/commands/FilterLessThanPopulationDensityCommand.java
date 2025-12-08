package ru.ifmo.se.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.Printer;
import ru.ifmo.se.service.CollectionService;
import ru.ifmo.se.validator.CommandValidator;
import ru.ifmo.se.validator.exceptions.FilterLessThanPopulationDensityValidationException;

@RequiredArgsConstructor
public class FilterLessThanPopulationDensityCommand implements Command {

    @Getter
    private final String commandSignature =
            "filter_less_than_population_density populationDensity";
    @Getter
    private final String commandDescription =
            "вывести элементы, значение поля populationDensity которых меньше заданного";

    private final CollectionService collectionService;
    private final CommandValidator commandValidator;
    private final Printer printer;

    @Override
    public void execute(String[] inputArgs, Reader ignoredReader) {
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
