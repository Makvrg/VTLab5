package ru.ifmo.se.commands;

import lombok.Getter;
import ru.ifmo.se.entity.City;
import ru.ifmo.se.entity.Coordinates;
import ru.ifmo.se.entity.Government;
import ru.ifmo.se.entity.Human;
import ru.ifmo.se.io.input.dto.ParamRawData;
import ru.ifmo.se.io.input.readers.InputTextHandler;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.service.CollectionService;
import ru.ifmo.se.service.ParamTypedData;
import ru.ifmo.se.typer.DataTyper;
import ru.ifmo.se.validator.CommandValidator;
import ru.ifmo.se.validator.exceptions.ExecuteScriptValidateException;
import ru.ifmo.se.validator.exceptions.InputFieldValidationException;
import ru.ifmo.se.validator.exceptions.ParamRawDataValidationException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private City city;
    private final Map<String, Consumer<String>> inputValidateMethods;
    private final Map<String, Consumer<String>> dtoFieldSetters;


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
        city = new City();
        city.setCoordinates(new Coordinates());
        city.setGovernor(new Human());
        inputValidateMethods = buildMapOfInputValidateMethods();
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

        city = new City();
        city.setCoordinates(new Coordinates());
        city.setGovernor(new Human());
        try {
            readManage();
        } catch (ExecuteScriptValidateException e) {
            printer.forcePrintln(e.getMessage());
            return;
        }

        ParamTypedData paramTypedData =
                dataTyper.typifyParamRawData(paramRawData);

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
                        dtoFieldSetters.get(action).accept(input);
                    } catch (InputFieldValidationException e) {
                        printer.printlnIfOn(e.getMessage() + ", повторите ввод");
                        inputIsRepeated = true;
                        continue;
                    }
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

        dtoFieldSetters.put("name", name -> city.setName(name));
        dtoFieldSetters.put("x",
                x -> city.getCoordinates().setX(Double.parseDouble(x)));
        dtoFieldSetters.put("y",
                y -> city.getCoordinates().setY(Float.parseFloat(y)));
        dtoFieldSetters.put("area", area -> city.setArea(Long.valueOf(area)));
        dtoFieldSetters.put("population",
                popul -> city.setPopulation(Integer.valueOf(popul)));
        dtoFieldSetters.put("metersAboveSeaLevel",
                meters -> city.setMetersAboveSeaLevel(
                        (meters != null) ? Float.valueOf(meters) : null
                )
        );
        dtoFieldSetters.put("populationDensity",
                popDens -> city.setPopulationDensity(Long.parseLong(popDens)));
        dtoFieldSetters.put("agglomeration",
                aggl -> city.setAgglomeration(
                        (aggl != null) ? Integer.valueOf(aggl) : null
                )
        );
        dtoFieldSetters.put("government",
                gov -> city.setGovernment(Government.fromRussianString(gov)));
        dtoFieldSetters.put("height",
                height -> city.getGovernor().setHeight(Double.parseDouble(height)));
        dtoFieldSetters.put("birthday",
                birthday ->
                {
                    if (birthday != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        sdf.setLenient(false);
                        try {
                            Date typedBirth = sdf.parse(birthday);
                            city.getGovernor().setBirthday(typedBirth);
                        } catch (ParseException e) {
                            throw new InputFieldValidationException(
                                    "Дата и время рождения губернатора города "
                                            + "должны иметь формат дд-ММ-гггг ЧЧ:мм:сс"
                            );
                        }
                    } else {
                        city.getGovernor().setBirthday(null);
                    }
                }
                );

        return dtoFieldSetters;
    }
}
