package com.example.input.commands.lineinput;

import com.example.input.commands.ICommand;
import com.example.input.dto.CityAddRequestDto;
import com.example.input.readers.IReader;
import com.example.input.readers.terminal.Processor;

public class HeightInputCommand implements ICommand {

    private final CityAddRequestDto cityAddRequestDto;
    private final IReader terminalReader;

    public HeightInputCommand(CityAddRequestDto cityAddRequestDto,
                              IReader terminalReader) {
        this.cityAddRequestDto = cityAddRequestDto;
        this.terminalReader = terminalReader;
    }

    @Override
    public void execute() {
        System.out.println("Рост губернатора - вещественное число в метрах");
        System.out.print("Введите рост губернатора города > ");
        cityAddRequestDto.getGovernorRequestDto().setHeight(
                Processor.processTerminalData(terminalReader.read())
        );
    }

}
