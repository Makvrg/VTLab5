package ru.ifmo.se.io.output.json;

import java.io.IOException;

public interface JsonWriter<T> {

    void write(String fileName, T data) throws IOException;

    void writeBackup(T data) throws IOException;

}
