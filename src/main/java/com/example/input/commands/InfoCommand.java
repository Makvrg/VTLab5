package com.example.input.commands;

import com.example.service.CollectionService;

public class InfoCommand implements ICommand {

    private final CollectionService collectionService;

    public InfoCommand(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @Override
    public void execute() {
        System.out.println(collectionService.info());
    }

}
