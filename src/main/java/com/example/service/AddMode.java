package com.example.service;

import com.example.entity.City;
import com.example.repository.CollectionRepository;

import java.util.function.BiConsumer;

public enum AddMode {
    ALWAYS(CollectionRepository::add),
    IF_MAX(CollectionRepository::addIfMax);

    private final BiConsumer<CollectionRepository, City> action;

    AddMode(BiConsumer<CollectionRepository, City> action) {
        this.action = action;
    }

    public void apply(CollectionRepository collectionRepository,
                      City city) {
        action.accept(collectionRepository, city);
    }
}
