package com.example.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Getter
public class City implements Comparable<City> {

    @Setter(AccessLevel.NONE)
    private Long id;

    @Setter
    private String name;

    @Setter
    private Coordinates coordinates;

    @Setter(AccessLevel.NONE)
    private Date creationDate;

    @Setter
    private Long area;

    @Setter
    private Integer population;

    @Setter
    private Float metersAboveSeaLevel;

    @Setter
    private long populationDensity;

    @Setter
    private Integer agglomeration;

    @Setter
    private Government government;

    @Setter
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


    @Override
    public int compareTo(City city) {
        Long thisDigit = area * population
                * ((agglomeration != null) ? agglomeration : 1);
        Long cityDigit = city.area * city.population
                * ((city.agglomeration != null) ? city.agglomeration : 1);
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
