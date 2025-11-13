package com.example.input.commands;

import com.example.CityValidationException;
import com.example.controller.CollectionController;
import com.example.entity.Government;
import com.example.input.dto.CityAddRequestDto;
import com.example.input.dto.CoordinatesRequestDto;
import com.example.input.dto.HumanRequestDto;
import com.example.input.readers.IReader;
import com.example.input.readers.terminal.Processor;
import com.example.service.AddMode;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class AddCommand implements ICommand {

    private final CollectionController collectionController;
    private final IReader terminalReader;
    private final AddMode addMode;
    private final Map<String, Runnable> readActions;
    private final CityAddRequestDto cityAddRequestDto;
    private Map<String, String> errorsWithMessages = new LinkedHashMap<>();

    public AddCommand(CollectionController collectionController,
                      IReader terminalReader,
                      AddMode addMode) {
        this.collectionController = collectionController;
        this.terminalReader = terminalReader;
        this.addMode = addMode;
        readActions = buildMapOfReadActions();

        cityAddRequestDto = new CityAddRequestDto();
        cityAddRequestDto.setCoordinatesRequestDto(
                new CoordinatesRequestDto()
        );
        cityAddRequestDto.setGovernorRequestDto(
                new HumanRequestDto()
        );
    }

    private void readManage(Map<String, String> errorsWithMessages) {
        if (errorsWithMessages.isEmpty()) {
            System.out.println("Выбрано добавление нового объекта City");
            System.out.println("Следуя указаниям, введите данные");

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
        readManage(errorsWithMessages);
        try {
            collectionController.add(cityAddRequestDto, addMode);
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
                            cityAddRequestDto::setName
                    )
        );
        commands.put("x",
                () ->
                    setDtoField(
                            "x-координата - вещественное число, не превышающее 579",
                            "Введите x-координату города",
                            cityAddRequestDto.getCoordinatesRequestDto()::setX
                    )
        );
        commands.put("y",
                () ->
                    setDtoField(
                            "y-координата - вещественное число",
                            "Введите y-координату города",
                            cityAddRequestDto.getCoordinatesRequestDto()::setY
                    )
        );
        commands.put("area",
                () ->
                    setDtoField(
                            null,
                            "Введите целочисленную площадь города",
                            cityAddRequestDto::setArea
                    )
        );
        commands.put("population",
                () ->
                    setDtoField(
                            null,
                            "Введите численность населения города",
                            cityAddRequestDto::setPopulation
                    )
        );
        commands.put("metersAboveSeaLevel",
                () ->
                    setDtoField(
                            "Количество метров над уровнем моря - вещественное число",
                            "Введите количество метров над уровнем моря",
                            cityAddRequestDto::setMetersAboveSeaLevel
                    )
        );
        commands.put("populationDensity",
                () ->
                    setDtoField(
                            null,
                            "Введите целочисленную плотность населения города",
                            cityAddRequestDto::setPopulationDensity
                        )
        );
        commands.put("agglomeration",
                () ->
                    setDtoField(
                            null,
                            "Введите численность населения агломерации города",
                            cityAddRequestDto::setAgglomeration
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
                            cityAddRequestDto::setGovernment
                    )
        );
        commands.put("height",
                () ->
                    setDtoField(
                            "Рост губернатора - вещественное число в метрах",
                            "Введите рост губернатора города",
                            cityAddRequestDto.getGovernorRequestDto()::setHeight
                    )
        );
        commands.put("birthday",
                () ->
                    setDtoField(
                            "Дата и время рождения губернатора имеют формат дд-ММ-гггг ЧЧ:мм:сс",
                            "Введите дату и время рождения губернатора города",
                            cityAddRequestDto.getGovernorRequestDto()::setBirthday
                    )
        );
        return commands;
    }

}
