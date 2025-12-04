package com.example.input.commands;

import com.example.input.readers.IReader;

public interface ICommand {

    String getCommandSignature();

    String getCommandDescription();

    void execute(String[] inputArgs, IReader reader);

}
