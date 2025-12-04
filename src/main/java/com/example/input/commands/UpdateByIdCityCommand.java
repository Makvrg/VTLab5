package com.example.input.commands;

import com.example.entity.City;
import com.example.input.dto.ParamRawData;
import com.example.output.IPrinter;
import com.example.service.CollectionService;
import com.example.service.ParamTypedData;
import com.example.typer.DataTyper;
import com.example.validator.CommandValidator;

public class UpdateByIdCityCommand extends AbstractAddCityCommand {

    public UpdateByIdCityCommand(CollectionService collectionService,
                                 CommandValidator commandValidator,
                                 DataTyper dataTyper,
                                 IPrinter printer) {
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
