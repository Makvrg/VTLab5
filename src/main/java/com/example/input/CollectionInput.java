package com.example.input;

import com.example.controller.CollectionController;
import com.example.event.IShutdownListener;
import com.example.input.readers.IReader;
import com.example.input.readers.terminal.CommandDistributor;
import com.example.input.readers.terminal.Processor;

public class CollectionInput implements IRunnable, IShutdownListener {

    private final IReader terminalReader;
    private final IReader fileReader;
    private final CollectionController collectionController;
    private boolean shutdown = false;

    public CollectionInput(IReader terminalReader,
                           IReader fileReader,
                           CollectionController collectionController) {
        this.terminalReader = terminalReader;
        this.fileReader = fileReader;
        this.collectionController = collectionController;
    }

    @Override
    public void run() {
        System.out.println("Приложение запущено");
        CommandDistributor commandDistributor =
                new CommandDistributor(collectionController);

        while (!shutdown) {
            System.out.print("> ");
            String[] inputArgs = Processor.process(terminalReader.read());

            commandDistributor.distribute(inputArgs);
        }
    }

    @Override
    public void onShutdown() {
        shutdown = true;
    }

}
