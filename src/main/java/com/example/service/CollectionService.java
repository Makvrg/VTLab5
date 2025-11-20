package com.example.service;

import com.example.CityValidationException;
import com.example.controller.RawActionDataValidationException;
import com.example.entity.City;
import com.example.entity.Coordinates;
import com.example.entity.Human;
import com.example.event.IShutdownListener;
import com.example.input.dto.CityTypedRequestDto;
import com.example.input.dto.ParamRawData;
import com.example.repository.CollectionRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final List<IShutdownListener> listeners = new ArrayList<>();
    private final CityTypedRequestDtoValidator cityTypedRequestDtoValidator;
    private Long id = 0L;

    public CollectionService(CollectionRepository collectionRepository,
                             CityTypedRequestDtoValidator cityTypedRequestDtoValidator) {
        this.collectionRepository = collectionRepository;
        this.cityTypedRequestDtoValidator = cityTypedRequestDtoValidator;
    }

    public void help() {
        System.out.println("Справка по командам приложения:");
        String helpText = """
                help : вывести справку по доступным командам
                
                info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
                
                show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
                
                add {element} : добавить новый элемент в коллекцию
                
                update id {element} : обновить значение элемента коллекции, id которого равен заданному
                
                remove_by_id id : удалить элемент из коллекции по его id
                
                clear : очистить коллекцию
                
                save : сохранить коллекцию в файл
                
                execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
                
                exit : завершить программу (без сохранения в файл)
                
                head : вывести первый элемент коллекции
                
                remove_head : вывести первый элемент коллекции и удалить его
                
                add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
                
                remove_all_by_population_density populationDensity : удалить из коллекции все элементы, значение поля populationDensity которого эквивалентно заданному
                
                filter_less_than_population_density populationDensity : вывести элементы, значение поля populationDensity которых меньше заданного
                
                print_field_descending_government : вывести значения поля government всех элементов в порядке убывания""";
        System.out.println(helpText);
    }

    public void exit() {
        System.out.println("Закрытие приложения");
        shutdown();
    }

    public void info() {
        collectionRepository.info();
    }


    // TODO Выделить в отдельный валидатор
    public void servicingRawActionData(ParamRawData paramRawData)
            throws RawActionDataValidationException {
        if (paramRawData.getId() != null) {
            if (Long.parseLong(paramRawData.getId()) < 0) {
                throw new RawActionDataValidationException(
                        "Аргумент id должен быть положительным числом"
                );
            }
        }
    }

    private void servicingInputCity(CityTypedRequestDto cityTypedRequestDto,
                                    ParamTypedData paramTypedData,
                                    Consumer<ParamTypedData> repoMethod)
            throws CityValidationException {

        Map<String, String> errorsWithMessages =
                cityTypedRequestDtoValidator.validate(cityTypedRequestDto);

        if (errorsWithMessages.isEmpty()) {
            City city = new City(
                    (paramTypedData.getId() != null) ? paramTypedData.getId() : id++,
                    cityTypedRequestDto.getName(),
                    new Coordinates(
                            cityTypedRequestDto.getCoordinates()
                                    .getX(),
                            cityTypedRequestDto.getCoordinates()
                                    .getY()
                    ),
                    new Date(),
                    cityTypedRequestDto.getArea(),
                    cityTypedRequestDto.getPopulation(),
                    cityTypedRequestDto.getMetersAboveSeaLevel(),
                    cityTypedRequestDto.getPopulationDensity(),
                    cityTypedRequestDto.getAgglomeration(),
                    cityTypedRequestDto.getGovernment(),
                    new Human(
                            cityTypedRequestDto.getGovernor()
                                    .getHeight(),
                            cityTypedRequestDto.getGovernor()
                                    .getBirthday()
                    )
            );
            paramTypedData.setCity(city);
            repoMethod.accept(paramTypedData);
        } else {
            throw new CityValidationException(errorsWithMessages);
        }
    }


    public void add(CityTypedRequestDto cityTypedRequestDto,
                    ParamTypedData paramTypedData) throws CityValidationException {
        servicingInputCity(
                cityTypedRequestDto,
                paramTypedData,
                paramTypedDataArg ->
                        collectionRepository.add(paramTypedDataArg.getCity())
        );
    }

    public void addIfMax(CityTypedRequestDto cityTypedRequestDto,
                    ParamTypedData paramTypedData) throws CityValidationException {
        servicingInputCity(
                cityTypedRequestDto,
                paramTypedData,
                paramTypedDataArg ->
                        collectionRepository.addIfMax(paramTypedDataArg.getCity())
        );
    }

    public void updateById(CityTypedRequestDto cityTypedRequestDto,
                         ParamTypedData paramTypedData) throws CityValidationException {
        servicingInputCity(
                cityTypedRequestDto,
                paramTypedData,
                paramTypedDataArg ->
                        collectionRepository.updateById(paramTypedData.getId(),
                                                        paramTypedDataArg.getCity())
        );
    }

    public void show() {
        collectionRepository.show();
    }

    public void removeById(Long id) {
        collectionRepository.removeById(id);
    }

    public void clear() {
        collectionRepository.clear();
    }

    public void head() {
        collectionRepository.head();
    }

    public void removeHead() {
        collectionRepository.removeHead();
    }

    public void removeAllByPopulationDensityCommand(long populationDensity) {
        if (populationDensity <= 0) {
            System.out.println("Плотность населения города должна быть больше 0");
        } else {
            collectionRepository.removeAllByPopulationDensityCommand(
                    populationDensity);
        }
    }

    public void filterLessThanPopulationDensity(long populationDensity) {
        if (populationDensity <= 0) {
            System.out.println("Плотность населения города должна быть больше 0");
        } else {
            collectionRepository.filterLessThanPopulationDensity(
                    populationDensity);
        }
    }

    public void printFieldDescendingGovernment() {
        collectionRepository.printFieldDescendingGovernment();
    }


    public void addShutdownListener(IShutdownListener listener) {
        listeners.add(listener);
    }

    private void shutdown() {
        listeners.forEach(IShutdownListener::onShutdown);
    }
}
