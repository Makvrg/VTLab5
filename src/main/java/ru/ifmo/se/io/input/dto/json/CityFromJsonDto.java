package ru.ifmo.se.io.input.dto.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.entity.Government;

import java.util.Date;

@Getter
@Setter
public class CityFromJsonDto {

    private Long id;
    private String name;
    private CoordFromJsonDto coordinates;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Europe/Moscow")
    private Date creationDate;
    private Long area;
    private Integer population;
    private Float metersAboveSeaLevel;
    private long populationDensity;
    private Integer agglomeration;
    private Government government;
    private HumanFromJsonDto governor;

}
