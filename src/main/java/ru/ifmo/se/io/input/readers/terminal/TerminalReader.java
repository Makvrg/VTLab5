package ru.ifmo.se.io.input.readers.terminal;

import ru.ifmo.se.io.input.readers.Reader;

import java.util.Scanner;

public class TerminalReader implements Reader {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

}
