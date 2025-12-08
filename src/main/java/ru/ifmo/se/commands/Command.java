package ru.ifmo.se.commands;

import ru.ifmo.se.io.input.readers.Reader;

public interface Command {

    String getCommandSignature();

    String getCommandDescription();

    void execute(String[] inputArgs, Reader reader);

}
