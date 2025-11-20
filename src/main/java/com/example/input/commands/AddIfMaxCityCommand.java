package com.example.input.commands;

import com.example.CityValidationException;
import com.example.controller.CollectionController;
import com.example.input.dto.ParamRawData;
import com.example.input.readers.IReader;

public class AddIfMaxCityCommand extends AddCityCommand {

    public AddIfMaxCityCommand(CollectionController collectionController,
                               IReader inputReader,
                               ParamRawData paramRawData) {
        super(collectionController, inputReader, paramRawData);
    }

    @Override
    protected void useController() throws CityValidationException {
        super.collectionController.addIfMax(super.cityRawRequestDto,
                                            super.paramRawData);
    }

}
