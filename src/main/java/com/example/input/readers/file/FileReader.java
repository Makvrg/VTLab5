package com.example.input.readers.file;

import com.example.input.readers.IReader;

import java.io.IOException;
import java.io.InputStreamReader;

public class FileReader implements IReader {

    private final IInputStreamProvider inputStreamProvider;
    private final String fileName;
    private InputStreamReader inputStreamReader;
    private boolean lastIsNewLine = false;

    public FileReader(IInputStreamProvider inputStreamProvider,
                      String fileName) {
        this.inputStreamProvider = inputStreamProvider;
        this.fileName = fileName;
    }

    @Override
    public String read() throws IOException {
        if (inputStreamReader == null) {
            inputStreamReader = inputStreamProvider.open(fileName);
        }
        StringBuilder sb = new StringBuilder();
        int code;

        while ((code = inputStreamReader.read()) != -1) {
            char c = (char) code;

            if (c == '\n') {
                lastIsNewLine = true;
                break;
            }
            if (c != '\r') {
                lastIsNewLine = false;
                sb.append(c);
            }
        }
        if (code == -1 && sb.isEmpty()) {
            if (lastIsNewLine) {
                lastIsNewLine = false;
                return "";
            } else {
                inputStreamReader.close();
                return null;
            }
        }
        return sb.toString();
    }

}
