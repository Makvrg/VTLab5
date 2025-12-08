package ru.ifmo.se.io.input.readers.file;

import java.io.IOException;
import java.io.InputStreamReader;

public interface InputStreamProvider {

    InputStreamReader open(String fileName) throws IOException;

}
