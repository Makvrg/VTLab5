package com.example.controller;

import com.example.CityValidationException;
import com.example.entity.Government;
import com.example.input.dto.*;
import com.example.service.ActionData;
import com.example.service.CollectionService;
import com.example.service.InputMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CollectionController {

    private final CollectionService collectionService;
    private final CityInputRequestDtoValidator cityAddRequestValidator;

    public CollectionController(CollectionService collectionService,
                                CityInputRequestDtoValidator cityInputRequestDtoValidator) {
        this.collectionService = collectionService;
        this.cityAddRequestValidator = cityInputRequestDtoValidator;
    }

    public void help() {
        collectionService.help();
    }

    public void exit() {
        collectionService.exit();
    }

    public void info() {
        collectionService.info();
    }

    // TODO Выделить в отдельный валидатор
    public void controlInputActionData(InputActionData inputActionData)
            throws InputActionDataValidationException {
        if (inputActionData.getId() != null) {
            try {
                Long.parseLong(inputActionData.getId());
            } catch (NumberFormatException | NullPointerException e) {
                throw new InputActionDataValidationException(
                        "Аргумент id должен быть целым числом"
                );
            }
            collectionService.servicingInputActionData(inputActionData);
        }
    }

    public void controlInputCity(CityInputRequestDto cityInputRequestDto,
                                 InputMode inputMode,
                                 InputActionData inputActionData) throws CityValidationException {

        Map<String, String> errorsWithMessages =
                cityAddRequestValidator.validate(cityInputRequestDto);

        if (errorsWithMessages.isEmpty()) {
            ActionData actionData = new ActionData();

            if (inputActionData.getId() != null) {
                actionData.setId(Long.valueOf(inputActionData.getId()));
            }

            Date birthday = null;
            if (cityInputRequestDto.getGovernorRequestDto().getBirthday() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                sdf.setLenient(false);
                try {
                    birthday = sdf.parse(cityInputRequestDto.getGovernorRequestDto().getBirthday());
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
            CityInputDto cityInputDto =
                    new CityInputDto(
                            cityInputRequestDto.getName(),
                            new CoordinatesDto(
                                    Double.parseDouble(cityInputRequestDto.getCoordinatesRequestDto()
                                                                        .getX()),
                                    Float.parseFloat(cityInputRequestDto.getCoordinatesRequestDto()
                                                                      .getY())
                            ),
                            Long.valueOf(cityInputRequestDto.getArea()),
                            Integer.valueOf(cityInputRequestDto.getPopulation()),
                            (cityInputRequestDto.getMetersAboveSeaLevel() == null)
                                    ? null
                                    : Float.valueOf(cityInputRequestDto.getMetersAboveSeaLevel()),
                            Long.parseLong(cityInputRequestDto.getPopulationDensity()),
                            (cityInputRequestDto.getAgglomeration() == null)
                                    ? null
                                    : Integer.valueOf(cityInputRequestDto.getAgglomeration()),
                            Government.fromString(cityInputRequestDto.getGovernment()),
                            new HumanDto(
                                    Double.parseDouble(cityInputRequestDto.getGovernorRequestDto()
                                                                        .getHeight()),
                                    birthday
                            )
                    );
            collectionService.servicingInputCity(cityInputDto,
                                                 inputMode,
                                                 actionData);
        } else {
            throw new CityValidationException(errorsWithMessages);
        }
    }

    public void show() {
        collectionService.show();
    }

    public void removeById(String id) {
        if (id == null) {
            System.out.println("Не передан параметр id");
            return;
        }
        try {
            collectionService.removeById(Long.valueOf(id));
        } catch (NumberFormatException e) {
            System.out.println("Переданный аргумент id не является целым числом");
        }
    }

    public void clear() {
        collectionService.clear();
    }

    public void head() {
        collectionService.head();
    }

    public void removeHead() {
        collectionService.removeHead();
    }

    public void removeAllByPopulationDensityCommand(String populationDensity) {
        try {
            collectionService.removeAllByPopulationDensityCommand(
                    Long.parseLong(populationDensity)
            );
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("Плотность населения города должна быть целым числом");
        }
    }

    public void filterLessThanPopulationDensity(String populationDensity) {
        try {
            collectionService.filterLessThanPopulationDensity(
                    Long.parseLong(populationDensity)
            );
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("Плотность населения города должна быть целым числом");
        }
    }

    public void printFieldDescendingGovernment() {
        collectionService.printFieldDescendingGovernment();
    }

}
