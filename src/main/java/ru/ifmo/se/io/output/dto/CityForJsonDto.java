package ru.ifmo.se.io.output.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.entity.City;
import ru.ifmo.se.entity.Government;

import java.util.Date;

@Getter
@Setter
public class CityForJsonDto {

    private Long id;
    private String name;
    private CoordinatesForJsonDto coordinates;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Europe/Moscow")
    private Date creationDate;
    private Long area;
    private Integer population;
    private Float metersAboveSeaLevel;
    private long populationDensity;
    private Integer agglomeration;
    private Government government;
    private HumanForJsonDto governor;

    public CityForJsonDto(City city) {
        id = city.getId();
        name = city.getName();
        coordinates = new CoordinatesForJsonDto(city.getCoordinates());
        creationDate = new Date(city.getCreationDate().getTime());
        area = city.getArea();
        population = city.getPopulation();
        metersAboveSeaLevel = city.getMetersAboveSeaLevel();
        populationDensity = city.getPopulationDensity();
        agglomeration = city.getAgglomeration();
        government = city.getGovernment();
        governor = new HumanForJsonDto(city.getGovernor());
    }

}
