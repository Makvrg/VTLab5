package com.example.input.commands;

import com.example.input.readers.IReader;
import com.example.input.readers.file.FileInputStreamProvider;
import com.example.input.readers.file.FileReader;
import com.example.output.IPrinter;
import com.example.validator.CommandValidator;
import com.example.validator.exceptions.ExecuteScriptValidateException;

import java.util.List;


public class ExecuteScriptCommand implements ICommand {

    private final CommandValidator commandValidator;
    private final String fileName;
    private final List<IReader> collectionInputReaders;
    private final IPrinter printer;

    public ExecuteScriptCommand(
            CommandValidator commandValidator,
            String[] args,
            List<IReader> collectionInputReaders,
            IPrinter printer) {
        this.commandValidator = commandValidator;
        fileName = (args.length > 1) ? args[1] : null;
        this.collectionInputReaders = collectionInputReaders;
        this.printer = printer;
    }

    @Override
    public void execute() {
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
