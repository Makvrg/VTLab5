package com.example.controller;

import com.example.service.CollectionService;

public class CollectionController {

    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    public void help() {
        collectionService.help();
    }

    public void exit() {
        collectionService.exit();
    }

}
