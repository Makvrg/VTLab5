package ru.ifmo.se.io.input.json;

import java.io.IOException;
import java.io.InputStreamReader;

public interface FileParser<T> {

    T parse(InputStreamReader reader) throws IOException;
}
