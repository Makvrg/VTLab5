package com.example.input.json;

import java.io.IOException;
import java.io.InputStreamReader;

public interface IJsonParser<T> {

    T parse(InputStreamReader reader) throws IOException;

}
