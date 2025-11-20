package com.example.input.commands;

import com.example.CityValidationException;
import com.example.controller.CollectionController;
import com.example.controller.RawActionDataValidationException;
import com.example.entity.Government;
import com.example.input.dto.CityRawRequestDto;
import com.example.input.dto.CoordRawRequestDto;
import com.example.input.dto.HumanRawRequestDto;
import com.example.input.dto.ParamRawData;
import com.example.input.readers.IReader;
import com.example.input.readers.file.IORuntimeException;
import com.example.input.readers.terminal.Processor;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class AddCityCommand implements ICommand {

    protected final CollectionController collectionController;
    private final IReader reader;
    private final Map<String, Runnable> readActions;
    protected final ParamRawData paramRawData;
    protected final CityRawRequestDto cityRawRequestDto;
    private Map<String, String> errorsWithMessages = new LinkedHashMap<>();

    public AddCityCommand(CollectionController collectionController,
                          IReader reader,
                          ParamRawData paramRawData) {
        this.collectionController = collectionController;
        this.reader = reader;
        this.paramRawData = paramRawData;
        readActions = buildMapOfReadActions();

        cityRawRequestDto = new CityRawRequestDto();
        cityRawRequestDto.setCoordinates(
                new CoordRawRequestDto()
        );
        cityRawRequestDto.setGovernor(
                new HumanRawRequestDto()
        );
    }

    private void readManage(Map<String, String> errorsWithMessages) {
        if (errorsWithMessages.isEmpty()) {
            System.out.println("Следуя указаниям, введите данные объекта City");

            readActions.keySet().forEach(
                    action -> readActions.get(action)
                                               .run()
            );
        } else {
            System.out.println();
            System.out.println("Некоторые данные были некорректными");
            System.out.println("Пожалуйста, исправьте их:");
            System.out.println();
            errorsWithMessages.values().forEach(System.out::println);
            errorsWithMessages.keySet().forEach(
                    action -> readActions.get(action)
                                               .run()
            );
            System.out.println();
        }
    }

    @Override
    public void execute() {
        if (!paramRawData.containsEmpty().isEmpty()) {
            System.out.println("Отсутствуют аргументы: ");
            for (String emptyField : paramRawData.containsEmpty()) {
                System.out.print(emptyField);
            }
            System.out.println();
            return;
        }
        try {
            collectionController.controlRawActionData(paramRawData);
        } catch (RawActionDataValidationException e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            readManage(errorsWithMessages);
        } catch (IORuntimeException e) {
            System.out.println(e.getMessage());
            return;
        }

        try {
            useController();
        } catch (CityValidationException e) {
            errorsWithMessages = e.getErrorsWithMessages();
            execute();
        }
    }

    protected void useController() throws CityValidationException {
        collectionController.add(cityRawRequestDto, paramRawData);
    }

    private void setDtoField(String explanation,
                             String message,
                             Consumer<String> setter) {
        if (explanation != null) {
            System.out.println(explanation);
        }
        System.out.print(message + " > ");
        try {
            String inputString = reader.read();
            setter.accept(Processor.processTerminalData(inputString));
        } catch (IOException e) {
            throw new IORuntimeException(
                    "Файл с указанным названием не найден или к нему нет доступа"
            );
        } catch (NullPointerException e) {
            throw new IORuntimeException(
                    "\nНеожиданное количество строк данных в файле"
            );
        }
    }

    private Map<String, Runnable> buildMapOfReadActions() {
        Map<String, Runnable> commands = new LinkedHashMap<>();

        commands.put("name",
                () ->
                    setDtoField(
                            null,
                            "Введите название города",
                            cityRawRequestDto::setName
                    )
        );
        commands.put("x",
                () ->
                    setDtoField(
                            "x-координата - вещественное число, не превышающее 579",
                            "Введите x-координату города",
                            cityRawRequestDto.getCoordinates()::setX
                    )
        );
        commands.put("y",
                () ->
                    setDtoField(
                            "y-координата - вещественное число",
                            "Введите y-координату города",
                            cityRawRequestDto.getCoordinates()::setY
                    )
        );
        commands.put("area",
                () ->
                    setDtoField(
                            null,
                            "Введите целочисленную площадь города",
                            cityRawRequestDto::setArea
                    )
        );
        commands.put("population",
                () ->
                    setDtoField(
                            null,
                            "Введите численность населения города",
                            cityRawRequestDto::setPopulation
                    )
        );
        commands.put("metersAboveSeaLevel",
                () ->
                    setDtoField(
                            "Количество метров над уровнем моря - вещественное число",
                            "Введите количество метров над уровнем моря",
                            cityRawRequestDto::setMetersAboveSeaLevel
                    )
        );
        commands.put("populationDensity",
                () ->
                    setDtoField(
                            null,
                            "Введите целочисленную плотность населения города",
                            cityRawRequestDto::setPopulationDensity
                        )
        );
        commands.put("agglomeration",
                () ->
                    setDtoField(
                            null,
                            "Введите численность населения агломерации города",
                            cityRawRequestDto::setAgglomeration
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
                    setDtoField(
                            governmentExplanation.toString(),
                            "Введите тип правления города",
                            cityRawRequestDto::setGovernment
                    )
        );
        commands.put("height",
                () ->
                    setDtoField(
                            "Рост губернатора - вещественное число в метрах",
                            "Введите рост губернатора города",
                            cityRawRequestDto.getGovernor()::setHeight
                    )
        );
        commands.put("birthday",
                () ->
                    setDtoField(
                            "Дата и время рождения губернатора имеют формат дд-ММ-гггг ЧЧ:мм:сс",
                            "Введите дату и время рождения губернатора города",
                            cityRawRequestDto.getGovernor()::setBirthday
                    )
        );
        return commands;
    }

}
