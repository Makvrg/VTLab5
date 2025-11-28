package com.example.output;

public class OutputPrinter implements IPrinter {

    private boolean onOff = true;

    @Override
    public void print(String inputLine) {
        if (onOff) {
            System.out.println(inputLine);
        }
    }

    @Override
    public void on() {
        onOff = true;
    }

    @Override
    public void off() {
        onOff = false;
    }

}
