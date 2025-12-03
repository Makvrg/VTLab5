package com.example.validator.exceptions;

import lombok.Getter;

@Getter
public class ParamRawDataValidationException extends RuntimeException {

    public ParamRawDataValidationException(String message) {
        super(message);
    }

}
