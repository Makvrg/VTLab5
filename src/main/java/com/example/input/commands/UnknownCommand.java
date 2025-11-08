package com.example.input.commands;

public class UnknownCommand implements ICommand {

    private final String[] inputArgs;

    public UnknownCommand(String[] inputArgs) {
        this.inputArgs = inputArgs;
    }

    @Override
    public void execute() {
        System.out.printf("Передана неизвестная команда: %s%n", inputArgs[0]);
    }

}
