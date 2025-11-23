package com.example.input.commands;

import com.example.input.readers.IReader;
import com.example.input.readers.file.FileInputStreamProvider;
import com.example.input.readers.file.FileReader;
import com.example.validator.CommandValidator;
import com.example.validator.exceptions.ExecuteScriptValidateException;

import java.util.function.Consumer;


public class ExecuteScriptCommand implements ICommand {

    private final CommandValidator commandValidator;
    private final String fileName;
    private final Consumer<IReader> setReaderInCollectionInput;
    private final Consumer<IReader> setReaderInCommandDistributor;

    public ExecuteScriptCommand(
            CommandValidator commandValidator,
            String[] args,
            Consumer<IReader> setReaderInCollectionInput,
            Consumer<IReader> setReaderInCommandDistributor) {
        this.commandValidator = commandValidator;
        fileName = (args.length > 1) ? args[1] : null;
        this.setReaderInCollectionInput = setReaderInCollectionInput;
        this.setReaderInCommandDistributor = setReaderInCommandDistributor;
    }

    @Override
    public void execute() {
        try {
            commandValidator.validateExecuteScript(fileName);
        } catch (ExecuteScriptValidateException e) {
            System.out.println(e.getMessage());
            return;
        }

        IReader fileReader = new FileReader(new FileInputStreamProvider(),
                                            fileName);
        setReaderInCollectionInput.accept(fileReader);
        setReaderInCommandDistributor.accept(fileReader);
        System.out.println("Активен режим чтения файла");
    }

}
