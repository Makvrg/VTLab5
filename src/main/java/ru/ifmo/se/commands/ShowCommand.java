package ru.ifmo.se.commands;

import lombok.RequiredArgsConstructor;
import ru.ifmo.se.entity.City;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.formatter.OutputStringFormatter;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.service.CollectionService;

import java.util.List;

@RequiredArgsConstructor
public class ShowCommand implements Command {

    private static final String COMMAND_SIGNATURE = "show";

    private static final String COMMAND_DESCRIPTION =
            "вывести в стандартный поток вывода все элементы "
                    + "коллекции в строковом представлении";

    private final CollectionService collectionService;
    private final Printer printer;
    private final OutputStringFormatter formatter;

    @Override
    public String getCommandSignature() {
        return COMMAND_SIGNATURE;
    }

    @Override
    public String getCommandDescription() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    public void execute(String[] ignoredArgs, Reader ignoredReader) {
        List<City> cities = collectionService.show();
        if (!cities.isEmpty()) {
            printer.forcePrintln("Содержимые в коллекции объекты City:\n"
                    + formatter.formatCityList(cities));
        } else {
            printer.forcePrintln("Коллекция пуста");
        }
    }
}
