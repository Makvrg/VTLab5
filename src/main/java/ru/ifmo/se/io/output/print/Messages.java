package ru.ifmo.se.io.output.print;

public class Messages {

    public static final String CITY_INIT_VALID_EXC = """
            При инициализации коллекции данными из файла
            произошла ошибка валидации объекта City с id: %d
            и порядковым номером: %d
            """;
    public static final String CITY_INIT_UNKNOWN_EXC = """
            При инициализации коллекции данными из файла
            по неизвестной причине не удалось добавить в коллекцию объект City с id: %d
            и порядковым номером: %d
            """;
    public static final String CITY_INIT_ADD_EXC = """
            При инициализации коллекции данными из файла
            произошла ошибка добавления объекта City с id: %d
            и порядковым номером: %d
            """;

}
