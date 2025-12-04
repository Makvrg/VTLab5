package com.example.repository;

import com.example.collection.CollectionWithInfo;
import com.example.entity.City;
import com.example.entity.Government;

import java.util.*;

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

    public String getCollectionType() {
        return collectionWithInfo.getCollectionType();
    }

    public Date getInitializationDate() {
        return collectionWithInfo.getInitializationDate();
    }

    public String getElementsType() {
        return collectionWithInfo.getElementsType();
    }

    public int getCountOfElements() {
        return collectionWithInfo.getCountOfElements();
    }

    public boolean existsById(Long id) {
        for (City city : collectionWithInfo.getCollection()) {
            if (city.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public Optional<Long> findMaxId() {
        if (collectionWithInfo.getCountOfElements() > 0) {
            return collectionWithInfo.collection.stream()
                                                .map(City::getId)
                                                .max(Long::compareTo);
        }
        return Optional.empty();
    }

    public boolean add(City city) {
        return collectionWithInfo.collection.add(city);
    }

    public Optional<City> findMaxCity() {
        if (collectionWithInfo.getCountOfElements() > 0) {
            return Optional.of(
                    new City(
                            Collections.max(collectionWithInfo.collection)
                    )
            );
        }
        return Optional.empty();
    }

    public boolean updateById(Long id, City city) {
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
                return true;
            }
        }
        return false;
    }

    public List<City> findAll() {
        return new ArrayList<>(
                collectionWithInfo.collection.stream()
                                             .map(City::new)
                                             .toList()
        );
    }

    public boolean deleteById(Long id) {
        for (Iterator<City> itr = collectionWithInfo.collection.iterator(); itr.hasNext(); ) {
            if (itr.next().getId().equals(id)) {
                itr.remove();
                return true;
            }
        }
        return false;
    }

    public boolean deleteAll() {
        if (collectionWithInfo.getCountOfElements() > 0) {
            collectionWithInfo.collection.clear();
            return true;
        } else {
            return false;
        }
    }

    public Optional<City> findFirst() {
        if (collectionWithInfo.getCountOfElements() > 0) {
            return Optional.of(
                    new City(
                            collectionWithInfo.collection.getFirst()
                    )
            );
        } else {
            return Optional.empty();
        }
    }

    public Optional<City> removeHead() {
        if (collectionWithInfo.getCountOfElements() > 0) {
            return Optional.of(
                    new City(
                            collectionWithInfo.collection.removeFirst()
                    )
            );
        } else {
            return Optional.empty();
        }
    }

    public boolean removeAllByPopulationDensityCommand(long populationDensity) {
        return collectionWithInfo.collection.removeIf(
                city -> city.getPopulationDensity() == populationDensity
        );
    }

    public List<City> findAllByLessPopulationDensity(long populationDensity) {
        return new ArrayList<>(
                collectionWithInfo.collection
                        .stream()
                        .filter(city -> city.getPopulationDensity() < populationDensity)
                        .map(City::new)
                        .toList()
        );
    }

    public List<Government> findAllGovernment() {
        return new ArrayList<>(
                findAll().stream().map(City::getGovernment).toList()
        );
    }

}
