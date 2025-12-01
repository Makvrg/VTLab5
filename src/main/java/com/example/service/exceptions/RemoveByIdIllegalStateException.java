package com.example.service.exceptions;

import lombok.Getter;

@Getter
public class RemoveByIdIllegalStateException extends RuntimeException {

    public RemoveByIdIllegalStateException(String message) {
        super(message);
    }

}
