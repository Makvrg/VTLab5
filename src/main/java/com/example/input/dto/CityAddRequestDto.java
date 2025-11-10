package com.example.input.dto;

import java.util.Date;

public class CityAddRequestDto {

    private Long id;
    private String name;
    private CoordinatesRequestDto coordinatesRequestDto;
    private Date creationDate;
    private String area;
    private String population;
    private String metersAboveSeaLevel;
    private String populationDensity;
    private String agglomeration;
    private String government;
    private HumanRequestDto governorRequestDto;


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CoordinatesRequestDto getCoordinates() {
        return coordinatesRequestDto;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getArea() {
        return area;
    }

    public String getPopulation() {
        return population;
    }

    public String getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public String getPopulationDensity() {
        return populationDensity;
    }

    public String getAgglomeration() {
        return agglomeration;
    }

    public String getGovernment() {
        return government;
    }

    public HumanRequestDto getGovernor() {
        return governorRequestDto;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(CoordinatesRequestDto coordinatesRequestDto) {
        this.coordinatesRequestDto = coordinatesRequestDto;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public void setMetersAboveSeaLevel(String metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public void setPopulationDensity(String populationDensity) {
        this.populationDensity = populationDensity;
    }

    public void setAgglomeration(String agglomeration) {
        this.agglomeration = agglomeration;
    }

    public void setGovernment(String government) {
        this.government = government;
    }

    public void setGovernor(HumanRequestDto governorRequestDto) {
        this.governorRequestDto = governorRequestDto;
    }

}
