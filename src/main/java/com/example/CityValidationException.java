package com.example;

import lombok.Getter;

import java.util.Map;

@Getter
public class CityValidationException extends Exception {

    private final Map<String, String> errorsWithMessages;

    public CityValidationException(Map<String, String> messages) {
        super();
        this.errorsWithMessages = messages;
    }

}
