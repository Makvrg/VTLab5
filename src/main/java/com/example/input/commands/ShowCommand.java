package com.example.input.commands;

import com.example.service.CollectionService;

public class ShowCommand implements ICommand{

    private final CollectionService collectionService;

    public ShowCommand(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @Override
    public void execute() {
        System.out.println(collectionService.show());
    }

}
