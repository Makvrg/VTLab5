package com.example.input.commands;

import com.example.service.CollectionService;

public class RemoveHeadCommand implements ICommand {

    private final CollectionService collectionService;

    public RemoveHeadCommand(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @Override
    public void execute() {
        System.out.println(collectionService.removeHead());
    }

}
