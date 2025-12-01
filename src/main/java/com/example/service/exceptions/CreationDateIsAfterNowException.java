package com.example.service.exceptions;

import lombok.Getter;

@Getter
public class CreationDateIsAfterNowException extends RuntimeException {

    public CreationDateIsAfterNowException(String message) {
        super(message);
    }

}
