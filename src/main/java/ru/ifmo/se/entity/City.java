package ru.ifmo.se.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.io.input.dto.json.CityFromJsonDto;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
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

    public City(CityFromJsonDto cityFromJsonDto) {
        this(
                cityFromJsonDto.getId(),
                cityFromJsonDto.getName(),
                new Coordinates(
                        cityFromJsonDto.getCoordinates().getX(),
                        cityFromJsonDto.getCoordinates().getY()
                ),
                cityFromJsonDto.getCreationDate(),
                cityFromJsonDto.getArea(),
                cityFromJsonDto.getPopulation(),
                cityFromJsonDto.getMetersAboveSeaLevel(),
                cityFromJsonDto.getPopulationDensity(),
                cityFromJsonDto.getAgglomeration(),
                cityFromJsonDto.getGovernment(),
                new Human(
                        cityFromJsonDto.getGovernor().getHeight(),
                        cityFromJsonDto.getGovernor().getBirthday()
                )
        );
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
