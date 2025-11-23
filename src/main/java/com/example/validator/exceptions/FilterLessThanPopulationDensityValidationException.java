package com.example.validator.exceptions;

import lombok.Getter;

@Getter
public class FilterLessThanPopulationDensityValidationException extends RuntimeException {

    public FilterLessThanPopulationDensityValidationException(String message) {
        super(message);
    }

}
