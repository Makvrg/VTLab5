package ru.ifmo.se.io.input.readers.file;

import lombok.RequiredArgsConstructor;
import ru.ifmo.se.io.input.readers.Reader;

import java.io.IOException;
import java.io.InputStreamReader;

@RequiredArgsConstructor
public class FileReader implements Reader {

    private final String name;
    private final InputStreamProvider inputStreamProvider;
    private final String fileName;
    private InputStreamReader inputStreamReader;
    private boolean lastIsNewLine = false;

    @Override
    public String readLine() throws IOException {
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

    @Override
    public String getName() {
        return name;
    }
}
