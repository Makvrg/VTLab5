package ru.ifmo.se.commands;

import ru.ifmo.se.entity.City;
import ru.ifmo.se.io.input.dto.ParamRawData;
import ru.ifmo.se.io.output.Printer;
import ru.ifmo.se.service.CollectionService;
import ru.ifmo.se.service.ParamTypedData;
import ru.ifmo.se.typer.DataTyper;
import ru.ifmo.se.validator.CommandValidator;

public class AddIfMaxCityCommand extends AbstractAddCityCommand {

    public AddIfMaxCityCommand(CollectionService collectionService,
                               CommandValidator commandValidator,
                               DataTyper dataTyper,
                               Printer printer) {
        super("add_if_max {element}",
              "добавить новый элемент в коллекцию, если его значение превышает "
                      + "значение наибольшего элемента этой коллекции",
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
        return collectionService.addIfMax(city);
    }

    @Override
    protected void workWithPrintedText(boolean result) {
        if (result) {
            super.printer.forcePrintln("Новый объект успешно добавлен в коллекцию");
        } else {
            super.printer.forcePrintln("Новый объект не был добавлен в коллекцию");
        }
    }

}
