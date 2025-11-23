package com.example.validator;

import com.example.CityValidationException;
import com.example.entity.Government;
import com.example.input.dto.CityRawRequestDto;
import com.example.input.dto.CityTypedRequestDto;
import com.example.input.dto.ParamRawData;
import com.example.validator.exceptions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommandValidator {

    public void validateCityRawRequestDto(CityRawRequestDto cityRawRequestDto)
            throws CityValidationException {
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

        if (!errorsWithMessages.isEmpty()) {
            throw new CityValidationException(errorsWithMessages);
        }
    }

    public void validateCityTypedRequestDto(CityTypedRequestDto cityTypedRequestDto)
            throws CityValidationException{
        double MAX_COORD_X = 579;
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

        if (!errorsWithMessages.isEmpty()) {
            throw new CityValidationException(errorsWithMessages);
        }
    }

    public void validateParamRawData(ParamRawData paramRawData) {
        if (paramRawData.getId() != null) {
            try {
                Long.parseLong(paramRawData.getId());
            } catch (NumberFormatException e) {
                throw new RawActionDataValidationException(
                        "Аргумент id должен быть целым числом"
                );
            }
            if (Long.parseLong(paramRawData.getId()) < 0) {
                throw new RawActionDataValidationException(
                        "Аргумент id должен быть положительным числом"
                );
            }
        }
    }

    public void validateRemoveById(String id) {
        if (id == null) {
            throw new RemoveByIdValidationException("Не передан параметр id");
        }
        try {
            Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new RemoveByIdValidationException(
                    "Переданный аргумент id не является целым числом"
            );
        }
    }

    public void validateRemoveAllByPopulationDensity(String populationDensity) {
        if (populationDensity == null) {
            throw new RemoveAllByPopulationDensityValidationException(
                    "Не передан параметр populationDensity"
            );
        }
        try {
            if (Long.parseLong(populationDensity) <= 0) {
                throw new RemoveAllByPopulationDensityValidationException(
                        "Плотность населения города должна быть больше 0"
                );
            }
        } catch (NumberFormatException e) {
            throw new RemoveAllByPopulationDensityValidationException(
                    "Плотность населения города должна быть целым числом"
            );
        }
    }

    public void validateFilterLessThanPopulationDensity(String populationDensity) {
        if (populationDensity == null) {
            throw new FilterLessThanPopulationDensityValidationException(
                    "Не передан параметр populationDensity"
            );
        }
        try {
            if (Long.parseLong(populationDensity) <= 0) {
                throw new FilterLessThanPopulationDensityValidationException(
                        "Плотность населения города должна быть больше 0"
                );
            }
        } catch (NumberFormatException e) {
            throw new FilterLessThanPopulationDensityValidationException(
                    "Плотность населения города должна быть целым числом"
            );
        }
    }

    public void validateExecuteScript(String fileName) {
        if (fileName == null) {
            throw new ExecuteScriptValidateException("Не введено название файла");
        }
    }

}
