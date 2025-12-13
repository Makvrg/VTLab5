package ru.ifmo.se.io.input.readers.terminal;

import lombok.RequiredArgsConstructor;
import ru.ifmo.se.io.input.readers.Reader;

import java.util.Scanner;

@RequiredArgsConstructor
public class TerminalReader implements Reader {

    private final String name;
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public String getName() {
        return name;
    }

}
