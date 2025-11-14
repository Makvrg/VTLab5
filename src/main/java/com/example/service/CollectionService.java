package com.example.service;

import com.example.CityValidationException;
import com.example.controller.InputActionDataValidationException;
import com.example.entity.City;
import com.example.entity.Coordinates;
import com.example.entity.Human;
import com.example.event.IShutdownListener;
import com.example.input.dto.CityInputDto;
import com.example.input.dto.InputActionData;
import com.example.repository.CollectionRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final List<IShutdownListener> listeners = new ArrayList<>();
    private final CityInputDtoValidator cityInputDtoValidator;
    private Long id = 0L;

    public CollectionService(CollectionRepository collectionRepository,
                             CityInputDtoValidator cityInputDtoValidator) {
        this.collectionRepository = collectionRepository;
        this.cityInputDtoValidator = cityInputDtoValidator;
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
    public void servicingInputActionData(InputActionData inputActionData)
            throws InputActionDataValidationException {
        if (inputActionData.getId() != null) {
            if (Long.parseLong(inputActionData.getId()) < 0) {
                throw new InputActionDataValidationException(
                        "Аргумент id должен быть положительным числом"
                );
            }
        }
    }

    public void servicingInputCity(CityInputDto cityInputDto,
                                   InputMode inputMode,
                                   ActionData actionData) throws CityValidationException {

        Map<String, String> errorsWithMessages =
                cityInputDtoValidator.validate(cityInputDto);

        if (errorsWithMessages.isEmpty()) {
            City city = new City(
                    (actionData.getId() != null) ? actionData.getId() : id++,
                    cityInputDto.getName(),
                    new Coordinates(
                            cityInputDto.getCoordinatesDto()
                                    .getX(),
                            cityInputDto.getCoordinatesDto()
                                    .getY()
                    ),
                    new Date(),
                    cityInputDto.getArea(),
                    cityInputDto.getPopulation(),
                    cityInputDto.getMetersAboveSeaLevel(),
                    cityInputDto.getPopulationDensity(),
                    cityInputDto.getAgglomeration(),
                    cityInputDto.getGovernment(),
                    new Human(
                            cityInputDto.getGovernorDto()
                                    .getHeight(),
                            cityInputDto.getGovernorDto()
                                    .getBirthday()
                    )
            );
            actionData.setCity(city);

            inputMode.apply(collectionRepository, actionData);

        } else {
            throw new CityValidationException(errorsWithMessages);
        }
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
