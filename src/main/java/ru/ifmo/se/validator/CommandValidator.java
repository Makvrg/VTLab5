package ru.ifmo.se.validator;

import ru.ifmo.se.entity.Government;
import ru.ifmo.se.io.input.dto.ParamRawData;
import ru.ifmo.se.validator.exceptions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class CommandValidator {

    private static final double MAX_X_COORD = 579;
    private static final String DATE_PATTERN = "dd-MM-yyyy HH:mm:ss";

    public void validateTypedIdInput(Long id) {
        if (id < 0) {
            throw new InputFieldValidationException(
                    ValidatorMessages.ID_MUST_BE_NON_NEGATIVE);
        }
    }

    public void validateTypedCreationDateInput(Date creationDate) {
        if (creationDate == null) {
            throw new InputFieldValidationException(
                    ValidatorMessages.CREATE_DATE_FORMAT_EXC);
        }
    }

    public void validateTypedXCoordInput(double xCoord) {
        if (Double.isNaN(xCoord)) {
            throw new InputFieldValidationException(
                    ValidatorMessages.X_COORD_MUST_BE_REAL_NUM);
        }
        if (Double.isInfinite(xCoord)) {
            throw new InputFieldValidationException(
                    ValidatorMessages.ABS_X_COORD_MUST_BE_LESS_MAX);
        }
        if (xCoord > MAX_X_COORD) {
            throw new InputFieldValidationException(
                    String.format(
                            ValidatorMessages.X_COORD_MUST_BE_LESS_MAX,
                            MAX_X_COORD
                    )
            );
        }
    }

    public void validateTypedYCoordInput(float yCoord) {
        if (Float.isNaN(yCoord)) {
            throw new InputFieldValidationException(
                    ValidatorMessages.Y_COORD_MUST_BE_REAL_NUM);
        }
        if (Float.isInfinite(yCoord)) {
            throw new InputFieldValidationException(
                    ValidatorMessages.ABS_Y_COORD_MUST_BE_LESS_MAX);
        }
    }

    public void validateTypedAreaInput(Long area) {
        if (area <= 0) {
            throw new InputFieldValidationException(
                    ValidatorMessages.AREA_MUST_BE_MORE_ZERO);
        }
    }

    public void validateTypedPopulationInput(Integer population) {
        if (population <= 0) {
            throw new InputFieldValidationException(
                    ValidatorMessages.POPULATION_MUST_BE_MORE_ZERO);
        }
    }

    public void validateTypedMetersAboveSeaLevelInput(Float metersAboveSeaLevel) {
        if (metersAboveSeaLevel != null) {
            if (metersAboveSeaLevel.isNaN()) {
                throw new InputFieldValidationException(
                        ValidatorMessages.METERS_ABOVE_SEA_LEVEL_MUST_BE_REAL_NUM);
            }
            if (metersAboveSeaLevel.isInfinite()) {
                throw new InputFieldValidationException(
                        ValidatorMessages.ABS_METERS_ABOVE_SEA_LEVEL_MUST_BE_LESS_MAX);
            }
        }
    }

    public void validateTypedPopulationDensityInput(long populationDensity) {
        if (populationDensity <= 0) {
            throw new InputFieldValidationException(
                    ValidatorMessages.POPULATION_DENSITY_MUST_BE_MORE_ZERO);
        }
    }

    public void validateTypedGovernmentInput(Government government) {
        if (government == null) {
            throw new InputFieldValidationException(
                    ValidatorMessages.GOVERNMENT_MUST_BE_IN_ENUM);
        }
    }

    public void validateTypedHeightInput(double height) {
        if (Double.isNaN(height)) {
            throw new InputFieldValidationException(
                    ValidatorMessages.HEIGHT_MUST_BE_REAL_NUM);
        }
        if (Double.isInfinite(height)) {
            throw new InputFieldValidationException(
                    ValidatorMessages.ABS_HEIGHT_MUST_BE_LESS_MAX);
        }
        if (height <= 0) {
            throw new InputFieldValidationException(
                    ValidatorMessages.HEIGHT_MUST_BE_MORE_ZERO);
        }
    }

    public void validateNameInput(String name) {
        if (name == null || name.isBlank()) {
            throw new InputFieldValidationException(
                    ValidatorMessages.NAME_MUST_BE_NON_BLANK);
        }
    }

    public void validateXCoordInput(String xCoord) {
        if (xCoord == null || xCoord.isBlank()) {
            throw new InputFieldValidationException(
                    ValidatorMessages.X_COORD_MUST_BE_REAL_NUM);
        }
        double typedX;
        try {
            typedX = Double.parseDouble(xCoord);
        } catch (NumberFormatException e) {
            throw new InputFieldValidationException(
                    ValidatorMessages.X_COORD_MUST_BE_REAL_NUM);
        }
        if (Double.isNaN(typedX)) {
            throw new InputFieldValidationException(
                    ValidatorMessages.X_COORD_MUST_BE_REAL_NUM);
        }
        if (Double.isInfinite(typedX)) {
            throw new InputFieldValidationException(
                    ValidatorMessages.ABS_X_COORD_MUST_BE_LESS_MAX);
        }
        if (typedX > MAX_X_COORD) {
            throw new InputFieldValidationException(
                    String.format(
                            ValidatorMessages.X_COORD_MUST_BE_LESS_MAX,
                            MAX_X_COORD
                    )
            );
        }
    }

    public void validateYCoordInput(String yCoord) {
        if (yCoord == null || yCoord.isBlank()) {
            throw new InputFieldValidationException(
                    ValidatorMessages.Y_COORD_MUST_BE_REAL_NUM);
        }
        float typedY;
        try {
            typedY = Float.parseFloat(yCoord);
        } catch (NumberFormatException e) {
            throw new InputFieldValidationException(
                    ValidatorMessages.Y_COORD_MUST_BE_REAL_NUM);
        }
        if (Float.isNaN(typedY)) {
            throw new InputFieldValidationException(
                    ValidatorMessages.Y_COORD_MUST_BE_REAL_NUM);
        }
        if (Float.isInfinite(typedY)) {
            throw new InputFieldValidationException(
                    ValidatorMessages.ABS_Y_COORD_MUST_BE_LESS_MAX);
        }
    }

    public void validateAreaInput(String area) {
        if (area == null || area.isBlank()) {
            throw new InputFieldValidationException(
                    ValidatorMessages.AREA_MUST_BE_INTEGER);
        }
        try {
            Long.parseLong(area);
        } catch (NumberFormatException e) {
            throw new InputFieldValidationException(
                    ValidatorMessages.AREA_MUST_BE_INTEGER);
        }
        if (Long.parseLong(area) <= 0) {
            throw new InputFieldValidationException(
                    ValidatorMessages.AREA_MUST_BE_MORE_ZERO);
        }
    }

    public void validatePopulationInput(String population) {
        if (population == null || population.isBlank()) {
            throw new InputFieldValidationException(
                    ValidatorMessages.POPULATION_MUST_BE_INTEGER);
        }
        try {
            Integer.parseInt(population);
        } catch (NumberFormatException e) {
            throw new InputFieldValidationException(
                    ValidatorMessages.POPULATION_MUST_BE_INTEGER);
        }
        if (Integer.parseInt(population) <= 0) {
            throw new InputFieldValidationException(
                    ValidatorMessages.POPULATION_MUST_BE_MORE_ZERO);
        }
    }

    public void validateMetersAboveSeaLevelInput(String metersAboveSeaLevel) {
        if (metersAboveSeaLevel != null) {
            float typedMeters;
            try {
                typedMeters = Float.parseFloat(metersAboveSeaLevel);
            } catch (NumberFormatException e) {
                throw new InputFieldValidationException(
                        ValidatorMessages.METERS_ABOVE_SEA_LEVEL_MUST_BE_REAL_NUM);
            }
            if (Float.isNaN(typedMeters)) {
                throw new InputFieldValidationException(
                        ValidatorMessages.METERS_ABOVE_SEA_LEVEL_MUST_BE_REAL_NUM);
            }
            if (Float.isInfinite(typedMeters)) {
                throw new InputFieldValidationException(
                        ValidatorMessages.ABS_METERS_ABOVE_SEA_LEVEL_MUST_BE_LESS_MAX);
            }
        }
    }

    public void validatePopulationDensityInput(String populationDensity) {
        if (populationDensity == null || populationDensity.isBlank()) {
            throw new InputFieldValidationException(
                    ValidatorMessages.POPULATION_DENSITY_MUST_BE_INTEGER);
        }
        try {
            Long.parseLong(populationDensity);
        } catch (NumberFormatException e) {
            throw new InputFieldValidationException(
                    ValidatorMessages.POPULATION_DENSITY_MUST_BE_INTEGER);
        }
        if (Long.parseLong(populationDensity) <= 0) {
            throw new InputFieldValidationException(
                    ValidatorMessages.POPULATION_MUST_BE_MORE_ZERO);
        }
    }

    public void validateAgglomerationInput(String agglomeration) {
        if (agglomeration != null) {
            try {
                Integer.parseInt(agglomeration);
            } catch (NumberFormatException e) {
                throw new InputFieldValidationException(
                        ValidatorMessages.AGGLOMERATION_MUST_BE_INTEGER);
            }
        }
    }

    public void validateRusGovernmentInput(String government) {
        if (government == null
                || !Arrays.stream(Government.values())
                          .map(Government::getTitle)
                          .toList()
                          .contains(government)) {
            throw new InputFieldValidationException(
                    ValidatorMessages.GOVERNMENT_MUST_BE_IN_ENUM);
        }
    }

    public void validateHeightInput(String height) {
        if (height == null || height.isBlank()) {
            throw new InputFieldValidationException(
                    ValidatorMessages.HEIGHT_MUST_BE_REAL_NUM);
        }
        double typedHeight;
        try {
            typedHeight = Double.parseDouble(height);
        } catch (NumberFormatException e) {
            throw new InputFieldValidationException(
                    ValidatorMessages.HEIGHT_MUST_BE_REAL_NUM);
        }
        if (Double.isNaN(typedHeight)) {
            throw new InputFieldValidationException(
                    ValidatorMessages.HEIGHT_MUST_BE_REAL_NUM);
        }
        if (Double.isInfinite(typedHeight)) {
            throw new InputFieldValidationException(
                    ValidatorMessages.ABS_HEIGHT_MUST_BE_LESS_MAX);
        }
        if (Double.parseDouble(height) <= 0) {
            throw new InputFieldValidationException(
                    ValidatorMessages.HEIGHT_MUST_BE_MORE_ZERO);
        }
    }

    public void validateBirthdayInput(String birthday) {
        if (birthday != null) {
            try {
                SimpleDateFormat sdf =
                        new SimpleDateFormat(DATE_PATTERN);
                sdf.setLenient(false);
                sdf.parse(birthday);
            } catch (ParseException e) {
                throw new InputFieldValidationException(
                        ValidatorMessages.BIRTHDAY_FORMAT_EXC);
            }
        }
    }

    public void validateParamRawData(ParamRawData paramRawData) {
        if (paramRawData.getId() != null) {
            try {
                Long.parseLong(paramRawData.getId());
            } catch (NumberFormatException e) {
                throw new ParamRawDataValidationException(
                        ValidatorMessages.ARGUMENT_ID_MUST_BE_INTEGER
                );
            }
            if (Long.parseLong(paramRawData.getId()) < 0) {
                throw new ParamRawDataValidationException(
                        ValidatorMessages.ARGUMENT_ID_MUST_BE_NON_NEGATIVE
                );
            }
        }
    }

    public void validateRemoveById(String id) {
        if (id == null) {
            throw new RemoveByIdValidationException(
                    ValidatorMessages.PARAMETER_ID_NOT_PASSED);
        }
        try {
            Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new RemoveByIdValidationException(
                    ValidatorMessages.ARGUMENT_ID_MUST_BE_INTEGER);
        }
    }

    public void validateRemoveAllByPopulationDensity(String populationDensity) {
        if (populationDensity == null) {
            throw new RemoveAllByPopulationDensityValidationException(
                    ValidatorMessages.PARAMETER_POPULATION_DENSITY_NOT_PASSED);
        }
        try {
            if (Long.parseLong(populationDensity) <= 0) {
                throw new RemoveAllByPopulationDensityValidationException(
                        ValidatorMessages.POPULATION_DENSITY_MUST_BE_MORE_ZERO);
            }
        } catch (NumberFormatException e) {
            throw new RemoveAllByPopulationDensityValidationException(
                    ValidatorMessages.POPULATION_DENSITY_MUST_BE_INTEGER
            );
        }
    }

    public void validateFilterLessThanPopulationDensity(String populationDensity) {
        if (populationDensity == null) {
            throw new FilterLessThanPopulationDensityValidationException(
                    ValidatorMessages.PARAMETER_POPULATION_DENSITY_NOT_PASSED);
        }
        try {
            if (Long.parseLong(populationDensity) <= 0) {
                throw new FilterLessThanPopulationDensityValidationException(
                        ValidatorMessages.POPULATION_DENSITY_MUST_BE_MORE_ZERO);
            }
        } catch (NumberFormatException e) {
            throw new FilterLessThanPopulationDensityValidationException(
                    ValidatorMessages.POPULATION_DENSITY_MUST_BE_INTEGER);
        }
    }

    public void validateExecuteScript(String fileName) {
        if (fileName == null) {
            throw new ExecuteScriptValidateException(
                    ValidatorMessages.PARAMETER_FILE_NAME_NOT_PASSED);
        }
    }
}
