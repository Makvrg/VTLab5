package com.example.validator.exceptions;

import lombok.Getter;

@Getter
public class InputFieldValidationException extends RuntimeException {

    public InputFieldValidationException(String message) {
        super(message);
    }

}
