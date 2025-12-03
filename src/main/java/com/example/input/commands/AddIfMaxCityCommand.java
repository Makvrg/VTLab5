package com.example.input.commands;

import com.example.entity.City;
import com.example.input.dto.ParamRawData;
import com.example.output.IPrinter;
import com.example.service.CollectionService;
import com.example.service.ParamTypedData;
import com.example.typer.DataTyper;
import com.example.validator.CommandValidator;

public class AddIfMaxCityCommand extends AbstractAddCityCommand {

    public AddIfMaxCityCommand(CollectionService collectionService,
                               CommandValidator commandValidator,
                               DataTyper dataTyper,
                               IPrinter printer) {
        super(collectionService, commandValidator,
              dataTyper, printer);
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
