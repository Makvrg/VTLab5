package com.example.input.readers.file;

import com.example.input.readers.IReader;

import java.io.IOException;
import java.io.InputStreamReader;

public class FileReader implements IReader {

    private final InputStreamProvider inputStreamProvider;
    private final String fileName;

    public FileReader(InputStreamProvider inputStreamProvider,
                      String fileName) {
        this.inputStreamProvider = inputStreamProvider;
        this.fileName = fileName;
    }

    @Override
    public String read() throws IOException {
        try (InputStreamReader inputStreamReader = inputStreamProvider.open(fileName)) {
            StringBuilder sb = new StringBuilder();
            int code;

            while ((code = inputStreamReader.read()) != -1) {
                char c = (char) code;

                if (c == '\n') {
                    break;
                }
                if (c != '\r') {
                    sb.append(c);
                }
            }
            if (code == -1 && sb.isEmpty()) {
                return null;
            }
            return sb.toString();
        }
    }

}
