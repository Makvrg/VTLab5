package com.example.repository;

import com.example.collection.CollectionWithInfo;
import com.example.entity.City;

import java.util.List;

public class CollectionRepository {

    private final CollectionWithInfo collectionWithInfo;

    public CollectionRepository(List<City> collection,
                                String collectionType,
                                String elementsType) {
        this.collectionWithInfo =
                new CollectionWithInfo(collection,
                                       collectionType,
                                       elementsType);
    }

    public void info() {
        System.out.println("Информация о коллекции:");
        System.out.printf(
                "1. Тип коллекции: %s%n", collectionWithInfo.getCollectionType());
        System.out.printf(
                "2. Дата инициализации: %s%n",
                collectionWithInfo.getInitializationDate().toString());
        System.out.printf(
                "3. Тип элементов: %s%n", collectionWithInfo.getElementsType());
        System.out.printf(
                "4. Количество элементов: %s%n",
                collectionWithInfo.getCountOfElements());
    }

}
