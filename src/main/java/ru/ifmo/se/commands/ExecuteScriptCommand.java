package ru.ifmo.se.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.input.readers.file.FileInputStreamProvider;
import ru.ifmo.se.io.input.readers.file.FileReader;
import ru.ifmo.se.io.output.Printer;
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
    private final List<Reader> collectionInputReaders;
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
        if (collectionInputReaders.size() < 10) {
            Reader fileReader = new FileReader(new FileInputStreamProvider(),
                    fileName);
            collectionInputReaders.add(fileReader);
            printer.forcePrintln("Активен режим чтения файла " + fileName);
            printer.off();
        } else {
            printer.forcePrintln(
                    "Превышена глубина рекурсии execute_script, принудительное завершение всей цепочки");
            while (collectionInputReaders.size() > 1) {
                collectionInputReaders.remove(collectionInputReaders.size() - 1);
            }
            printer.forcePrintln("Активен режим чтения терминала");
            printer.on();
        }
    }

}
