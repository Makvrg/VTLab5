package ru.ifmo.se.collection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ifmo.se.entity.City;

import java.util.Date;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CollectionWithInfo {

    private final List<City> collection;
    private final Date initializationDate = new Date();
    private final Class<?> collectionType;
    private final Class<?> elementsType;

    public int getCountOfElements() {
        return collection.size();
    }
}
