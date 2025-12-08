package ru.ifmo.se.io.output;

public interface Printer {

    void printlnIfOn(String inputLine);

    void printIfOn(String inputLine);

    void forcePrintln(String inputLine);

    void forcePrint(String inputLine);

    void on();

    void off();

}
