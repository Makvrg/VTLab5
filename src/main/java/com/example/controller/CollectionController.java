package com.example.controller;

import com.example.CityValidationException;
import com.example.entity.Government;
import com.example.input.dto.*;
import com.example.service.CollectionService;
import com.example.service.ParamTypedData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Date;
import java.util.Map;

public class CollectionController {

    private final CollectionService collectionService;
    private final CityRawRequestDtoValidator cityAddRequestValidator;

    public CollectionController(CollectionService collectionService,
                                CityRawRequestDtoValidator cityRawRequestDtoValidator) {
        this.collectionService = collectionService;
        this.cityAddRequestValidator = cityRawRequestDtoValidator;
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
    public void controlRawActionData(ParamRawData paramRawData)
            throws RawActionDataValidationException {
        if (paramRawData.getId() != null) {
            try {
                Long.parseLong(paramRawData.getId());
            } catch (NumberFormatException | NullPointerException e) {
                throw new RawActionDataValidationException(
                        "Аргумент id должен быть целым числом"
                );
            }
            collectionService.servicingRawActionData(paramRawData);
        }
    }

    private AbstractMap.SimpleImmutableEntry<CityTypedRequestDto, ParamTypedData> controlInputCity(
            CityRawRequestDto cityRawRequestDto,
            ParamRawData paramRawData) throws CityValidationException {

        Map<String, String> errorsWithMessages =
                cityAddRequestValidator.validate(cityRawRequestDto);

        if (errorsWithMessages.isEmpty()) {
            ParamTypedData paramTypedData = new ParamTypedData();

            if (paramRawData.getId() != null) {
                paramTypedData.setId(Long.valueOf(paramRawData.getId()));
            }

            Date birthday = null;
            if (cityRawRequestDto.getGovernor().getBirthday() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                sdf.setLenient(false);
                try {
                    birthday = sdf.parse(cityRawRequestDto.getGovernor()
                                                            .getBirthday());
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
            CityTypedRequestDto cityTypedRequestDto =
                    new CityTypedRequestDto(
                            cityRawRequestDto.getName(),
                            new CoordTypedRequestDto(
                                    Double.parseDouble(cityRawRequestDto.getCoordinates()
                                                                          .getX()),
                                    Float.parseFloat(cityRawRequestDto.getCoordinates()
                                                                        .getY())
                            ),
                            Long.valueOf(cityRawRequestDto.getArea()),
                            Integer.valueOf(cityRawRequestDto.getPopulation()),
                            (cityRawRequestDto.getMetersAboveSeaLevel() == null)
                                    ? null
                                    : Float.valueOf(cityRawRequestDto.getMetersAboveSeaLevel()),
                            Long.parseLong(cityRawRequestDto.getPopulationDensity()),
                            (cityRawRequestDto.getAgglomeration() == null)
                                    ? null
                                    : Integer.valueOf(cityRawRequestDto.getAgglomeration()),
                            Government.fromString(cityRawRequestDto.getGovernment()),
                            new HumanTypedRequestDto(
                                    Double.parseDouble(cityRawRequestDto.getGovernor()
                                                                          .getHeight()),
                                    birthday
                            )
                    );
            return new AbstractMap.SimpleImmutableEntry<>(
                    cityTypedRequestDto,
                    paramTypedData
            );
        } else {
            throw new CityValidationException(errorsWithMessages);
        }
    }


    public void add(CityRawRequestDto cityRawRequestDto,
                    ParamRawData paramRawData) throws CityValidationException {
        AbstractMap.SimpleImmutableEntry<CityTypedRequestDto, ParamTypedData> entry =
                controlInputCity(cityRawRequestDto, paramRawData);
        collectionService.add(entry.getKey(), entry.getValue());
    }

    public void addIfMax(CityRawRequestDto cityRawRequestDto,
                    ParamRawData paramRawData) throws CityValidationException {
        AbstractMap.SimpleImmutableEntry<CityTypedRequestDto, ParamTypedData> entry =
                controlInputCity(cityRawRequestDto, paramRawData);
        collectionService.addIfMax(entry.getKey(), entry.getValue());
    }

    public void updateById(CityRawRequestDto cityRawRequestDto,
                         ParamRawData paramRawData) throws CityValidationException {
        AbstractMap.SimpleImmutableEntry<CityTypedRequestDto, ParamTypedData> entry =
                controlInputCity(cityRawRequestDto, paramRawData);
        collectionService.updateById(entry.getKey(), entry.getValue());
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
