package com.example.service;

import lombok.Getter;

@Getter
public class RemoveByIdIllegalStateException extends RuntimeException {

    public RemoveByIdIllegalStateException(String message) {
        super(message);
    }

}
