package ru.ifmo.se.commands;

import lombok.Getter;
import ru.ifmo.se.entity.City;
import ru.ifmo.se.entity.Government;
import ru.ifmo.se.io.input.dto.CityRawRequestDto;
import ru.ifmo.se.io.input.dto.CoordRawRequestDto;
import ru.ifmo.se.io.input.dto.HumanRawRequestDto;
import ru.ifmo.se.io.input.dto.ParamRawData;
import ru.ifmo.se.io.input.readers.InputTextHandler;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.Printer;
import ru.ifmo.se.service.CollectionService;
import ru.ifmo.se.service.ParamTypedData;
import ru.ifmo.se.typer.DataTyper;
import ru.ifmo.se.validator.CommandValidator;
import ru.ifmo.se.validator.exceptions.ExecuteScriptValidateException;
import ru.ifmo.se.validator.exceptions.InputFieldValidationException;
import ru.ifmo.se.validator.exceptions.ParamRawDataValidationException;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractAddCityCommand implements Command {

    @Getter
    private final String commandSignature;
    @Getter
    private final String commandDescription;

    private Reader reader;

    protected final CollectionService collectionService;
    private final CommandValidator commandValidator;
    private final DataTyper dataTyper;
    protected final Printer printer;
    private final Map<String, Supplier<String>> readActions = buildMapOfReadActions();
    private boolean inputIsRepeated = false;
    private final Map<String, Consumer<String>> inputValidateMethods;
    Map<String, Consumer<String>> dtoFieldSetters;
    protected final CityRawRequestDto cityRawRequestDto;

    public AbstractAddCityCommand(String commandSignature,
                                  String commandDescription,
                                  CollectionService collectionService,
                                  CommandValidator commandValidator,
                                  DataTyper dataTyper,
                                  Printer printer) {
        this.commandSignature = commandSignature;
        this.commandDescription = commandDescription;
        this.collectionService = collectionService;
        this.commandValidator = commandValidator;
        this.dataTyper = dataTyper;
        this.printer = printer;
        inputValidateMethods = buildMapOfInputValidateMethods();

        cityRawRequestDto = new CityRawRequestDto();
        cityRawRequestDto.setCoordinates(
                new CoordRawRequestDto()
        );
        cityRawRequestDto.setGovernor(
                new HumanRawRequestDto()
        );
        dtoFieldSetters = buildMapOfDtoFieldSetters();
    }

    @Override
    public void execute(String[] inputArgs, Reader reader) {
        this.reader = reader;
        ParamRawData paramRawData = makeParamRawData(inputArgs);

        if (!paramRawData.containsEmpty().isEmpty()) {
            printer.forcePrintln("Отсутствуют аргументы команды: ");
            for (String emptyField : paramRawData.containsEmpty()) {
                printer.forcePrintln(emptyField);
            }
            return;
        }
        try {
            commandValidator.validateParamRawData(paramRawData);
        } catch (ParamRawDataValidationException e) {
            printer.forcePrintln(e.getMessage());
            return;
        }
        try {
            readManage();
        } catch (ExecuteScriptValidateException e) {
            printer.forcePrintln(e.getMessage());
            return;
        }

        ParamTypedData paramTypedData =
                dataTyper.typifyParamRawData(paramRawData);
        City city = dataTyper.typifyCityRawRequestDtoToCity(cityRawRequestDto);

        workWithPrintedText(
                useService(
                        city,
                        paramTypedData
                )
        );
    }

    private void readManage() {
        printer.printlnIfOn("Следуя указаниям, введите данные объекта City");
        for (String action : readActions.keySet()) {
                while (true) {
                    String input = readActions.get(action).get();
                    try {
                        inputValidateMethods.get(action).accept(input);
                    } catch (InputFieldValidationException e) {
                        printer.printlnIfOn(e.getMessage() + ", повторите ввод");
                        inputIsRepeated = true;
                        continue;
                    }
                    dtoFieldSetters.get(action).accept(input);
                    inputIsRepeated = false;
                    break;
                }
        }
    }

    protected abstract ParamRawData makeParamRawData(String[] inputArgs);

    protected abstract void workWithPrintedText(boolean result);

    protected abstract boolean useService(City city,
                                          ParamTypedData paramTypedData);

    private String readInput(String explanation,
                             String message) {
        if (explanation != null && !inputIsRepeated) {
            printer.printlnIfOn(explanation);
        }
        printer.printIfOn(message + " > ");
        try {
            String inputString = reader.readLine();
            return InputTextHandler.stripOrNullField(inputString);
        } catch (IOException e) {
            throw new ExecuteScriptValidateException(
                    "Файл с указанным названием не найден или к нему нет доступа"
            );
        } catch (NullPointerException e) {
            throw new ExecuteScriptValidateException(
                    "Неожиданное количество строк данных в файле"
            );
        }
    }

    private Map<String, Supplier<String>> buildMapOfReadActions() {
        Map<String, Supplier<String>> commands = new LinkedHashMap<>();

        commands.put("name",
                () ->
                    readInput(
                            null,
                            "Введите название города"
                    )
        );
        commands.put("x",
                () ->
                    readInput(
                            "x-координата города - вещественное число, не превышающее 579",
                            "Введите координату x"
                    )
        );
        commands.put("y",
                () ->
                    readInput(
                            "y-координата города - вещественное число",
                            "Введите координату y"
                    )
        );
        commands.put("area",
                () ->
                    readInput(
                            null,
                            "Введите целочисленную площадь города"
                    )
        );
        commands.put("population",
                () ->
                    readInput(
                            null,
                            "Введите численность населения города"
                    )
        );
        commands.put("metersAboveSeaLevel",
                () ->
                    readInput(
                            "Количество метров над уровнем моря - вещественное число",
                            "Введите количество метров над уровнем моря"
                    )
        );
        commands.put("populationDensity",
                () ->
                    readInput(
                            null,
                            "Введите целочисленную плотность населения города"
                        )
        );
        commands.put("agglomeration",
                () ->
                    readInput(
                            null,
                            "Введите численность населения агломерации города"
                    )
        );
        StringBuilder governmentExplanation = new StringBuilder();
        governmentExplanation.append("Возможные типы правления города:\n");
        for (Government government : Government.values()) {
            governmentExplanation.append("- ")
                                 .append(government)
                                 .append("\n");
        }
        commands.put("government",
                () ->
                    readInput(
                            governmentExplanation.toString(),
                            "Введите тип правления города"
                    )
        );
        commands.put("height",
                () ->
                    readInput(
                            "Рост губернатора города - вещественное число в метрах",
                            "Введите рост губернатора"
                    )
        );
        commands.put("birthday",
                () ->
                    readInput(
                            "Дата и время рождения губернатора города имеют формат дд-ММ-гггг ЧЧ:мм:сс",
                            "Введите дату и время рождения губернатора"
                    )
        );
        return commands;
    }

    private Map<String, Consumer<String>> buildMapOfInputValidateMethods() {
        Map<String, Consumer<String>> validateMethods = new HashMap<>();

        validateMethods.put("name", commandValidator::validateNameInput);
        validateMethods.put("x", commandValidator::validateXCoordInput);
        validateMethods.put("y", commandValidator::validateYCoordInput);
        validateMethods.put("area", commandValidator::validateAreaInput);
        validateMethods.put("population", commandValidator::validatePopulationInput);
        validateMethods.put("metersAboveSeaLevel",
                commandValidator::validateMetersAboveSeaLevelInput);
        validateMethods.put("populationDensity",
                commandValidator::validatePopulationDensityInput);
        validateMethods.put("agglomeration", commandValidator::validateAgglomerationInput);
        validateMethods.put("government", commandValidator::validateRusGovernmentInput);
        validateMethods.put("height", commandValidator::validateHeightInput);
        validateMethods.put("birthday", commandValidator::validateBirthdayInput);

        return validateMethods;
    }

    private Map<String, Consumer<String>> buildMapOfDtoFieldSetters() {
        Map<String, Consumer<String>> dtoFieldSetters = new HashMap<>();

        dtoFieldSetters.put("name", cityRawRequestDto::setName);
        dtoFieldSetters.put("x", cityRawRequestDto.getCoordinates()::setX);
        dtoFieldSetters.put("y", cityRawRequestDto.getCoordinates()::setY);
        dtoFieldSetters.put("area", cityRawRequestDto::setArea);
        dtoFieldSetters.put("population", cityRawRequestDto::setPopulation);
        dtoFieldSetters.put("metersAboveSeaLevel", cityRawRequestDto::setMetersAboveSeaLevel);
        dtoFieldSetters.put("populationDensity", cityRawRequestDto::setPopulationDensity);
        dtoFieldSetters.put("agglomeration", cityRawRequestDto::setAgglomeration);
        dtoFieldSetters.put("government", cityRawRequestDto::setGovernment);
        dtoFieldSetters.put("height", cityRawRequestDto.getGovernor()::setHeight);
        dtoFieldSetters.put("birthday", cityRawRequestDto.getGovernor()::setBirthday);

        return dtoFieldSetters;
    }
}
