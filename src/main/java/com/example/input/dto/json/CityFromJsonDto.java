package com.example.input.dto.json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityFromJsonDto {

    private String name;
    private CoordFromJsonDto coordinates;
    private String area;
    private String population;
    private String metersAboveSeaLevel;
    private String populationDensity;
    private String agglomeration;
    private String government;
    private HumanFromJsonDto governor;

}
