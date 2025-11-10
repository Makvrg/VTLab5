package com.example.entity;

import java.util.Date;
import java.util.Objects;

public class City implements Comparable<City> {

    private Long id;
    private String name;
    private Coordinates coordinates;
    private Date creationDate;
    private Long area;
    private Integer population;
    private Float metersAboveSeaLevel;
    private long populationDensity;
    private Integer agglomeration;
    private Government government;
    private Human governor;

    public City(Long id, String name, Coordinates coordinates,
                Date creationDate, Long area, Integer population,
                Float metersAboveSeaLevel, long populationDensity,
                Integer agglomeration, Government government,
                Human governor) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.populationDensity = populationDensity;
        this.agglomeration = agglomeration;
        this.government = government;
        this.governor = governor;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Long getArea() {
        return area;
    }

    public Integer getPopulation() {
        return population;
    }

    public Float getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public long getPopulationDensity() {
        return populationDensity;
    }

    public Integer getAgglomeration() {
        return agglomeration;
    }

    public Government getGovernment() {
        return government;
    }

    public Human getGovernor() {
        return governor;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public void setMetersAboveSeaLevel(Float metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public void setPopulationDensity(long populationDensity) {
        this.populationDensity = populationDensity;
    }

    public void setAgglomeration(Integer agglomeration) {
        this.agglomeration = agglomeration;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public void setGovernor(Human governor) {
        this.governor = governor;
    }


    @Override
    public int compareTo(City city) {
        Long thisDigit = area * population * agglomeration;
        Long cityDigit = city.area * city.population * city.agglomeration;
        return thisDigit.compareTo(cityDigit);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("City{")
          .append("id=").append(id)
          .append(", name='").append(name).append('\'')
          .append(", coordinates=").append(coordinates)
          .append(", creationDate=").append(creationDate)
          .append(", area=").append(area)
          .append(", population=").append(population)
          .append(", metersAboveSeaLevel=").append(metersAboveSeaLevel)
          .append(", populationDensity=").append(populationDensity)
          .append(", agglomeration=").append(agglomeration)
          .append(", government=").append(government)
          .append(", governor=").append(governor)
          .append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof City city)) {
            return false;
        }
        return populationDensity == city.populationDensity
               && Objects.equals(name, city.name)
               && Objects.equals(coordinates, city.coordinates)
               && Objects.equals(creationDate, city.creationDate)
               && Objects.equals(area, city.area)
               && Objects.equals(population, city.population)
               && Objects.equals(metersAboveSeaLevel, city.metersAboveSeaLevel)
               && Objects.equals(agglomeration, city.agglomeration)
               && government == city.government
               && Objects.equals(governor, city.governor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, creationDate,
                            area, population, metersAboveSeaLevel,
                            populationDensity, agglomeration,
                            government, governor);
    }
}
