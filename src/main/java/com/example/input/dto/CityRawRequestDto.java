package com.example.input.dto;

import com.example.input.dto.json.CityFromJsonDto;
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

    public CityRawRequestDto() {}

    public CityRawRequestDto(CityFromJsonDto cityFromJsonDto) {
        name = cityFromJsonDto.getName();

        coordinates = new CoordRawRequestDto();
        coordinates.setX(cityFromJsonDto.getCoordinates().getX());
        coordinates.setY(cityFromJsonDto.getCoordinates().getY());

        area = cityFromJsonDto.getArea();
        population = cityFromJsonDto.getPopulation();
        metersAboveSeaLevel = cityFromJsonDto.getMetersAboveSeaLevel();
        populationDensity = cityFromJsonDto.getPopulationDensity();
        agglomeration = cityFromJsonDto.getAgglomeration();
        government = cityFromJsonDto.getGovernment();

        governor = new HumanRawRequestDto();
        governor.setHeight(cityFromJsonDto.getGovernor().getHeight());
        governor.setBirthday(cityFromJsonDto.getGovernor().getBirthday());
    }

}
