package com.example.input.commands;

import com.example.CityValidationException;
import com.example.controller.CollectionController;
import com.example.controller.InputActionDataValidationException;
import com.example.entity.Government;
import com.example.input.dto.CityInputRequestDto;
import com.example.input.dto.CoordinatesRequestDto;
import com.example.input.dto.HumanRequestDto;
import com.example.input.dto.InputActionData;
import com.example.input.readers.IReader;
import com.example.input.readers.terminal.Processor;
import com.example.service.InputMode;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class InputCityCommand implements ICommand {

    private final CollectionController collectionController;
    private final IReader terminalReader;
    private final InputMode inputMode;
    private final Map<String, Runnable> readActions;
    private final InputActionData inputActionData;
    private final CityInputRequestDto cityInputRequestDto;
    private Map<String, String> errorsWithMessages = new LinkedHashMap<>();

    public InputCityCommand(CollectionController collectionController,
                            IReader terminalReader,
                            InputMode inputMode,
                            InputActionData inputActionData) {
        this.collectionController = collectionController;
        this.terminalReader = terminalReader;
        this.inputMode = inputMode;
        this.inputActionData = inputActionData;
        readActions = buildMapOfReadActions();

        cityInputRequestDto = new CityInputRequestDto();
        cityInputRequestDto.setCoordinatesRequestDto(
                new CoordinatesRequestDto()
        );
        cityInputRequestDto.setGovernorRequestDto(
                new HumanRequestDto()
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
        if (!inputActionData.containsEmpty().isEmpty()) {
            System.out.println("Отсутствуют аргументы: ");
            for (String emptyField : inputActionData.containsEmpty()) {
                System.out.print(emptyField);
            }
            System.out.println();
            return;
        }
        try {
            collectionController.controlInputActionData(inputActionData);
        } catch (InputActionDataValidationException e) {
            System.out.println(e.getMessage());
            return;
        }
        readManage(errorsWithMessages);
        try {
            collectionController.controlInputCity(cityInputRequestDto,
                                                  inputMode,
                                                  inputActionData);
        } catch (CityValidationException e) {
            errorsWithMessages = e.getErrorsWithMessages();
            execute();
        }
    }

    private void setDtoField(String explanation,
                             String message,
                             Consumer<String> setter) {
        if (explanation != null) {
            System.out.println(explanation);
        }
        System.out.print(message + " > ");
        setter.accept(Processor.processTerminalData(terminalReader.read()));
    }

    private Map<String, Runnable> buildMapOfReadActions() {
        Map<String, Runnable> commands = new LinkedHashMap<>();

        commands.put("name",
                () ->
                    setDtoField(
                            null,
                            "Введите название города",
                            cityInputRequestDto::setName
                    )
        );
        commands.put("x",
                () ->
                    setDtoField(
                            "x-координата - вещественное число, не превышающее 579",
                            "Введите x-координату города",
                            cityInputRequestDto.getCoordinatesRequestDto()::setX
                    )
        );
        commands.put("y",
                () ->
                    setDtoField(
                            "y-координата - вещественное число",
                            "Введите y-координату города",
                            cityInputRequestDto.getCoordinatesRequestDto()::setY
                    )
        );
        commands.put("area",
                () ->
                    setDtoField(
                            null,
                            "Введите целочисленную площадь города",
                            cityInputRequestDto::setArea
                    )
        );
        commands.put("population",
                () ->
                    setDtoField(
                            null,
                            "Введите численность населения города",
                            cityInputRequestDto::setPopulation
                    )
        );
        commands.put("metersAboveSeaLevel",
                () ->
                    setDtoField(
                            "Количество метров над уровнем моря - вещественное число",
                            "Введите количество метров над уровнем моря",
                            cityInputRequestDto::setMetersAboveSeaLevel
                    )
        );
        commands.put("populationDensity",
                () ->
                    setDtoField(
                            null,
                            "Введите целочисленную плотность населения города",
                            cityInputRequestDto::setPopulationDensity
                        )
        );
        commands.put("agglomeration",
                () ->
                    setDtoField(
                            null,
                            "Введите численность населения агломерации города",
                            cityInputRequestDto::setAgglomeration
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
                            cityInputRequestDto::setGovernment
                    )
        );
        commands.put("height",
                () ->
                    setDtoField(
                            "Рост губернатора - вещественное число в метрах",
                            "Введите рост губернатора города",
                            cityInputRequestDto.getGovernorRequestDto()::setHeight
                    )
        );
        commands.put("birthday",
                () ->
                    setDtoField(
                            "Дата и время рождения губернатора имеют формат дд-ММ-гггг ЧЧ:мм:сс",
                            "Введите дату и время рождения губернатора города",
                            cityInputRequestDto.getGovernorRequestDto()::setBirthday
                    )
        );
        return commands;
    }

}
