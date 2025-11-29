package com.example.output;


public interface IPrinter {

    void printlnIfOn(String inputLine);

    void printIfOn(String inputLine);

    void forcePrintln(String inputLine);

    void forcePrint(String inputLine);

    void on();

    void off();

}
