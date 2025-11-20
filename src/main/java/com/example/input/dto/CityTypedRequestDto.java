package com.example.input.dto;

import com.example.entity.Government;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityTypedRequestDto {

    private String name;
    private CoordTypedRequestDto coordinates;
    private Long area;
    private Integer population;
    private Float metersAboveSeaLevel;
    private long populationDensity;
    private Integer agglomeration;
    private Government government;
    private HumanTypedRequestDto governor;

    public CityTypedRequestDto(String name,
                               CoordTypedRequestDto coordinates,
                               Long area,
                               Integer population,
                               Float metersAboveSeaLevel,
                               long populationDensity,
                               Integer agglomeration,
                               Government government,
                               HumanTypedRequestDto governor) {
        this.name = name;
        this.coordinates = coordinates;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.populationDensity = populationDensity;
        this.agglomeration = agglomeration;
        this.government = government;
        this.governor = governor;
    }
}
