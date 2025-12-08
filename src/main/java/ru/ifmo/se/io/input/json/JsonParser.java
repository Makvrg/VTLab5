package ru.ifmo.se.io.input.json;

import java.io.IOException;
import java.io.InputStreamReader;

public interface JsonParser<T> {

    T parse(InputStreamReader reader) throws IOException;

}
