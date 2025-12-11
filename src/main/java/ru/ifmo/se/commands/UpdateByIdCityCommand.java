package ru.ifmo.se.commands;

import ru.ifmo.se.entity.City;
import ru.ifmo.se.io.input.dto.ParamRawData;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.service.CollectionService;
import ru.ifmo.se.service.ParamTypedData;
import ru.ifmo.se.typer.DataTyper;
import ru.ifmo.se.validator.CommandValidator;

public class UpdateByIdCityCommand extends AbstractAddCityCommand {

    public UpdateByIdCityCommand(CollectionService collectionService,
                                 CommandValidator commandValidator,
                                 DataTyper dataTyper,
                                 Printer printer) {
        super("update id {element}",
              "обновить значение элемента коллекции, id которого равен заданному",
              collectionService, commandValidator, dataTyper, printer)
        ;
    }

    @Override
    protected ParamRawData makeParamRawData(String[] inputArgs) {
        ParamRawData paramRawData = new ParamRawData();
        paramRawData.setId(
                (inputArgs.length == 1 || inputArgs[1] == null) ? "" : inputArgs[1]
        );
        return paramRawData;
    }

    @Override
    protected boolean useService(City city,
                                 ParamTypedData paramTypedData) {
        return collectionService.updateById(city, paramTypedData);
    }

    @Override
    protected void workWithPrintedText(boolean result) {
        if (result) {
            super.printer.forcePrintln("Объект успешно обновлён");
        } else {
            super.printer.forcePrintln(
                    "Объект не был обновлён, так как в коллекции нет объекта с данным id");
        }
    }

}
