package ru.ifmo.se.collection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ifmo.se.entity.City;

import java.util.Date;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CollectionWithInfo {

    public final List<City> collection;
    private final Date initializationDate = new Date();
    private final String collectionType;
    private final String elementsType;

    public int getCountOfElements() {
        return collection.size();
    }

}
