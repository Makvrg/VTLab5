package com.example.input.commands;

import com.example.service.CollectionService;

public class PrintFieldDescendingGovernmentCommand implements ICommand {

    private final CollectionService collectionService;

    public PrintFieldDescendingGovernmentCommand(
            CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @Override
    public void execute() {
        collectionService.printFieldDescendingGovernment();
    }

}
