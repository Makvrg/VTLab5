package com.example.input.commands;

import com.example.CityValidationException;
import com.example.controller.CollectionController;
import com.example.input.commands.lineinput.*;
import com.example.input.dto.CityAddRequestDto;
import com.example.input.dto.CoordinatesRequestDto;
import com.example.input.dto.HumanRequestDto;
import com.example.input.readers.IReader;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class AddCommand implements ICommand {

    private final CollectionController collectionController;
    private final IReader terminalReader;
    private final Map<String, Function<CityAddRequestDto, ICommand>> commands;
    private final CityAddRequestDto cityAddRequestDto;
    private Map<String, String> errorsWithMessages = new LinkedHashMap<>();

    public AddCommand(CollectionController collectionController,
                      IReader terminalReader) {
        this.collectionController = collectionController;
        this.terminalReader = terminalReader;
        // TODO Можно сделать через enum
        this.commands = new LinkedHashMap<>();

        this.commands.put("name",
                cityAddRequestDto -> new NameInputCommand(cityAddRequestDto,
                                                          terminalReader));
        this.commands.put("x",
                cityAddRequestDto -> new CoordXInputCommand(cityAddRequestDto,
                                                            terminalReader));
        this.commands.put("y",
                cityAddRequestDto -> new CoordYInputCommand(cityAddRequestDto,
                                                            terminalReader));
        this.commands.put("area",
                cityAddRequestDto -> new AreaInputCommand(cityAddRequestDto,
                                                          terminalReader));
        this.commands.put("population",
                cityAddRequestDto -> new PopulationInputCommand(cityAddRequestDto,
                                                                terminalReader));
        this.commands.put("metersAboveSeaLevel",
                cityAddRequestDto -> new MetersAboveSeaInputCommand(cityAddRequestDto,
                                                                    terminalReader));
        this.commands.put("populationDensity",
                cityAddRequestDto -> new PopulationDensityInputCommand(cityAddRequestDto,
                                                                       terminalReader));
        this.commands.put("agglomeration",
                cityAddRequestDto -> new AgglomerationInputCommand(cityAddRequestDto,
                                                                   terminalReader));
        this.commands.put("government",
                cityAddRequestDto -> new GovernmentInputCommand(cityAddRequestDto,
                                                                terminalReader));
        this.commands.put("height",
                cityAddRequestDto -> new HeightInputCommand(cityAddRequestDto,
                                                            terminalReader));
        this.commands.put("birthday",
                cityAddRequestDto -> new BirthdayInputCommand(cityAddRequestDto,
                                                              terminalReader));

        this.cityAddRequestDto = new CityAddRequestDto();

        cityAddRequestDto.setCoordinatesRequestDto(new CoordinatesRequestDto());
        cityAddRequestDto.setGovernorRequestDto(new HumanRequestDto());
    }

    private void readElement(Map<String, String> errorsWithMessages) {
        if (errorsWithMessages.isEmpty()) {
            System.out.println("Выбрано добавление нового объекта City");
            System.out.println("Следуя указаниям, введите данные");

            commands.keySet().forEach(
                    command -> commands.get(command)
                                             .apply(cityAddRequestDto)
                                             .execute()
            );
        } else {
            System.out.println();
            System.out.println("Некоторые данные были некорректными");
            System.out.println("Пожалуйста, исправьте их:");
            System.out.println();
            errorsWithMessages.values().forEach(System.out::println);
            errorsWithMessages.keySet().forEach(
                    command -> commands.get(command)
                                             .apply(cityAddRequestDto)
                                             .execute()
            );
            System.out.println();
        }
    }

    @Override
    public void execute() {
        readElement(errorsWithMessages);
        try {
            collectionController.add(cityAddRequestDto);
        } catch (CityValidationException e) {
            errorsWithMessages = e.getErrorsWithMessages();
            execute();
        }
    }

}
