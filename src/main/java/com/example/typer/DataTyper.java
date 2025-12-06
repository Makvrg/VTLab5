package com.example.typer;

import com.example.entity.City;
import com.example.entity.Coordinates;
import com.example.entity.Government;
import com.example.entity.Human;
import com.example.input.dto.CityRawRequestDto;
import com.example.input.dto.ParamRawData;
import com.example.input.dto.json.CityFromJsonDto;
import com.example.service.ParamTypedData;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTyper {

    @SneakyThrows
    public City typifyCityRawRequestDtoToCity(CityRawRequestDto cityRawRequestDto) {
        Date birthday = null;
        if (cityRawRequestDto.getGovernor().getBirthday() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            sdf.setLenient(false);
            birthday = sdf.parse(cityRawRequestDto.getGovernor()
                                                  .getBirthday());
        }
        return new City(
                null,
                cityRawRequestDto.getName(),
                new Coordinates(
                        Double.parseDouble(cityRawRequestDto.getCoordinates()
                                .getX()),
                        Float.parseFloat(cityRawRequestDto.getCoordinates()
                                .getY())
                ),
                null,
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
                new Human(
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

}
