package com.example.service;

import com.example.input.dto.CityInputDto;

import java.util.LinkedHashMap;
import java.util.Map;

public class CityInputDtoValidator {

    private static final double MAX_COORD_X = 579;

    public Map<String, String> validate(CityInputDto cityInputDto) {
        Map<String, String> errorsWithMessages = new LinkedHashMap<>();

        if (cityInputDto.getCoordinatesDto().getX() > MAX_COORD_X) {
            errorsWithMessages.put("x",
                    "Координата x не должна быть больше " + MAX_COORD_X);
        }
        if (cityInputDto.getArea() <= 0) {
            errorsWithMessages.put("area", "Площадь города должна быть больше 0");
        }
        if (cityInputDto.getPopulation() <= 0) {
            errorsWithMessages.put("population",
                    "Численность населения должна быть больше 0");
        }
        if (cityInputDto.getPopulationDensity() <= 0) {
            errorsWithMessages.put("populationDensity",
                    "Плотность населения должна быть больше 0");
        }
        if (cityInputDto.getGovernorDto().getHeight() <= 0) {
            errorsWithMessages.put("height",
                    "Рост губернатора города должен быть больше 0");
        }
        return errorsWithMessages;
    }

}
