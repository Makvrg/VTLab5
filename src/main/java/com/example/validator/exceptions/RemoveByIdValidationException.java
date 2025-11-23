package com.example.validator.exceptions;

import lombok.Getter;

@Getter
public class RemoveByIdValidationException extends RuntimeException {

    public RemoveByIdValidationException(String message) {
        super(message);
    }

}
