package com.example.controller;

import com.example.CityValidationException;
import com.example.entity.Government;
import com.example.input.dto.CityAddDto;
import com.example.input.dto.CityAddRequestDto;
import com.example.input.dto.CoordinatesDto;
import com.example.input.dto.HumanDto;
import com.example.service.CollectionService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class CollectionController {

    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
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

    public void add(CityAddRequestDto cityAddRequestDto)
            throws CityValidationException {

        Map<String, String> errorsWithMessages = new LinkedHashMap<>();

        if (cityAddRequestDto.getName() == null
                || cityAddRequestDto.getName().isBlank()) {
            errorsWithMessages.put("name", "Название города не должно быть пустым");
        }
        try {
            Double.parseDouble(cityAddRequestDto.getCoordinatesRequestDto().getX());
        } catch (NumberFormatException | NullPointerException e) {
            errorsWithMessages.put("x", "Координата x должна быть вещественным числом");
        }
        try {
            Float.parseFloat(cityAddRequestDto.getCoordinatesRequestDto().getY());
        } catch (NumberFormatException | NullPointerException e) {
            errorsWithMessages.put("y", "Координата y должна быть вещественным числом");
        }
        if (cityAddRequestDto.getArea() == null) {
            errorsWithMessages.put("area", "Площадь города должна быть целым числом");
        } else {
            try {
                Long.valueOf(cityAddRequestDto.getArea());
            } catch (NumberFormatException e) {
                errorsWithMessages.put("area",
                                       "Площадь города должна быть целым числом");
            }
        }
        if (cityAddRequestDto.getPopulation() == null) {
            errorsWithMessages.put(
                    "population",
                    "Численность населения города должна быть целым числом");
        } else {
            try {
                Integer.valueOf(cityAddRequestDto.getPopulation());
            } catch (NumberFormatException e) {
                errorsWithMessages.put(
                        "population",
                        "Численность населения города должна быть целым числом");
            }
        }
        if (cityAddRequestDto.getMetersAboveSeaLevel() != null) {
            try {
                Float.valueOf(cityAddRequestDto.getMetersAboveSeaLevel());
            } catch (NumberFormatException e) {
                errorsWithMessages.put(
                        "metersAboveSeaLevel",
                        "Число метров над уровнем моря должно быть вещественным числом");
            }
        }
        try {
            Long.parseLong(cityAddRequestDto.getPopulationDensity());
        } catch (NumberFormatException | NullPointerException e) {
            errorsWithMessages.put(
                    "populationDensity",
                    "Плотность населения города должна быть целым числом");
        }
        if (cityAddRequestDto.getAgglomeration() != null) {
            try {
                Integer.valueOf(cityAddRequestDto.getAgglomeration());
            } catch (NumberFormatException e) {
                errorsWithMessages.put(
                        "agglomeration",
                        "Численность населения агломерации должна быть целым числом");
            }
        }
        if (cityAddRequestDto.getGovernment() == null
                || !Arrays.stream(Government.values())
                         .map(Government::getTitle)
                         .toList()
                         .contains(cityAddRequestDto.getGovernment())) {
            errorsWithMessages.put(
                    "government",
                    "Тип правления города должен быть одним из предложенных");
        }
        try {
            Double.parseDouble(cityAddRequestDto.getGovernorRequestDto().getHeight());
        } catch (NumberFormatException | NullPointerException e) {
            errorsWithMessages.put("height",
                    "Рост губернатора должен быть вещественным числом в метрах");
        }
        if (cityAddRequestDto.getGovernorRequestDto().getBirthday() != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                sdf.setLenient(false);
                sdf.parse(cityAddRequestDto.getGovernorRequestDto().getBirthday());
            } catch (ParseException e) {
                errorsWithMessages.put("birthday",
                        "Дата и время рождения губернатора должны иметь формат дд-ММ-гггг ЧЧ:мм:сс");
            }
        }

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
            collectionService.add(cityAddDto);
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

}
