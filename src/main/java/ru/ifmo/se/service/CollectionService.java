package ru.ifmo.se.service;

import lombok.RequiredArgsConstructor;
import ru.ifmo.se.entity.City;
import ru.ifmo.se.entity.Government;
import ru.ifmo.se.event.ShutdownListener;
import ru.ifmo.se.io.output.formatter.OutputStringFormatter;
import ru.ifmo.se.repository.CollectionRepository;
import ru.ifmo.se.service.exceptions.CreationDateIsAfterNowException;
import ru.ifmo.se.service.exceptions.NonUniqueIdException;
import ru.ifmo.se.service.exceptions.RemoveByIdIllegalStateException;

import java.util.*;

@RequiredArgsConstructor
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final OutputStringFormatter formatter;
    private final List<ShutdownListener> listeners = new ArrayList<>();

    public boolean exit() {
        shutdown();
        return true;
    }

    public String info() {
        return formatter.formatCollectionWithInfoFields(
                collectionRepository.getCollectionType(),
                collectionRepository.getInitializationDate(),
                collectionRepository.getElementsType(),
                collectionRepository.getCountOfElements()
        );
    }

    public boolean add(City city) {
        city.setId(createNewId());
        city.setCreationDate(new Date());
        return collectionRepository.add(city);
    }

    public boolean addInitCity(City city) {
        if (collectionRepository.existsById(city.getId())) {
            throw new NonUniqueIdException("Передан уже существующий id");
        }
        if (city.getCreationDate().after(new Date())) {
            throw new CreationDateIsAfterNowException("Передана дата и время создания объекта City из будущего");
        }
        if (city.getGovernor().getBirthday() != null
                && city.getGovernor().getBirthday().after(new Date())) {
            throw new CreationDateIsAfterNowException("Передана дата и время рождения губернатора из будущего");
        }
        return collectionRepository.add(city);
    }

    private Long createNewId() {
        Optional<Long> maxId = collectionRepository.findMaxId();
        return maxId.map(aLong -> aLong + 1).orElse(0L);
    }

    public boolean addIfMax(City city) {
        Optional<City> maxCity = collectionRepository.findMaxCity();
        if (maxCity.isPresent() && city.compareTo(maxCity.get()) > 0) {
            return collectionRepository.add(city);
        } else {
            return false;
        }
    }

    public boolean updateById(City city,
                         ParamTypedData paramTypedData) {
        return collectionRepository.updateById(paramTypedData.getId(), city);
    }

    public List<City> show() {
        return collectionRepository.findAll();
    }

    public boolean removeById(Long id) {
        try {
            return collectionRepository.deleteById(id);
        } catch (IllegalStateException e) {
            throw new RemoveByIdIllegalStateException(e.getMessage());
        }
    }

    public boolean clear() {
        return collectionRepository.deleteAll();
    }

    public List<City> getCitiesForSave() {
        return collectionRepository.findAll();
    }

    public Optional<City> head() {
        return collectionRepository.findFirst();

    }

    public Optional<City> removeHead() {
        return collectionRepository.removeHead();
    }

    public boolean removeAllByPopulationDensityCommand(long populationDensity) {
        return collectionRepository.removeAllByPopulationDensityCommand(
                populationDensity
        );
    }

    public List<City> filterLessThanPopulationDensity(long populationDensity) {
        return collectionRepository.findAllByLessPopulationDensity(populationDensity);
    }

    public List<Government> printFieldDescendingGovernment() {
        List<Government> govList = collectionRepository.findAllGovernment();
        govList.sort(null);
        Collections.reverse(govList);
        return govList;
    }


    public void addShutdownListener(ShutdownListener listener) {
        listeners.add(listener);
    }

    private void shutdown() {
        listeners.forEach(ShutdownListener::onShutdown);
    }

}
