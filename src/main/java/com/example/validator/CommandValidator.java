package com.example.validator;

import com.example.entity.Government;
import com.example.input.dto.ParamRawData;
import com.example.validator.exceptions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class CommandValidator {

    public void validateIdInput(String id) {
        try {
            Long.parseLong(id);
        } catch (NumberFormatException | NullPointerException e) {
            throw new InputFieldValidationException(
                    "Id города должен быть целым числом");
        }
        if (Long.parseLong(id) < 0) {
            throw new InputFieldValidationException(
                    "Id города должен быть неотрицательным");
        }
    }

    public void validateNameInput(String name) {
        if (name == null || name.isBlank()) {
            throw new InputFieldValidationException(
                    "Название города не должно быть пустым");
        }
    }

    public void validateXCoordInput(String xCoord) {
        double MAX_COORD_X = 579;
        try {
            Double.parseDouble(xCoord);
        } catch (NumberFormatException | NullPointerException e) {
            throw new InputFieldValidationException(
                    "Координата x должна быть вещественным числом");
        }
        if (Double.parseDouble(xCoord) > MAX_COORD_X) {
            throw new InputFieldValidationException(
                    "Координата x не должна быть больше "
                            + MAX_COORD_X
            );
        }
    }

    public void validateYCoordInput(String yCoord) {
        try {
            Float.parseFloat(yCoord);
        } catch (NumberFormatException | NullPointerException e) {
            throw new InputFieldValidationException(
                    "Координата y должна быть вещественным числом");
        }
    }

    public void validateCreationDateInput(String creationDate) {
        if (creationDate == null) {
            throw new InputFieldValidationException("Дата основания города "
                    + "должна иметь формат дд-ММ-гггг ЧЧ:мм:сс"
            );
        }
        try {
            SimpleDateFormat sdf =
                    new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            sdf.setLenient(false);
            sdf.parse(creationDate);
        } catch (ParseException e) {
            throw new InputFieldValidationException("Дата основания города "
                    + "должна иметь формат дд-ММ-гггг ЧЧ:мм:сс"
            );
        }
    }

    public void validateAreaInput(String area) {
        try {
            Long.parseLong(area);
        } catch (NumberFormatException | NullPointerException e) {
            throw new InputFieldValidationException(
                    "Площадь города должна быть целым числом");
        }
        if (Long.parseLong(area) <= 0) {
            throw new InputFieldValidationException(
                    "Площадь города должна быть больше 0");
        }
    }

    public void validatePopulationInput(String population) {
        try {
            Integer.parseInt(population);
        } catch (NumberFormatException | NullPointerException e) {
            throw new InputFieldValidationException(
                    "Численность населения города должна быть целым числом");
        }
        if (Integer.parseInt(population) <= 0) {
            throw new InputFieldValidationException(
                    "Численность населения города должна быть больше 0");
        }
    }

    public void validateMetersAboveSeaLevelInput(String metersAboveSeaLevel) {
        if (metersAboveSeaLevel != null) {
            try {
                Float.parseFloat(metersAboveSeaLevel);
            } catch (NumberFormatException e) {
                throw new InputFieldValidationException(
                        "Число метров над уровнем моря должно быть вещественным числом");
            }
        }
    }

    public void validatePopulationDensityInput(String populationDensity) {
        try {
            Long.parseLong(populationDensity);
        } catch (NumberFormatException | NullPointerException e) {
            throw new InputFieldValidationException(
                    "Плотность населения города должна быть целым числом");
        }
        if (Long.parseLong(populationDensity) <= 0) {
            throw new InputFieldValidationException(
                    "Плотность населения города должна быть больше 0");
        }
    }

    public void validateAgglomerationInput(String agglomeration) {
        if (agglomeration != null) {
            try {
                Integer.parseInt(agglomeration);
            } catch (NumberFormatException e) {
                throw new InputFieldValidationException(
                        "Численность населения агломерации города должна быть целым числом");
            }
        }
    }

    public void validateGovernmentInput(String government) {
        if (government == null
                || !Arrays.stream(Government.values())
                          .map(Government::getTitle)
                          .toList()
                          .contains(government)) {
            throw new InputFieldValidationException(
                    "Тип правления города должен быть одним из предложенных");
        }
    }

    public void validateHeightInput(String height) {
        try {
            Double.parseDouble(height);
        } catch (NumberFormatException | NullPointerException e) {
            throw new InputFieldValidationException(
                    "Рост губернатора города должен быть вещественным числом в метрах");
        }
        if (Double.parseDouble(height) <= 0) {
            throw new InputFieldValidationException(
                    "Рост губернатора города должен быть больше 0");
        }
    }

    public void validateBirthdayInput(String birthday) {
        if (birthday != null) {
            try {
                SimpleDateFormat sdf =
                        new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                sdf.setLenient(false);
                sdf.parse(birthday);
            } catch (ParseException e) {
                throw new InputFieldValidationException(
                        "Дата и время рождения губернатора города "
                                + "должны иметь формат дд-ММ-гггг ЧЧ:мм:сс"
                );
            }
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
