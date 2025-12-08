package ru.ifmo.se.repository;

import lombok.RequiredArgsConstructor;
import ru.ifmo.se.collection.CollectionWithInfo;
import ru.ifmo.se.entity.City;
import ru.ifmo.se.entity.Government;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CollectionRepository {

    private final CollectionWithInfo collectionWithInfo;

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
                    Collections.max(collectionWithInfo.collection)
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
        return new ArrayList<>(collectionWithInfo.collection);
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
                    collectionWithInfo.collection.get(0)
            );
        } else {
            return Optional.empty();
        }
    }

    public Optional<City> removeHead() {
        if (collectionWithInfo.getCountOfElements() > 0) {
            return Optional.of(
                    collectionWithInfo.collection.remove(0)
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
        return collectionWithInfo.collection.stream()
                .filter(city -> city.getPopulationDensity() < populationDensity)
                .collect(Collectors.toCollection(ArrayList::new)
                );
    }

    public List<Government> findAllGovernment() {
        return collectionWithInfo.collection.stream()
                .map(City::getGovernment)
                .collect(Collectors.toCollection(ArrayList::new)
                );
    }

}
