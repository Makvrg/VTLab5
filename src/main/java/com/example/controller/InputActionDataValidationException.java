package com.example.controller;

import lombok.Getter;

@Getter
public class InputActionDataValidationException extends Exception {

    public InputActionDataValidationException(String message) {
        super(message);
    }

}
