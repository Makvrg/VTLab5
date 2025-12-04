package com.example.output.json;

import java.io.IOException;

public interface IJsonWriter<T> {

    void write(String fileName, T data) throws IOException;

}
