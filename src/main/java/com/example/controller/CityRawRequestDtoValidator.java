package com.example.controller;

import com.example.entity.Government;
import com.example.input.dto.CityRawRequestDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class CityRawRequestDtoValidator {

    public Map<String, String> validate(CityRawRequestDto cityRawRequestDto) {
        Map<String, String> errorsWithMessages = new LinkedHashMap<>();

        if (cityRawRequestDto.getName() == null
                || cityRawRequestDto.getName().isBlank()) {
            errorsWithMessages.put("name", "Название города не должно быть пустым");
        }
        try {
            Double.parseDouble(cityRawRequestDto.getCoordinates()
                                                .getX());
        } catch (NumberFormatException | NullPointerException e) {
            errorsWithMessages.put("x",
                    "Координата x должна быть вещественным числом");
        }
        try {
            Float.parseFloat(cityRawRequestDto.getCoordinates()
                                              .getY());
        } catch (NumberFormatException | NullPointerException e) {
            errorsWithMessages.put("y",
                    "Координата y должна быть вещественным числом");
        }
        if (cityRawRequestDto.getArea() == null) {
            errorsWithMessages.put("area",
                    "Площадь города должна быть целым числом");
        } else {
            try {
                Long.valueOf(cityRawRequestDto.getArea());
            } catch (NumberFormatException e) {
                errorsWithMessages.put("area",
                        "Площадь города должна быть целым числом");
            }
        }
        if (cityRawRequestDto.getPopulation() == null) {
            errorsWithMessages.put(
                    "population",
                    "Численность населения города должна быть целым числом");
        } else {
            try {
                Integer.valueOf(cityRawRequestDto.getPopulation());
            } catch (NumberFormatException e) {
                errorsWithMessages.put(
                        "population",
                        "Численность населения города должна быть целым числом");
            }
        }
        if (cityRawRequestDto.getMetersAboveSeaLevel() != null) {
            try {
                Float.valueOf(cityRawRequestDto.getMetersAboveSeaLevel());
            } catch (NumberFormatException e) {
                errorsWithMessages.put(
                        "metersAboveSeaLevel",
                        "Число метров над уровнем моря должно быть вещественным числом");
            }
        }
        try {
            Long.parseLong(cityRawRequestDto.getPopulationDensity());
        } catch (NumberFormatException | NullPointerException e) {
            errorsWithMessages.put(
                    "populationDensity",
                    "Плотность населения города должна быть целым числом");
        }
        if (cityRawRequestDto.getAgglomeration() != null) {
            try {
                Integer.valueOf(cityRawRequestDto.getAgglomeration());
            } catch (NumberFormatException e) {
                errorsWithMessages.put(
                        "agglomeration",
                        "Численность населения агломерации должна быть целым числом");
            }
        }
        if (cityRawRequestDto.getGovernment() == null
                || !Arrays.stream(Government.values())
                .map(Government::getTitle)
                .toList()
                .contains(cityRawRequestDto.getGovernment())) {
            errorsWithMessages.put(
                    "government",
                    "Тип правления города должен быть одним из предложенных");
        }
        try {
            Double.parseDouble(cityRawRequestDto.getGovernor()
                                                .getHeight());
        } catch (NumberFormatException | NullPointerException e) {
            errorsWithMessages.put("height",
                    "Рост губернатора должен быть вещественным числом в метрах");
        }
        if (cityRawRequestDto.getGovernor().getBirthday() != null) {
            try {
                SimpleDateFormat sdf =
                        new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                sdf.setLenient(false);
                sdf.parse(cityRawRequestDto.getGovernor().getBirthday());
            } catch (ParseException e) {
                errorsWithMessages.put("birthday",
                        "Дата и время рождения губернатора "
                        + "должны иметь формат дд-ММ-гггг ЧЧ:мм:сс");
            }
        }
        return errorsWithMessages;
    }

}
