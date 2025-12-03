package com.example.input.commands;

import com.example.input.readers.IReader;

public interface ICommand {

    void execute(String[] inputArgs, IReader reader);

}
