package ru.ifmo.se.io.input.readers.factory;

import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.input.readers.file.FileReader;
import ru.ifmo.se.io.input.readers.file.InputStreamProvider;
import ru.ifmo.se.io.input.readers.terminal.TerminalReader;

import java.io.File;
import java.io.IOException;

public class ReaderFactory {

    public Reader createFileReader(String fileName,
                                   InputStreamProvider inputStreamProvider) {
        String canonicalFileName;
        try {
            canonicalFileName = getCanonicalPath(fileName);
        } catch (IOException e) {
            throw new ReaderCreateException(e.getMessage());
        }
        return new FileReader(
                canonicalFileName,
                inputStreamProvider,
                fileName
        );
    }

    public Reader createTerminalReader(String terminalName) {
        return new TerminalReader(terminalName);
    }

    private String getCanonicalPath(String fileName) throws IOException {
        return new File(fileName).getCanonicalPath();
    }
}
