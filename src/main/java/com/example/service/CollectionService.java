package com.example.service;

import com.example.event.IShutdownListener;
import com.example.repository.CollectionRepository;

import java.util.ArrayList;
import java.util.List;

public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final List<IShutdownListener> listeners = new ArrayList<>();

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


    public void addShutdownListener(IShutdownListener listener) {
        listeners.add(listener);
    }

    private void shutdown() {
        listeners.forEach(IShutdownListener::onShutdown);
    }
}
