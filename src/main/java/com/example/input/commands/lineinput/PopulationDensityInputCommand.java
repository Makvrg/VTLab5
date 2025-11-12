package com.example.input.commands.lineinput;

import com.example.input.commands.ICommand;
import com.example.input.dto.CityAddRequestDto;
import com.example.input.readers.IReader;
import com.example.input.readers.terminal.Processor;

public class PopulationDensityInputCommand implements ICommand {

    private final CityAddRequestDto cityAddRequestDto;
    private final IReader terminalReader;

    public PopulationDensityInputCommand(CityAddRequestDto cityAddRequestDto,
                                         IReader terminalReader) {
        this.cityAddRequestDto = cityAddRequestDto;
        this.terminalReader = terminalReader;
    }

    @Override
    public void execute() {
        System.out.print("Введите целочисленную плотность населения города > ");
        cityAddRequestDto.setPopulationDensity(
                Processor.processTerminalData(terminalReader.read())
        );
    }

}
