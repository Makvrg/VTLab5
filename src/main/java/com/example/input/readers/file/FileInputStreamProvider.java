package com.example.input.readers.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileInputStreamProvider implements IInputStreamProvider {

    @Override
    public InputStreamReader open(String fileName) throws IOException {
        return new InputStreamReader(new FileInputStream(fileName));
    }

}
