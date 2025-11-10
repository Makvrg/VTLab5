package com.example.input.commands.lineinput;

import com.example.input.commands.ICommand;
import com.example.input.dto.CityAddRequestDto;
import com.example.input.readers.IReader;
import com.example.input.readers.terminal.Processor;

public class MetersAboveSeaInputCommand implements ICommand {

    private final CityAddRequestDto cityAddRequestDto;
    private final IReader terminalReader;

    public MetersAboveSeaInputCommand(CityAddRequestDto cityAddRequestDto,
                                      IReader terminalReader) {
        this.cityAddRequestDto = cityAddRequestDto;
        this.terminalReader = terminalReader;
    }

    @Override
    public void execute() {
        System.out.println("Количество метров над уровнем моря - вещественное число");
        System.out.print("Введите количество метров над уровнем моря > ");
        cityAddRequestDto.setMetersAboveSeaLevel(
                Processor.processTerminalData(terminalReader.read())
        );
    }

}
