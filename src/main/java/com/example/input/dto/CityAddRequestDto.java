package com.example.input.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityAddRequestDto {

    private String name;
    private CoordinatesRequestDto coordinatesRequestDto;
    private String area;
    private String population;
    private String metersAboveSeaLevel;
    private String populationDensity;
    private String agglomeration;
    private String government;
    private HumanRequestDto governorRequestDto;

}
