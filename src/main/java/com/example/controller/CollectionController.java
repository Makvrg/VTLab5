package com.example.controller;

import com.example.entity.Government;
import com.example.input.dto.CityAddRequestDto;
import com.example.service.CollectionService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
            Double.parseDouble(cityAddRequestDto.getCoordinates().getX());
        } catch (NumberFormatException | NullPointerException e) {
            errorsWithMessages.put("x", "Координата x должна быть вещественным числом");
        }
        try {
            Float.parseFloat(cityAddRequestDto.getCoordinates().getY());
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
            Double.parseDouble(cityAddRequestDto.getGovernor().getHeight());
        } catch (NumberFormatException | NullPointerException e) {
            errorsWithMessages.put("height",
                    "Рост губернатора должен быть вещественным числом в метрах");
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            sdf.setLenient(false);
            sdf.parse(cityAddRequestDto.getGovernor().getBirthday());
        } catch (ParseException | NullPointerException e) {
            errorsWithMessages.put("birthday",
                "Дата и время рождения губернатора должны иметь формат дд-ММ-гггг ЧЧ:мм:сс");
        }

        if (errorsWithMessages.isEmpty()) {
            collectionService.add(cityAddRequestDto);
        } else {
            throw new CityValidationException(errorsWithMessages);
        }

    }

}
