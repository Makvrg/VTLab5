package com.example.input.commands.lineinput;

import com.example.input.commands.ICommand;
import com.example.input.dto.CityAddRequestDto;
import com.example.input.readers.IReader;
import com.example.input.readers.terminal.Processor;

public class PopulationInputCommand implements ICommand {

    private final CityAddRequestDto cityAddRequestDto;
    private final IReader terminalReader;

    public PopulationInputCommand(CityAddRequestDto cityAddRequestDto,
                                  IReader terminalReader) {
        this.cityAddRequestDto = cityAddRequestDto;
        this.terminalReader = terminalReader;
    }

    @Override
    public void execute() {
        System.out.print("Введите численность населения города > ");
        cityAddRequestDto.setPopulation(
                Processor.processTerminalData(terminalReader.read())
        );
    }

}
