package com.example.service;

import com.example.entity.City;
import com.example.entity.Government;
import com.example.event.IShutdownListener;
import com.example.repository.CollectionRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final List<IShutdownListener> listeners = new ArrayList<>();
    private Long id = 0L;

    public CollectionService(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    public boolean exit() {
        shutdown();
        return true;
    }

    public String info() {
        StringBuilder sb = new StringBuilder();
        sb.append("Информация о коллекции:\n")
          .append(String.format(
                  "1. Тип коллекции: %s%n",
                  collectionRepository.getCollectionType()
                  )
          )
          .append(String.format(
                  "2. Дата инициализации: %s%n",
                  collectionRepository.getInitializationDate()
                  )
          )
          .append(String.format(
                  "3. Тип элементов: %s%n",
                  collectionRepository.getElementsType()
                  )
          )
          .append(String.format(
                  "4. Количество элементов: %s%n",
                  collectionRepository.getCountOfElements()
                  )
          );
        return sb.toString();
    }

    public boolean add(City city) {
        city.setId(++id);
        city.setCreationDate(new Date());
        return collectionRepository.add(city);
    }

    public boolean addIfMax(City city) {
        city.setId(++id);
        city.setCreationDate(new Date());
        Optional<City> maxCity = collectionRepository.findMaxCity();
        if (maxCity.isPresent() && city.compareTo(maxCity.get()) > 0) {
            return collectionRepository.add(city);
        } else {
            id--;
            return false;
        }
    }

    public boolean updateById(City city,
                         ParamTypedData paramTypedData) {
        return collectionRepository.updateById(paramTypedData.getId(), city);
    }

    public String show() {
        List<City> cityList = collectionRepository.findAll();
        StringBuilder sb = new StringBuilder();
        if (!cityList.isEmpty()) {
            sb.append("Содержимые в коллекции объекты City:\n");
            cityList.forEach(
                    city -> sb.append(city.toString()).append("\n")
            );
        } else {
            sb.append("Коллекция пуста");
        }
        return sb.toString();
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

    public String head() {
        Optional<City> headCity = collectionRepository.findFirst();
        return headCity.map(city -> "Первый элемент коллекции: " + city)
                       .orElse("Коллекция пуста");
    }

    public String removeHead() {
        Optional<City> removedCity = collectionRepository.removeHead();
        return removedCity.map(city -> "Удалённый первый элемент коллекции: " + city)
                          .orElse("Коллекция пуста");
    }

    public boolean removeAllByPopulationDensityCommand(long populationDensity) {
        return collectionRepository.removeAllByPopulationDensityCommand(
                populationDensity
        );
    }

    public String filterLessThanPopulationDensity(long populationDensity) {
        List<City> filteredCityList =
                collectionRepository.findAllByLessPopulationDensity(populationDensity);
        StringBuilder sb = new StringBuilder();
        if (!filteredCityList.isEmpty()) {
            sb.append("Искомые объекты City:\n");
            filteredCityList.forEach(
                    city -> sb.append(city.toString()).append("\n")
            );
        } else {
            sb.append("Искомых элементов в коллекции не найдено");
        }
        return sb.toString();
    }

    public String printFieldDescendingGovernment() {
        List<Government> govList = collectionRepository.findAllGovernment();
        govList.sort(null);
        govList = govList.reversed();
        StringBuilder sb = new StringBuilder();
        if (!govList.isEmpty()) {
            sb.append("Все упорядоченные по убыванию типы правления из коллекции:\n");
            govList.forEach(
                    city -> sb.append(city.toString()).append("\n")
            );
        } else {
            sb.append("Коллекция пуста");
        }
        return sb.toString();
    }


    public void addShutdownListener(IShutdownListener listener) {
        listeners.add(listener);
    }

    private void shutdown() {
        listeners.forEach(IShutdownListener::onShutdown);
    }
}
