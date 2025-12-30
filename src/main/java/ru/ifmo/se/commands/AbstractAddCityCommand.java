package ru.ifmo.se.commands;

import ru.ifmo.se.entity.City;
import ru.ifmo.se.entity.Coordinates;
import ru.ifmo.se.entity.Government;
import ru.ifmo.se.entity.Human;
import ru.ifmo.se.io.input.dto.ParamRawData;
import ru.ifmo.se.io.input.readers.InputTextHandler;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.input.readers.file.FileReader;
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
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractAddCityCommand implements Command {

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

    protected AbstractAddCityCommand(CollectionService collectionService,
                                     CommandValidator commandValidator,
                                     DataTyper dataTyper,
                                     Printer printer) {
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
        for (Map.Entry<String, Supplier<String>> actionEntry : readActions.entrySet()) {
            while (true) {
                String input = actionEntry.getValue().get();
                try {
                    inputValidateMethods.get(actionEntry.getKey()).accept(input);
                    dtoFieldSetters.get(actionEntry.getKey()).accept(input);
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
            if (inputString == null) {
                if (reader instanceof FileReader) {
                    throw new ExecuteScriptValidateException(
                            "Неожиданное количество строк данных в файле"
                    );
                }
                inputString = "";
                printer.forcePrintln("");
            }
            return InputTextHandler.stripOrNullField(inputString);
        } catch (IOException e) {
            throw new ExecuteScriptValidateException(
                    "Файл с указанным названием не найден или к нему нет доступа"
            );
        }
    }

    private Map<String, Supplier<String>> buildMapOfReadActions() {
        Map<String, Supplier<String>> commands = new LinkedHashMap<>();

        commands.put(City.FieldNames.ID.getTitle(),
                () ->
                    readInput(
                            null,
                            "Введите название города"
                    )
        );
        commands.put(City.FieldNames.X.getTitle(),
                () ->
                    readInput(
                            "x-координата города - вещественное число, не превышающее 579",
                            "Введите координату x"
                    )
        );
        commands.put(City.FieldNames.Y.getTitle(),
                () ->
                    readInput(
                            "y-координата города - вещественное число",
                            "Введите координату y"
                    )
        );
        commands.put(City.FieldNames.AREA.getTitle(),
                () ->
                    readInput(
                            null,
                            "Введите целочисленную площадь города"
                    )
        );
        commands.put(City.FieldNames.POPULATION.getTitle(),
                () ->
                    readInput(
                            null,
                            "Введите численность населения города"
                    )
        );
        commands.put(City.FieldNames.METERS_ABOVE_SEA_LEVEL.getTitle(),
                () ->
                    readInput(
                            "Количество метров над уровнем моря - вещественное число",
                            "Введите количество метров над уровнем моря"
                    )
        );
        commands.put(City.FieldNames.POPULATION_DENSITY.getTitle(),
                () ->
                    readInput(
                            null,
                            "Введите целочисленную плотность населения города"
                        )
        );
        commands.put(City.FieldNames.AGGLOMERATION.getTitle(),
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
        commands.put(City.FieldNames.GOVERNMENT.getTitle(),
                () ->
                    readInput(
                            governmentExplanation.toString(),
                            "Введите тип правления города"
                    )
        );
        commands.put(City.FieldNames.HEIGHT.getTitle(),
                () ->
                    readInput(
                            "Рост губернатора города - вещественное число в метрах",
                            "Введите рост губернатора"
                    )
        );
        commands.put(City.FieldNames.BIRTHDAY.getTitle(),
                () ->
                    readInput(
                            "Дата и время рождения губернатора "
                                    + "города имеют формат дд-ММ-гггг ЧЧ:мм:сс",
                            "Введите дату и время рождения губернатора"
                    )
        );
        return commands;
    }

    private Map<String, Consumer<String>> buildMapOfInputValidateMethods() {
        Map<String, Consumer<String>> validateMethods = new HashMap<>();

        validateMethods.put(
                City.FieldNames.NAME.getTitle(), commandValidator::validateNameInput);
        validateMethods.put(
                City.FieldNames.X.getTitle(), commandValidator::validateXCoordInput);
        validateMethods.put(
                City.FieldNames.Y.getTitle(), commandValidator::validateYCoordInput);
        validateMethods.put(
                City.FieldNames.AREA.getTitle(), commandValidator::validateAreaInput
        );
        validateMethods.put(
                City.FieldNames.POPULATION.getTitle(), commandValidator::validatePopulationInput);
        validateMethods.put(
                City.FieldNames.METERS_ABOVE_SEA_LEVEL.getTitle(),
                commandValidator::validateMetersAboveSeaLevelInput
        );
        validateMethods.put(
                City.FieldNames.POPULATION_DENSITY.getTitle(),
                commandValidator::validatePopulationDensityInput
        );
        validateMethods.put(
                City.FieldNames.AGGLOMERATION.getTitle(),
                commandValidator::validateAgglomerationInput
        );
        validateMethods.put(
                City.FieldNames.GOVERNMENT.getTitle(),
                commandValidator::validateRusGovernmentInput
        );
        validateMethods.put(
                City.FieldNames.HEIGHT.getTitle(), commandValidator::validateHeightInput);
        validateMethods.put(
                City.FieldNames.BIRTHDAY.getTitle(),
                commandValidator::validateBirthdayInput
        );
        return validateMethods;
    }

    private Map<String, Consumer<String>> buildMapOfDtoFieldSetters() {
        Map<String, Consumer<String>> dtoFieldSetters = new HashMap<>();

        dtoFieldSetters.put(City.FieldNames.NAME.getTitle(), name -> city.setName(name));
        dtoFieldSetters.put(City.FieldNames.X.getTitle(),
                x -> city.getCoordinates().setX(Double.parseDouble(x)));
        dtoFieldSetters.put(City.FieldNames.Y.getTitle(),
                y -> city.getCoordinates().setY(Float.parseFloat(y)));
        dtoFieldSetters.put(City.FieldNames.AREA.getTitle(),
                area -> city.setArea(Long.valueOf(area)));
        dtoFieldSetters.put(City.FieldNames.POPULATION.getTitle(),
                popul -> city.setPopulation(Integer.valueOf(popul)));
        dtoFieldSetters.put(City.FieldNames.METERS_ABOVE_SEA_LEVEL.getTitle(),
                meters -> city.setMetersAboveSeaLevel(
                        (meters != null) ? Float.valueOf(meters) : null
                )
        );
        dtoFieldSetters.put(City.FieldNames.POPULATION_DENSITY.getTitle(),
                popDens -> city.setPopulationDensity(Long.parseLong(popDens)));
        dtoFieldSetters.put(City.FieldNames.AGGLOMERATION.getTitle(),
                aggl -> city.setAgglomeration(
                        (aggl != null) ? Integer.valueOf(aggl) : null
                )
        );
        dtoFieldSetters.put(City.FieldNames.GOVERNMENT.getTitle(),
                gov -> city.setGovernment(Government.fromRussianString(gov)));
        dtoFieldSetters.put(City.FieldNames.HEIGHT.getTitle(),
                height -> city.getGovernor().setHeight(Double.parseDouble(height)));
        dtoFieldSetters.put(City.FieldNames.BIRTHDAY.getTitle(),
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
