package com.example.collection;

import com.example.entity.City;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class CollectionWithInfo {

    public final List<City> collection;
    private final Date initializationDate;
    private final String collectionType;
    private final String elementsType;

    public CollectionWithInfo(List<City> collection,
                              String collectionType,
                              String elementsType) {
        this.collection = collection;
        this.initializationDate = new Date();
        this.collectionType = collectionType;
        this.elementsType = elementsType;
    }

    public int getCountOfElements() {
        return collection.size();
    }

}
