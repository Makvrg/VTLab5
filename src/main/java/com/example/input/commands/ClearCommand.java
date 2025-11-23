package com.example.input.commands;

import com.example.service.CollectionService;

public class ClearCommand implements ICommand {

    private final CollectionService collectionService;

    public ClearCommand(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @Override
    public void execute() {
        if (collectionService.clear()) {
            System.out.println("Коллекция успешно очищена");
        } else {
            System.out.println("Коллекция уже пуста");
        }
    }

}
