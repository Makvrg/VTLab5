package com.example.input.readers.terminal;

import com.example.input.readers.IReader;

import java.util.Scanner;

public class TerminalReader implements IReader {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String read() {
        return scanner.nextLine();
    }

}
