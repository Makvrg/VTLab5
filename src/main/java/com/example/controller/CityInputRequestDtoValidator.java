package com.example.controller;

import com.example.entity.Government;
import com.example.input.dto.CityInputRequestDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class CityInputRequestDtoValidator {

    public Map<String, String> validate(CityInputRequestDto cityInputRequestDto) {
        Map<String, String> errorsWithMessages = new LinkedHashMap<>();

        if (cityInputRequestDto.getName() == null
                || cityInputRequestDto.getName().isBlank()) {
            errorsWithMessages.put("name", "Название города не должно быть пустым");
        }
        try {
            Double.parseDouble(cityInputRequestDto.getCoordinatesRequestDto()
                                                .getX());
        } catch (NumberFormatException | NullPointerException e) {
            errorsWithMessages.put("x",
                    "Координата x должна быть вещественным числом");
        }
        try {
            Float.parseFloat(cityInputRequestDto.getCoordinatesRequestDto()
                                              .getY());
        } catch (NumberFormatException | NullPointerException e) {
            errorsWithMessages.put("y",
                    "Координата y должна быть вещественным числом");
        }
        if (cityInputRequestDto.getArea() == null) {
            errorsWithMessages.put("area",
                    "Площадь города должна быть целым числом");
        } else {
            try {
                Long.valueOf(cityInputRequestDto.getArea());
            } catch (NumberFormatException e) {
                errorsWithMessages.put("area",
                        "Площадь города должна быть целым числом");
            }
        }
        if (cityInputRequestDto.getPopulation() == null) {
            errorsWithMessages.put(
                    "population",
                    "Численность населения города должна быть целым числом");
        } else {
            try {
                Integer.valueOf(cityInputRequestDto.getPopulation());
            } catch (NumberFormatException e) {
                errorsWithMessages.put(
                        "population",
                        "Численность населения города должна быть целым числом");
            }
        }
        if (cityInputRequestDto.getMetersAboveSeaLevel() != null) {
            try {
                Float.valueOf(cityInputRequestDto.getMetersAboveSeaLevel());
            } catch (NumberFormatException e) {
                errorsWithMessages.put(
                        "metersAboveSeaLevel",
                        "Число метров над уровнем моря должно быть вещественным числом");
            }
        }
        try {
            Long.parseLong(cityInputRequestDto.getPopulationDensity());
        } catch (NumberFormatException | NullPointerException e) {
            errorsWithMessages.put(
                    "populationDensity",
                    "Плотность населения города должна быть целым числом");
        }
        if (cityInputRequestDto.getAgglomeration() != null) {
            try {
                Integer.valueOf(cityInputRequestDto.getAgglomeration());
            } catch (NumberFormatException e) {
                errorsWithMessages.put(
                        "agglomeration",
                        "Численность населения агломерации должна быть целым числом");
            }
        }
        if (cityInputRequestDto.getGovernment() == null
                || !Arrays.stream(Government.values())
                .map(Government::getTitle)
                .toList()
                .contains(cityInputRequestDto.getGovernment())) {
            errorsWithMessages.put(
                    "government",
                    "Тип правления города должен быть одним из предложенных");
        }
        try {
            Double.parseDouble(cityInputRequestDto.getGovernorRequestDto()
                                                .getHeight());
        } catch (NumberFormatException | NullPointerException e) {
            errorsWithMessages.put("height",
                    "Рост губернатора должен быть вещественным числом в метрах");
        }
        if (cityInputRequestDto.getGovernorRequestDto().getBirthday() != null) {
            try {
                SimpleDateFormat sdf =
                        new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                sdf.setLenient(false);
                sdf.parse(cityInputRequestDto.getGovernorRequestDto().getBirthday());
            } catch (ParseException e) {
                errorsWithMessages.put("birthday",
                        "Дата и время рождения губернатора "
                        + "должны иметь формат дд-ММ-гггг ЧЧ:мм:сс");
            }
        }
        return errorsWithMessages;
    }

}
