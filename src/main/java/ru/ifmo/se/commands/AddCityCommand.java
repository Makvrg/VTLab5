package ru.ifmo.se.commands;

import ru.ifmo.se.entity.City;
import ru.ifmo.se.io.input.dto.ParamRawData;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.service.CollectionService;
import ru.ifmo.se.service.ParamTypedData;
import ru.ifmo.se.typer.DataTyper;
import ru.ifmo.se.validator.CommandValidator;

public class AddCityCommand extends AbstractAddCityCommand {

    public AddCityCommand(CollectionService collectionService,
                          CommandValidator commandValidator,
                          DataTyper dataTyper,
                          Printer printer) {
        super("add {element}", "добавить новый элемент в коллекцию",
                collectionService, commandValidator, dataTyper, printer
        );
    }

    @Override
    protected ParamRawData makeParamRawData(String[] inputArgs) {
        return new ParamRawData();
    }

    @Override
    protected boolean useService(City city,
                              ParamTypedData paramTypedData) {
        return collectionService.add(city);
    }

    @Override
    protected void workWithPrintedText(boolean result) {
        if (result) {
            super.printer.forcePrintln(
                    "Новый объект успешно добавлен в коллекцию");
        } else {
            super.printer.forcePrintln(
                    "Новый объект не был добавлен в коллекцию");
        }
    }
}
