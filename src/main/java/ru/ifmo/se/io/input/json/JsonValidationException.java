package ru.ifmo.se.io.input.json;

public class JsonValidationException extends RuntimeException {

    public JsonValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}