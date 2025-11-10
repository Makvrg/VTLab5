package com.example.input.readers.terminal;

public class Processor {

    public static String[] processTerminalCommand(String line) {
        return line.strip().split("\\s+");
    }

    public static String processTerminalData(String line) {
        return line.strip();
    }

}
