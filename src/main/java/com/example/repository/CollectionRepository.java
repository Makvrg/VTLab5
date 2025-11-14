package com.example.repository;

import com.example.collection.CollectionWithInfo;
import com.example.entity.City;
import com.example.entity.Government;

import java.util.ArrayList;
import java.util.Collections;
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

    public void addIfMax(City city) {
        if (collectionWithInfo.getCountOfElements() > 0) {
            City maxCity = Collections.max(collectionWithInfo.collection);
            if (city.compareTo(maxCity) > 0) {
                collectionWithInfo.collection.add(city);
                System.out.println("Новый объект успешно добавлен в коллекцию");
                return;
            }
            System.out.println("Объект не был добавлен в коллекцию");
        }
    }

    public void updateById(Long id, City city) {
        for (int i = 0; i < collectionWithInfo.getCountOfElements(); i++) {
            if (collectionWithInfo.collection.get(i).getId().equals(id)) {

                City updatedCity = collectionWithInfo.collection.get(i);
                updatedCity.setName(city.getName());
                updatedCity.setCoordinates(city.getCoordinates());
                updatedCity.setArea(city.getArea());
                updatedCity.setPopulation(city.getPopulation());
                updatedCity.setMetersAboveSeaLevel(city.getMetersAboveSeaLevel());
                updatedCity.setPopulationDensity(city.getPopulationDensity());
                updatedCity.setAgglomeration(city.getAgglomeration());
                updatedCity.setGovernment(city.getGovernment());
                updatedCity.setGovernor(city.getGovernor());

                System.out.println("Объект City по заданному id обновлён");
                return;
            }
        }
        System.out.println("Объект с таким id не существует в коллекции");
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

    public void removeAllByPopulationDensityCommand(long populationDensity) {
        collectionWithInfo.collection.removeIf(
                city -> city.getPopulationDensity() == populationDensity
        );
        System.out.println("Все City с заданными population density удалены из коллекции");
    }

    public void filterLessThanPopulationDensity(long populationDensity) {
        if (collectionWithInfo.getCountOfElements() > 0) {
            for (City city : collectionWithInfo.collection) {
                if (city.getPopulationDensity() < populationDensity) {
                    System.out.println(city);
                }
            }
        } else {
            System.out.println("Коллекция пуста");
        }
    }

    public void printFieldDescendingGovernment() {
        if (collectionWithInfo.getCountOfElements() > 0) {

            List<Government> listOfGov = new ArrayList<>();
            for (City city : collectionWithInfo.collection) {
                listOfGov.add(city.getGovernment());
            }
            listOfGov.sort(null);
            for (int i = collectionWithInfo.collection.size() - 1; i >= 0; i--) {
                System.out.println(listOfGov.get(i));
            }
        } else {
            System.out.println("Коллекция пуста");
        }
    }

}
