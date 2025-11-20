package com.example.service;

import com.example.input.dto.CityTypedRequestDto;

import java.util.LinkedHashMap;
import java.util.Map;

public class CityTypedRequestDtoValidator {

    private static final double MAX_COORD_X = 579;

    public Map<String, String> validate(CityTypedRequestDto cityTypedRequestDto) {
        Map<String, String> errorsWithMessages = new LinkedHashMap<>();

        if (cityTypedRequestDto.getCoordinates().getX() > MAX_COORD_X) {
            errorsWithMessages.put("x",
                    "Координата x не должна быть больше " + MAX_COORD_X);
        }
        if (cityTypedRequestDto.getArea() <= 0) {
            errorsWithMessages.put("area", "Площадь города должна быть больше 0");
        }
        if (cityTypedRequestDto.getPopulation() <= 0) {
            errorsWithMessages.put("population",
                    "Численность населения должна быть больше 0");
        }
        if (cityTypedRequestDto.getPopulationDensity() <= 0) {
            errorsWithMessages.put("populationDensity",
                    "Плотность населения должна быть больше 0");
        }
        if (cityTypedRequestDto.getGovernor().getHeight() <= 0) {
            errorsWithMessages.put("height",
                    "Рост губернатора города должен быть больше 0");
        }
        return errorsWithMessages;
    }

}
