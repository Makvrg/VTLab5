package com.example.input.commands;

import com.example.entity.City;
import com.example.input.dto.ParamRawData;
import com.example.input.readers.IReader;
import com.example.service.CollectionService;
import com.example.service.ParamTypedData;
import com.example.typer.DataTyper;
import com.example.validator.CommandValidator;

public class UpdateByIdCityCommand extends AbstractAddCityCommand {

    public UpdateByIdCityCommand(CollectionService collectionService,
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
        return collectionService.updateById(city, paramTypedData);
    }

    @Override
    protected void workWithPrintedText(boolean result) {
        if (result) {
            System.out.println("Объект успешно обновлён");
        } else {
            System.out.println(
                    "Объект не был обновлён, так как в коллекции нет объекта с данным id");
        }
    }

}
