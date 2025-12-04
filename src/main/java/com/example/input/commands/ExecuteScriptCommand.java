package com.example.input.commands;

import com.example.input.readers.IReader;
import com.example.input.readers.file.FileInputStreamProvider;
import com.example.input.readers.file.FileReader;
import com.example.output.IPrinter;
import com.example.validator.CommandValidator;
import com.example.validator.exceptions.ExecuteScriptValidateException;
import lombok.Getter;

import java.util.List;


public class ExecuteScriptCommand implements ICommand {

    @Getter
    private final String commandSignature = "execute_script file_name";
    @Getter
    private final String commandDescription =
            "считать и исполнить скрипт из указанного файла. В скрипте содержатся "
                    + "команды в таком же виде, в котором их вводит пользователь "
                    + "в интерактивном режиме.";

    private final CommandValidator commandValidator;
    private final List<IReader> collectionInputReaders;
    private final IPrinter printer;

    public ExecuteScriptCommand(
            CommandValidator commandValidator,
            List<IReader> collectionInputReaders,
            IPrinter printer) {
        this.commandValidator = commandValidator;
        this.collectionInputReaders = collectionInputReaders;
        this.printer = printer;
    }

    @Override
    public void execute(String[] inputArgs, IReader ignoredReader) {
        String fileName = (inputArgs.length > 1) ? inputArgs[1] : null;
        try {
            commandValidator.validateExecuteScript(fileName);
        } catch (ExecuteScriptValidateException e) {
            printer.forcePrintln(e.getMessage());
            return;
        }
        if (collectionInputReaders.size() < 10) {
            IReader fileReader = new FileReader(new FileInputStreamProvider(),
                    fileName);
            collectionInputReaders.addLast(fileReader);
            printer.forcePrintln("Активен режим чтения файла " + fileName);
            printer.off();
        } else {
            printer.forcePrintln(
                    "Превышена глубина рекурсии execute_script, принудительное завершение всей цепочки");
            while (collectionInputReaders.size() > 1) {
                collectionInputReaders.removeLast();
            }
            printer.forcePrintln("Активен режим чтения терминала");
            printer.on();
        }
    }

}
