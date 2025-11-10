package com.example.controller;

import java.util.Map;

public class CityValidationException extends Exception {

    private final Map<String, String> errorsWithMessages;

    public CityValidationException(Map<String, String> messages) {
        super();
        this.errorsWithMessages = messages;
    }

    public Map<String, String> getErrorsWithMessages() {
        return errorsWithMessages;
    }
}
