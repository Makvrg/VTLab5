package com.example.validator.exceptions;

import lombok.Getter;

@Getter
public class RawActionDataValidationException extends RuntimeException {

    public RawActionDataValidationException(String message) {
        super(message);
    }

}
