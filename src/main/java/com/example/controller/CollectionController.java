package com.example.controller;

import com.example.CityValidationException;
import com.example.entity.Government;
import com.example.input.dto.CityAddDto;
import com.example.input.dto.CityAddRequestDto;
import com.example.input.dto.CoordinatesDto;
import com.example.input.dto.HumanDto;
import com.example.service.AddMode;
import com.example.service.CollectionService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CollectionController {

    private final CollectionService collectionService;
    private final CityAddRequestDtoValidator cityAddRequestValidator;

    public CollectionController(CollectionService collectionService,
                                CityAddRequestDtoValidator cityAddRequestDtoValidator) {
        this.collectionService = collectionService;
        this.cityAddRequestValidator = cityAddRequestDtoValidator;
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

    public void add(CityAddRequestDto cityAddRequestDto,
                    AddMode addMode) throws CityValidationException {

        Map<String, String> errorsWithMessages =
                cityAddRequestValidator.validate(cityAddRequestDto);

        if (errorsWithMessages.isEmpty()) {
            Date birthday = null;
            if (cityAddRequestDto.getGovernorRequestDto().getBirthday() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                sdf.setLenient(false);
                try {
                    birthday = sdf.parse(cityAddRequestDto.getGovernorRequestDto().getBirthday());
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
            CityAddDto cityAddDto =
                    new CityAddDto(
                            cityAddRequestDto.getName(),
                            new CoordinatesDto(
                                    Double.parseDouble(cityAddRequestDto.getCoordinatesRequestDto()
                                                                        .getX()),
                                    Float.parseFloat(cityAddRequestDto.getCoordinatesRequestDto()
                                                                      .getY())
                            ),
                            Long.valueOf(cityAddRequestDto.getArea()),
                            Integer.valueOf(cityAddRequestDto.getPopulation()),
                            (cityAddRequestDto.getMetersAboveSeaLevel() == null)
                                    ? null
                                    : Float.valueOf(cityAddRequestDto.getMetersAboveSeaLevel()),
                            Long.parseLong(cityAddRequestDto.getPopulationDensity()),
                            (cityAddRequestDto.getAgglomeration() == null)
                                    ? null
                                    : Integer.valueOf(cityAddRequestDto.getAgglomeration()),
                            Government.fromString(cityAddRequestDto.getGovernment()),
                            new HumanDto(
                                    Double.parseDouble(cityAddRequestDto.getGovernorRequestDto()
                                                                        .getHeight()),
                                    birthday
                            )
                    );
            collectionService.add(cityAddDto, addMode);
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

}
