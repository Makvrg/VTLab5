package com.example.input.commands;

import com.example.entity.City;
import com.example.input.dto.ParamRawData;
import com.example.input.readers.IReader;
import com.example.service.CollectionService;
import com.example.service.ParamTypedData;
import com.example.typer.DataTyper;
import com.example.validator.CommandValidator;

public class AddCityCommand extends AbstractAddCityCommand {

    public AddCityCommand(CollectionService collectionService,
                               CommandValidator commandValidator,
                               DataTyper dataTyper,
                               IReader inputReader,
                               ParamRawData paramRawData) {
        super(collectionService, commandValidator,
              dataTyper, inputReader, paramRawData);
    }

    @Override
    protected boolean useService(City city,
                              ParamTypedData paramTypedData) {
        return collectionService.add(city);
    }

    @Override
    protected void workWithPrintedText(boolean result) {
        if (result) {
            System.out.println("Новый объект успешно добавлен в коллекцию");
        } else {
            System.out.println("Новый объект не был добавлен в коллекцию");
        }
    }

}
