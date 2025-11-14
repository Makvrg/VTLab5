package com.example.service;

import com.example.repository.CollectionRepository;

import java.util.function.BiConsumer;

public enum InputMode {
    ALWAYS(
            (repo, actData) -> repo.add(actData.getCity())
    ),
    IF_MAX(
            (repo, actData) -> repo.addIfMax(actData.getCity())
    ),
    UPDATE_BY_ID(
            (repo, actData) -> repo.updateById(actData.getId(),
                                               actData.getCity())
    );

    private final BiConsumer<CollectionRepository, ActionData> action;

    InputMode(BiConsumer<CollectionRepository, ActionData> action) {
        this.action = action;
    }

    public void apply(CollectionRepository collectionRepository,
                      ActionData actionData) {
        action.accept(collectionRepository, actionData);
    }

}
