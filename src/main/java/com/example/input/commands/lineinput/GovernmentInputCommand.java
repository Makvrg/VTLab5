package com.example.input.commands.lineinput;

import com.example.entity.Government;
import com.example.input.commands.ICommand;
import com.example.input.dto.CityAddRequestDto;
import com.example.input.readers.IReader;
import com.example.input.readers.terminal.Processor;

public class GovernmentInputCommand implements ICommand {

    private final CityAddRequestDto cityAddRequestDto;
    private final IReader terminalReader;

    public GovernmentInputCommand(CityAddRequestDto cityAddRequestDto,
                                  IReader terminalReader) {
        this.cityAddRequestDto = cityAddRequestDto;
        this.terminalReader = terminalReader;
    }

    @Override
    public void execute() {
        System.out.println("Возможные типы правления города:");
        for (Government government : Government.values()) {
            System.out.println("- " + government.toString());
        }
        System.out.print("Введите тип правления города > ");
        cityAddRequestDto.setGovernment(
                Processor.processTerminalData(terminalReader.read())
        );
    }

}
