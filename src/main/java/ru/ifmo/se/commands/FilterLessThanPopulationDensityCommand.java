package ru.ifmo.se.commands;

import ru.ifmo.se.entity.City;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.formatter.OutputStringFormatter;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.service.CollectionService;
import ru.ifmo.se.validator.CommandValidator;
import ru.ifmo.se.validator.exceptions.FilterLessThanPopulationDensityValidationException;

import java.util.List;

public class FilterLessThanPopulationDensityCommand extends Command {

    private final CollectionService collectionService;
    private final CommandValidator commandValidator;
    private final Printer printer;
    private final OutputStringFormatter formatter;

    public FilterLessThanPopulationDensityCommand(
            CollectionService collectionService,
            CommandValidator commandValidator,
            Printer printer,
            OutputStringFormatter formatter) {
        super("filter_less_than_population_density populationDensity",
                "вывести элементы, значение поля populationDensity которых меньше заданного"
        );
        this.collectionService = collectionService;
        this.commandValidator = commandValidator;
        this.printer = printer;
        this.formatter = formatter;
    }

    @Override
    public void execute(String[] inputArgs, Reader ignoredReader) {
        String populationDensity = (inputArgs.length > 1) ? inputArgs[1] : null;
        try {
            commandValidator.validateFilterLessThanPopulationDensity(
                    populationDensity
            );
            List<City> filteredCityList =
                    collectionService.filterLessThanPopulationDensity(
                            Long.parseLong(populationDensity)
                    );
            if (!filteredCityList.isEmpty()) {
                printer.forcePrintln("Искомые объекты City:\n"
                        + formatter.formatCityList(filteredCityList)
                );
            } else {
                printer.forcePrintln("Искомых элементов в коллекции не найдено");
            }
        } catch (FilterLessThanPopulationDensityValidationException e) {
            printer.forcePrintln(e.getMessage());
        }
    }
}
