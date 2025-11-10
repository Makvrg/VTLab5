package com.example.input.commands.lineinput;

import com.example.input.commands.ICommand;
import com.example.input.dto.CityAddRequestDto;
import com.example.input.readers.IReader;
import com.example.input.readers.terminal.Processor;

public class BirthdayInputCommand implements ICommand {

    private final CityAddRequestDto cityAddRequestDto;
    private final IReader terminalReader;

    public BirthdayInputCommand(CityAddRequestDto cityAddRequestDto,
                                IReader terminalReader) {
        this.cityAddRequestDto = cityAddRequestDto;
        this.terminalReader = terminalReader;
    }

    @Override
    public void execute() {
        System.out.println(
                "Дата и время рождения губернатора имеют формат дд-ММ-гггг ЧЧ:мм:сс");
        System.out.print("Введите дату и время рождения губернатора города > ");
        cityAddRequestDto.getGovernor().setBirthday(
                Processor.processTerminalData(terminalReader.read())
        );
    }

}
