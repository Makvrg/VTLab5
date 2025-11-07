package com.example.input.terminal;

public class Processor {

    public static String[] process(String line) {
        return line.strip().split("\\s+");
    }

}
