package ru.ifmo.se.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class City implements Comparable<City> {

    private Long id;
    private String name;
    private Coordinates coordinates;
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy HH:mm:ss",
            timezone = "Europe/Moscow"
    )
    private Date creationDate;
    private Long area;
    private Integer population;
    private Float metersAboveSeaLevel;
    private long populationDensity;
    private Integer agglomeration;
    private Government government;
    private Human governor;

    @Getter
    public enum FieldNames {
        ID("id"),
        NAME("name"),
        X("x"),
        Y("y"),
        CREATION_DATE("creationDate"),
        AREA("area"),
        POPULATION("population"),
        METERS_ABOVE_SEA_LEVEL("metersAboveSeaLevel"),
        POPULATION_DENSITY("populationDensity"),
        AGGLOMERATION("agglomeration"),
        GOVERNMENT("government"),
        HEIGHT("height"),
        BIRTHDAY("birthday");

        private final String title;

        FieldNames(String title) {
            this.title = title;
        }
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
