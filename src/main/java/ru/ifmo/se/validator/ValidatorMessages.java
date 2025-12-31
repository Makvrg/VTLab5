package ru.ifmo.se.validator;

public class ValidatorMessages {

    private ValidatorMessages() {
    }

    public static final String ID_MUST_BE_NON_NEGATIVE =
            "Id города должен быть неотрицательным";
    public static final String ARGUMENT_ID_MUST_BE_NON_NEGATIVE =
            "Переданный аргумент id должен быть неотрицательным";
    public static final String ARGUMENT_ID_MUST_BE_INTEGER =
            "Переданный аргумент id должен быть целым числом";
    public static final String PARAMETER_ID_NOT_PASSED =
            "Не передан параметр id";

    public static final String NAME_MUST_BE_NON_BLANK =
            "Название города не должно быть пустым";

    public static final String CREATE_DATE_FORMAT_EXC =
            "Дата и время основания города "
                    + "должны иметь формат дд-ММ-гггг ЧЧ:мм:сс";

    public static final String X_COORD_MUST_BE_REAL_NUM =
            "Координата x должна быть вещественным числом";
    public static final String X_COORD_MUST_BE_LESS_MAX =
            "Координата x не должна быть больше %f";
    public static final String Y_COORD_MUST_BE_REAL_NUM =
            "Координата y должна быть вещественным числом";
    public static final String ABS_X_COORD_MUST_BE_LESS_MAX =
            "Координата x по модулю не должна превышать максимум числа в памяти";
    public static final String ABS_Y_COORD_MUST_BE_LESS_MAX =
            "Координата y по модулю не должна превышать максимум числа в памяти";

    public static final String AREA_MUST_BE_MORE_ZERO =
            "Площадь города должна быть больше 0";
    public static final String AREA_MUST_BE_INTEGER =
            "Площадь города должна быть целым числом";

    public static final String POPULATION_MUST_BE_MORE_ZERO =
            "Численность населения города должна быть больше 0";
    public static final String POPULATION_MUST_BE_INTEGER =
            "Численность населения города должна быть целым числом";

    public static final String METERS_ABOVE_SEA_LEVEL_MUST_BE_REAL_NUM =
            "Число метров над уровнем моря должно быть вещественным числом";
    public static final String ABS_METERS_ABOVE_SEA_LEVEL_MUST_BE_LESS_MAX =
            "Число метров над уровнем моря не должно превышать максимум числа в памяти";

    public static final String POPULATION_DENSITY_MUST_BE_MORE_ZERO =
            "Плотность населения города должна быть больше 0";
    public static final String POPULATION_DENSITY_MUST_BE_INTEGER =
            "Плотность населения города должна быть целым числом";
    public static final String PARAMETER_POPULATION_DENSITY_NOT_PASSED =
            "Не передан параметр populationDensity";

    public static final String AGGLOMERATION_MUST_BE_INTEGER =
            "Численность населения агломерации города должна быть целым числом";

    public static final String GOVERNMENT_MUST_BE_IN_ENUM =
            "Тип правления города должен быть одним из предложенных";

    public static final String HEIGHT_MUST_BE_REAL_NUM =
            "Рост губернатора города должен быть вещественным числом в метрах";
    public static final String ABS_HEIGHT_MUST_BE_LESS_MAX =
            "Рост губернатора города не должен превышать максимум числа в памяти";
    public static final String HEIGHT_MUST_BE_MORE_ZERO =
            "Рост губернатора города должен быть больше 0";

    public static final String BIRTHDAY_FORMAT_EXC =
            "Дата и время рождения губернатора города "
                    + "должны иметь формат дд-ММ-гггг ЧЧ:мм:сс";

    public static final String PARAMETER_FILE_NAME_NOT_PASSED =
            "Не введено название файла";
}
