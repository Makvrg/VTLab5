package com.example.input.dto;

import com.example.entity.Government;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityAddDto {

    private String name;
    private CoordinatesDto coordinatesDto;
    private Long area;
    private Integer population;
    private Float metersAboveSeaLevel;
    private long populationDensity;
    private Integer agglomeration;
    private Government government;
    private HumanDto governorDto;

    public CityAddDto(String name,
                      CoordinatesDto coordinatesDto,
                      Long area,
                      Integer population,
                      Float metersAboveSeaLevel,
                      long populationDensity,
                      Integer agglomeration,
                      Government government,
                      HumanDto governorDto) {
        this.name = name;
        this.coordinatesDto = coordinatesDto;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.populationDensity = populationDensity;
        this.agglomeration = agglomeration;
        this.government = government;
        this.governorDto = governorDto;
    }
}
