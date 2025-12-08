package ru.ifmo.se.service;

import lombok.RequiredArgsConstructor;
import ru.ifmo.se.entity.City;
import ru.ifmo.se.entity.Government;
import ru.ifmo.se.event.ShutdownListener;
import ru.ifmo.se.io.output.dto.CityForJsonDto;
import ru.ifmo.se.repository.CollectionRepository;
import ru.ifmo.se.service.exceptions.CreationDateIsAfterNowException;
import ru.ifmo.se.service.exceptions.NonUniqueIdException;
import ru.ifmo.se.service.exceptions.RemoveByIdIllegalStateException;

import java.util.*;

@RequiredArgsConstructor
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final List<ShutdownListener> listeners = new ArrayList<>();

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
                  "4. Количество элементов: %s",
                  collectionRepository.getCountOfElements()
                  )
          );
        return sb.toString();
    }

    public boolean add(City city) {
        city.setId(createNewId());
        city.setCreationDate(new Date());
        return collectionRepository.add(city);
    }

    public boolean initializationAdd(City city) {
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

    public String show() {
        List<City> cities = collectionRepository.findAll();
        StringBuilder sb = new StringBuilder();
        if (!cities.isEmpty()) {
            sb.append("Содержимые в коллекции объекты City:\n\n");
            cities.forEach(
                    city -> sb.append(city.toString()).append("\n\n")
            );
            sb.delete(sb.length() - 1, sb.length());
        } else {
            sb.append("Коллекция пуста\n");
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

    public List<CityForJsonDto> getCitiesForSave() {
        return collectionRepository.findAll()
                                   .stream()
                                   .map(CityForJsonDto::new)
                                   .toList();
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
            sb.append("Искомых элементов в коллекции не найдено\n");
        }
        return sb.toString();
    }

    public String printFieldDescendingGovernment() {
        List<Government> govList = collectionRepository.findAllGovernment();
        govList.sort(null);
        Collections.reverse(govList);
        StringBuilder sb = new StringBuilder();
        if (!govList.isEmpty()) {
            sb.append("Все упорядоченные по убыванию типы правления из коллекции:\n");
            govList.forEach(
                    city -> sb.append(city.toString()).append("\n")
            );
        } else {
            sb.append("Коллекция пуста\n");
        }
        return sb.toString();
    }


    public void addShutdownListener(ShutdownListener listener) {
        listeners.add(listener);
    }

    private void shutdown() {
        listeners.forEach(ShutdownListener::onShutdown);
    }
}
