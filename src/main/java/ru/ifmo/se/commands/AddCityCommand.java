package ru.ifmo.se.commands;

import ru.ifmo.se.entity.City;
import ru.ifmo.se.io.input.dto.ParamRawData;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.service.CollectionService;
import ru.ifmo.se.service.ParamTypedData;
import ru.ifmo.se.typer.DataTyper;
import ru.ifmo.se.validator.CommandValidator;

public class AddCityCommand extends AbstractAddCityCommand {

    private static final String COMMAND_SIGNATURE = "add {element}";
    private static final String COMMAND_DESCRIPTION = "добавить новый элемент в коллекцию";

    public AddCityCommand(CollectionService collectionService,
                               CommandValidator commandValidator,
                               DataTyper dataTyper,
                               Printer printer) {
        super(collectionService, commandValidator, dataTyper, printer);
    }

    @Override
    public String getCommandSignature() {
        return COMMAND_SIGNATURE;
    }

    @Override
    public String getCommandDescription() {
        return COMMAND_DESCRIPTION;
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
