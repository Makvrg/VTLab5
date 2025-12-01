package com.example.service.exceptions;

import lombok.Getter;

@Getter
public class NonUniqueIdException extends RuntimeException {

    public NonUniqueIdException(String message) {
        super(message);
    }

}
