package com.example.output.dto;

import com.example.entity.City;
import com.example.entity.Government;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CityForJsonDto {

    private Long id;
    private String name;
    private CoordinatesForJsonDto coordinates;
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
