package com.example.controller;

import com.example.entity.Government;
import com.example.input.dto.CityAddRequestDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class CityAddRequestValidator {

    public Map<String, String> validate(CityAddRequestDto cityAddRequestDto) {
        Map<String, String> errorsWithMessages = new LinkedHashMap<>();

        if (cityAddRequestDto.getName() == null
                || cityAddRequestDto.getName().isBlank()) {
            errorsWithMessages.put("name", "Название города не должно быть пустым");
        }
        try {
            Double.parseDouble(cityAddRequestDto.getCoordinatesRequestDto()
                                                .getX());
        } catch (NumberFormatException | NullPointerException e) {
            errorsWithMessages.put("x",
                    "Координата x должна быть вещественным числом");
        }
        try {
            Float.parseFloat(cityAddRequestDto.getCoordinatesRequestDto()
                                              .getY());
        } catch (NumberFormatException | NullPointerException e) {
            errorsWithMessages.put("y",
                    "Координата y должна быть вещественным числом");
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
            Double.parseDouble(cityAddRequestDto.getGovernorRequestDto()
                                                .getHeight());
        } catch (NumberFormatException | NullPointerException e) {
            errorsWithMessages.put("height",
                    "Рост губернатора должен быть вещественным числом в метрах");
        }
        if (cityAddRequestDto.getGovernorRequestDto().getBirthday() != null) {
            try {
                SimpleDateFormat sdf =
                        new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                sdf.setLenient(false);
                sdf.parse(cityAddRequestDto.getGovernorRequestDto().getBirthday());
            } catch (ParseException e) {
                errorsWithMessages.put("birthday",
                        "Дата и время рождения губернатора "
                        + "должны иметь формат дд-ММ-гггг ЧЧ:мм:сс");
            }
        }
        return errorsWithMessages;
    }

}
