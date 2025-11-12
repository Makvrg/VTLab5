package com.example.input.commands.lineinput;

import com.example.input.commands.ICommand;
import com.example.input.dto.CityAddRequestDto;
import com.example.input.readers.IReader;
import com.example.input.readers.terminal.Processor;

public class CoordYInputCommand implements ICommand {

    private final CityAddRequestDto cityAddRequestDto;
    private final IReader terminalReader;

    public CoordYInputCommand(CityAddRequestDto cityAddRequestDto,
                              IReader terminalReader) {
        this.cityAddRequestDto = cityAddRequestDto;
        this.terminalReader = terminalReader;
    }

    @Override
    public void execute() {
        System.out.println("y-координата - вещественное число");
        System.out.print("Введите y-координату города > ");
        cityAddRequestDto.getCoordinatesRequestDto().setY(
                Processor.processTerminalData(terminalReader.read())
        );
    }

}
