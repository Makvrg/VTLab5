package com.example.input.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityRawRequestDto {

    private String name;
    private CoordRawRequestDto coordinates;
    private String area;
    private String population;
    private String metersAboveSeaLevel;
    private String populationDensity;
    private String agglomeration;
    private String government;
    private HumanRawRequestDto governor;

}
