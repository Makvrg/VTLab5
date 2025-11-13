package com.example.repository;

import com.example.collection.CollectionWithInfo;
import com.example.entity.City;

import java.util.Iterator;
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
                collectionWithInfo.getInitializationDate());
        System.out.printf(
                "3. Тип элементов: %s%n", collectionWithInfo.getElementsType());
        System.out.printf(
                "4. Количество элементов: %s%n",
                collectionWithInfo.getCountOfElements());
    }

    public void add(City city) {
        collectionWithInfo.collection.add(city);
        System.out.println("Объект City успешно добавлен в коллекцию");
    }

    public void show() {
        if (collectionWithInfo.getCountOfElements() > 0) {
            System.out.println("Объекты City, содержащиеся в коллекции:");
            for (City city : collectionWithInfo.collection) {
                System.out.println(city);
            }
        } else {
            System.out.println("Коллекция пуста");
        }
    }

    public void removeById(Long id) {
        for (Iterator<City> itr = collectionWithInfo.collection.iterator(); itr.hasNext(); ) {
            if (itr.next().getId().equals(id)) {
                itr.remove();
                System.out.println("Объект City успешно удалён из коллекции по заданному id");
                return;
            }
        }
        System.out.println("Объект City с заданным id не найден в коллекции");
    }

    public void clear() {
        if (collectionWithInfo.getCountOfElements() > 0) {
            collectionWithInfo.collection.clear();
            System.out.println("Из коллекции удалены все объекты City");
        } else {
            System.out.println("Коллекция и так пуста");
        }
    }

    public void head() {
        if (collectionWithInfo.getCountOfElements() > 0) {
            System.out.print("Первый элемент коллекции: ");
            System.out.println(collectionWithInfo.collection.getFirst());
        } else {
            System.out.println("Коллекция пуста");
        }
    }

    public void removeHead() {
        if (collectionWithInfo.getCountOfElements() > 0) {
            System.out.print("Уделён первый элемент коллекции: ");
            System.out.println(collectionWithInfo.collection.removeFirst());
        } else {
            System.out.println("Коллекция пуста");
        }
    }

    public void addIfMax(City city) {
        // TODO
        System.out.println("Я - метод addIfMax репозитория, и я работаю");
    }

}
