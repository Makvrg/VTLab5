package com.example.input.terminal;

import com.example.input.Reader;

import java.util.Scanner;

public class TerminalReader implements Reader {

    private Scanner scanner = new Scanner(System.in);

    @Override
    public String read() {
        return scanner.nextLine();
    }

}
