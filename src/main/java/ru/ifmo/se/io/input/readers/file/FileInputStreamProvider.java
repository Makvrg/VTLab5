package ru.ifmo.se.io.input.readers.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileInputStreamProvider implements InputStreamProvider {

    @Override
    public InputStreamReader open(String fileName) throws IOException {
        return new InputStreamReader(new FileInputStream(fileName),
                StandardCharsets.UTF_8
        );
    }
}
