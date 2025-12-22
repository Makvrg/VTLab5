package ru.ifmo.se.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.input.readers.factory.ReaderCreateException;
import ru.ifmo.se.io.input.readers.factory.ReaderFactory;
import ru.ifmo.se.io.input.readers.file.InputStreamProvider;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.validator.CommandValidator;
import ru.ifmo.se.validator.exceptions.ExecuteScriptValidateException;

import java.util.List;

@RequiredArgsConstructor
public class ExecuteScriptCommand implements Command {

    @Getter
    private final String commandSignature = "execute_script file_name";
    @Getter
    private final String commandDescription =
            "считать и исполнить скрипт из указанного файла. В скрипте содержатся "
                    + "команды в таком же виде, в котором их вводит пользователь "
                    + "в интерактивном режиме.";

    private final CommandValidator commandValidator;
    private final InputStreamProvider inputStreamProvider;
    private final ReaderFactory readerFactory;
    private final List<Reader> commandInputReaders;
    private final Printer printer;

    @Override
    public void execute(String[] inputArgs, Reader ignoredReader) {
        String fileName = (inputArgs.length > 1) ? inputArgs[1] : null;
        try {
            commandValidator.validateExecuteScript(fileName);
        } catch (ExecuteScriptValidateException e) {
            printer.forcePrintln(e.getMessage());
            return;
        }
        Reader currentFileReader;
        try {
            currentFileReader = readerFactory.createFileReader(
                    fileName,
                    inputStreamProvider
            );
        } catch (ReaderCreateException e) {
            printer.forcePrintln(
                    "Файл с указанным названием не найден или к нему нет доступа");
            return;
        }

        for (Reader reader : commandInputReaders) {
            if (reader.getName().equals(currentFileReader.getName())) {
                printer.forcePrintln(
                        "Во избежание рекурсии выполняется "
                                + "принудительное завершение всей цепочки скриптов");
                while (commandInputReaders.size() > 1) {
                    commandInputReaders.remove(commandInputReaders.size() - 1);
                }
                printer.forcePrintln("Активен режим чтения терминала");
                printer.on();
                return;
            }
        }

        commandInputReaders.add(currentFileReader);
        printer.forcePrintln("Активен режим чтения файла " + currentFileReader.getName());
        printer.off();
    }
}
