package com.example.service;

import com.example.CityValidationException;
import com.example.entity.City;
import com.example.entity.Coordinates;
import com.example.entity.Government;
import com.example.entity.Human;
import com.example.event.IShutdownListener;
import com.example.input.dto.CityAddDto;
import com.example.input.dto.CityAddRequestDto;
import com.example.repository.CollectionRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final List<IShutdownListener> listeners = new ArrayList<>();
    private Long id = 0L;

    public CollectionService(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
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

    public void add(CityAddDto cityAddDto) throws CityValidationException {
        double MAX_COORD_X = 579;
        Map<String, String> errorsWithMessages = new LinkedHashMap<>();

        if (cityAddDto.getCoordinatesDto().getX() > MAX_COORD_X) {
            errorsWithMessages.put("x",
                                   "Координата x не должна быть больше " + MAX_COORD_X);
        }
        if (cityAddDto.getArea() <= 0) {
            errorsWithMessages.put("area", "Площадь города должна быть больше 0");
        }
        if (cityAddDto.getPopulation() <= 0) {
            errorsWithMessages.put("population",
                                   "Численность населения должна быть больше 0");
        }
        if (cityAddDto.getPopulationDensity() <= 0) {
            errorsWithMessages.put("populationDensity",
                    "Плотность населения должна быть больше 0");
        }
        if (cityAddDto.getGovernorDto().getHeight() <= 0) {
            errorsWithMessages.put("height",
                                   "Рост губернатора города должен быть больше 0");
        }

        if (errorsWithMessages.isEmpty()) {
            collectionRepository.add(
                    new City(
                            id++,
                            cityAddDto.getName(),
                            new Coordinates(
                                    cityAddDto.getCoordinatesDto().getX(),
                                    cityAddDto.getCoordinatesDto().getY()
                            ),
                            new Date(),
                            cityAddDto.getArea(),
                            cityAddDto.getPopulation(),
                            cityAddDto.getMetersAboveSeaLevel(),
                            cityAddDto.getPopulationDensity(),
                            cityAddDto.getAgglomeration(),
                            cityAddDto.getGovernment(),
                            new Human(
                                    cityAddDto.getGovernorDto().getHeight(),
                                    cityAddDto.getGovernorDto().getBirthday()
                            )
                    )
            );
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


    public void addShutdownListener(IShutdownListener listener) {
        listeners.add(listener);
    }

    private void shutdown() {
        listeners.forEach(IShutdownListener::onShutdown);
    }
}
