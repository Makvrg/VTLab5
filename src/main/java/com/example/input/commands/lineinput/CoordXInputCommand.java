package com.example.input.commands.lineinput;

import com.example.input.commands.ICommand;
import com.example.input.dto.CityAddRequestDto;
import com.example.input.readers.IReader;
import com.example.input.readers.terminal.Processor;

public class CoordXInputCommand implements ICommand {

    private final CityAddRequestDto cityAddRequestDto;
    private final IReader terminalReader;

    public CoordXInputCommand(CityAddRequestDto cityAddRequestDto,
                              IReader terminalReader) {
        this.cityAddRequestDto = cityAddRequestDto;
        this.terminalReader = terminalReader;
    }

    @Override
    public void execute() {
        System.out.println("x-координата - вещественное число, не превышающее 579");
        System.out.print("Введите x-координату города > ");
        cityAddRequestDto.getCoordinates().setX(
                Processor.processTerminalData(terminalReader.read())
        );
    }

}
