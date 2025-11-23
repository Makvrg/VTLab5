package com.example.input.commands;

import com.example.service.CollectionService;

public class ExitCommand implements ICommand {

    private final CollectionService collectionService;

    public ExitCommand(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @Override
    public void execute() {
        collectionService.exit();
    }

}
