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

    @SneakyThrows
    public City typifyCityFromJsonDtoToCity(CityFromJsonDto cityFromJsonDto) {
        SimpleDateFormat sdfCreationDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        sdfCreationDate.setLenient(false);
        Date creationDate = sdfCreationDate.parse(cityFromJsonDto.getCreationDate());

        Date birthday = null;
        if (cityFromJsonDto.getGovernor().getBirthday() != null) {
            SimpleDateFormat sdfBirth = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            sdfBirth.setLenient(false);
            birthday = sdfBirth.parse(cityFromJsonDto.getGovernor()
                                                .getBirthday());
        }
        return new City(
                Long.valueOf(cityFromJsonDto.getId()),
                cityFromJsonDto.getName(),
                new Coordinates(
                        Double.parseDouble(cityFromJsonDto.getCoordinates()
                                .getX()),
                        Float.parseFloat(cityFromJsonDto.getCoordinates()
                                .getY())
                ),
                creationDate,
                Long.valueOf(cityFromJsonDto.getArea()),
                Integer.valueOf(cityFromJsonDto.getPopulation()),
                (cityFromJsonDto.getMetersAboveSeaLevel() == null)
                        ? null
                        : Float.valueOf(cityFromJsonDto.getMetersAboveSeaLevel()),
                Long.parseLong(cityFromJsonDto.getPopulationDensity()),
                (cityFromJsonDto.getAgglomeration() == null)
                        ? null
                        : Integer.valueOf(cityFromJsonDto.getAgglomeration()),
                Government.fromString(cityFromJsonDto.getGovernment()),
                new Human(
                        Double.parseDouble(cityFromJsonDto.getGovernor()
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
