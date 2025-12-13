package ru.ifmo.se.io.output.formatter;

import ru.ifmo.se.entity.City;
import ru.ifmo.se.entity.Government;
import ru.ifmo.se.io.input.readers.Reader;

import java.util.Date;
import java.util.List;

public class OutputStringFormatter {

    public String formatCity(City city) {
        StringBuilder sb = new StringBuilder();
        sb.append("Объект City:\n")
          .append("  Id: ").append(city.getId()).append("\n")
          .append("  Название: ").append(city.getName()).append("\n")
          .append("  Координата x: ").append(city.getCoordinates().getX()).append("\n")
          .append("  Координата y: ").append(city.getCoordinates().getY()).append("\n")
          .append("  Дата основания: ").append(city.getCreationDate()).append("\n")
          .append("  Площадь: ").append(city.getArea()).append("\n")
          .append("  Численность населения: ").append(city.getPopulation()).append("\n")
          .append("  Число метров над уровнем моря: ").append(city.getMetersAboveSeaLevel()).append("\n")
          .append("  Плотность населения: ").append(city.getPopulationDensity()).append("\n")
          .append("  Численность агломерации: ").append(city.getAgglomeration()).append("\n")
          .append("  Тип правления: ").append(city.getGovernment()).append("\n")
          .append("  Рост губернатора: ").append(city.getGovernor().getHeight()).append("\n")
          .append("  Дата рождения губернатора: ").append(city.getGovernor().getBirthday());
        return sb.toString();
    }

    public String formatCityList(List<City> cities) {
        StringBuilder sb = new StringBuilder();
        for (City city : cities) {
            sb.append(formatCity(city)).append("\n\n");
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

    public String formatGovernment(Government government) {
        return government.toString();
    }

    public String formatGovernmentList(List<Government> governments) {
        StringBuilder sb = new StringBuilder();
        for (Government government : governments) {
            sb.append(formatGovernment(government)).append("\n");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    public String formatCollectionWithInfoFields(
            Class<?> collectionType,
            Date initializationDate,
            Class<?> elementsType,
            int countOfElements) {
        StringBuilder sb = new StringBuilder();
        sb.append("Информация о коллекции:\n")
                .append(String.format(
                        "1. Тип коллекции: %s%n",
                        collectionType.getSimpleName()
                        )
                )
                .append(String.format(
                        "2. Дата инициализации: %s%n",
                        initializationDate
                        )
                )
                .append(String.format(
                        "3. Тип элементов: %s%n",
                        elementsType.getSimpleName()
                        )
                )
                .append(String.format(
                        "4. Количество элементов: %s",
                        countOfElements
                        )
                );
        return sb.toString();
    }

    public String formatCurrentReaderInfo(List<Reader> readers) {
        StringBuilder sb = new StringBuilder();
        sb.append("Активен режим чтения ");
        if (readers.size() == 1) {
            sb.append("терминала");
        } else {
            sb.append(
                    String.format(
                            "файла %s",
                            readers.get(readers.size() - 1).getName()
                    )
            );
        }
        return sb.toString();
    }

}


