package com.example.validator.exceptions;

import lombok.Getter;

@Getter
public class RemoveAllByPopulationDensityValidationException extends RuntimeException {

    public RemoveAllByPopulationDensityValidationException(String message) {
        super(message);
    }

}
