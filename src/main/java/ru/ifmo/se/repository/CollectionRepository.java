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

    public Class<?> getCollectionType() {
        return collectionWithInfo.getCollectionType();
    }

    public Date getInitializationDate() {
        return collectionWithInfo.getInitializationDate();
    }

    public Class<?> getElementsType() {
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
            return collectionWithInfo.getCollection().stream()
                                                .map(City::getId)
                                                .max(Long::compareTo);
        }
        return Optional.empty();
    }

    public boolean add(City city) {
        return collectionWithInfo.getCollection().add(city);
    }

    public Optional<City> findMaxCity() {
        if (collectionWithInfo.getCountOfElements() > 0) {
            return Optional.of(
                    Collections.max(collectionWithInfo.getCollection())
            );
        }
        return Optional.empty();
    }

    public boolean updateById(Long id, City newData) {
        for (City city : collectionWithInfo.getCollection()) {
            if (city.getId().equals(id)) {
                updateCity(city, newData);
                return true;
            }
        }
        return false;
    }

    private void updateCity(City targetCity, City newData) {
        targetCity.setName(newData.getName());
        targetCity.setCoordinates(newData.getCoordinates());
        targetCity.setArea(newData.getArea());
        targetCity.setPopulation(newData.getPopulation());
        targetCity.setMetersAboveSeaLevel(newData.getMetersAboveSeaLevel());
        targetCity.setPopulationDensity(newData.getPopulationDensity());
        targetCity.setAgglomeration(newData.getAgglomeration());
        targetCity.setGovernment(newData.getGovernment());
        targetCity.setGovernor(newData.getGovernor());
    }

    public List<City> findAll() {
        return new ArrayList<>(collectionWithInfo.getCollection());
    }

    public boolean deleteById(Long id) {
        for (Iterator<City> itr = collectionWithInfo.getCollection().iterator(); itr.hasNext(); ) {
            if (itr.next().getId().equals(id)) {
                itr.remove();
                return true;
            }
        }
        return false;
    }

    public boolean deleteAll() {
        if (collectionWithInfo.getCountOfElements() > 0) {
            collectionWithInfo.getCollection().clear();
            return true;
        } else {
            return false;
        }
    }

    public Optional<City> findFirst() {
        if (collectionWithInfo.getCountOfElements() > 0) {
            return Optional.of(
                    collectionWithInfo.getCollection().get(0)
            );
        } else {
            return Optional.empty();
        }
    }

    public Optional<City> removeHead() {
        if (collectionWithInfo.getCountOfElements() > 0) {
            return Optional.of(
                    collectionWithInfo.getCollection().remove(0)
            );
        } else {
            return Optional.empty();
        }
    }

    public boolean removeAllByPopulationDensityCommand(long populationDensity) {
        return collectionWithInfo.getCollection().removeIf(
                city -> city.getPopulationDensity() == populationDensity
        );
    }

    public List<City> findAllByLessPopulationDensity(long populationDensity) {
        return collectionWithInfo.getCollection().stream()
                .filter(city -> city.getPopulationDensity() < populationDensity)
                .collect(Collectors.toCollection(ArrayList::new)
                );
    }

    public List<Government> findAllGovernment() {
        return collectionWithInfo.getCollection().stream()
                .map(City::getGovernment)
                .collect(Collectors.toCollection(ArrayList::new)
                );
    }
}
