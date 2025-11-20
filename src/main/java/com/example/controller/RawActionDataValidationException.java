package com.example.controller;

import lombok.Getter;

@Getter
public class RawActionDataValidationException extends Exception {

    public RawActionDataValidationException(String message) {
        super(message);
    }

}
