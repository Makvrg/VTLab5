package com.example.typer;

import com.example.entity.City;
import com.example.entity.Coordinates;
import com.example.entity.Government;
import com.example.entity.Human;
import com.example.input.dto.*;
import com.example.service.ParamTypedData;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTyper {

    @SneakyThrows
    public CityTypedRequestDto typifyCityRawRequestDto(
            CityRawRequestDto cityRawRequestDto) {
        Date birthday = null;
        if (cityRawRequestDto.getGovernor().getBirthday() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            sdf.setLenient(false);
            birthday = sdf.parse(cityRawRequestDto.getGovernor()
                          .getBirthday());
        }
        return new CityTypedRequestDto(
                cityRawRequestDto.getName(),
                new CoordTypedRequestDto(
                        Double.parseDouble(cityRawRequestDto.getCoordinates()
                                .getX()),
                        Float.parseFloat(cityRawRequestDto.getCoordinates()
                                .getY())
                ),
                Long.valueOf(cityRawRequestDto.getArea()),
                Integer.valueOf(cityRawRequestDto.getPopulation()),
                (cityRawRequestDto.getMetersAboveSeaLevel() == null)
                        ? null
                        : Float.valueOf(cityRawRequestDto.getMetersAboveSeaLevel()),
                Long.parseLong(cityRawRequestDto.getPopulationDensity()),
                (cityRawRequestDto.getAgglomeration() == null)
                        ? null
                        : Integer.valueOf(cityRawRequestDto.getAgglomeration()),
                Government.fromString(cityRawRequestDto.getGovernment()),
                new HumanTypedRequestDto(
                        Double.parseDouble(cityRawRequestDto.getGovernor()
                                .getHeight()),
                        birthday
                )
        );
    }

    public ParamTypedData typifyParamRawData(ParamRawData paramRawData) {
        ParamTypedData paramTypedData = new ParamTypedData();

        if (paramRawData.getId() != null) {
            paramTypedData.setId(Long.valueOf(paramRawData.getId()));
        }
        return paramTypedData;
    }

    public City typifyCityTypedRequestDtoToCity(CityTypedRequestDto cityTypedRequestDto) {
        return new City(
                null,
                cityTypedRequestDto.getName(),
                new Coordinates(
                        cityTypedRequestDto.getCoordinates()
                                .getX(),
                        cityTypedRequestDto.getCoordinates()
                                .getY()
                ),
                null,
                cityTypedRequestDto.getArea(),
                cityTypedRequestDto.getPopulation(),
                cityTypedRequestDto.getMetersAboveSeaLevel(),
                cityTypedRequestDto.getPopulationDensity(),
                cityTypedRequestDto.getAgglomeration(),
                cityTypedRequestDto.getGovernment(),
                new Human(
                        cityTypedRequestDto.getGovernor()
                                .getHeight(),
                        cityTypedRequestDto.getGovernor()
                                .getBirthday()
                )
        );
    }

}
