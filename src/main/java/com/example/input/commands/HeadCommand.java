package com.example.input.commands;

import com.example.service.CollectionService;

public class HeadCommand implements ICommand {

    private final CollectionService collectionService;

    public HeadCommand(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @Override
    public void execute() {
        System.out.println(collectionService.head());
    }

}
