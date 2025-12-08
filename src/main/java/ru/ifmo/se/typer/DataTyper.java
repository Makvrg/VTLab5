package ru.ifmo.se.typer;

import lombok.SneakyThrows;
import ru.ifmo.se.entity.City;
import ru.ifmo.se.entity.Coordinates;
import ru.ifmo.se.entity.Government;
import ru.ifmo.se.entity.Human;
import ru.ifmo.se.io.input.dto.CityRawRequestDto;
import ru.ifmo.se.io.input.dto.ParamRawData;
import ru.ifmo.se.service.ParamTypedData;

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
