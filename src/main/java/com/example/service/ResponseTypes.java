package com.example.service;

import lombok.Getter;


@Getter
public enum ResponseTypes {

    SUCCESS(true),
    STANDARD_FAIL(false),
    EXCEPTION(false);

    ResponseTypes(boolean result) {
        this.result = result;
    }

    private final boolean result;

    private String message;

    public ResponseTypes setMessage(String message) {
        this.message = message;
        return this;
    }

}
