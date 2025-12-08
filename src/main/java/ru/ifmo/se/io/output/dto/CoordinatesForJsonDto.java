package ru.ifmo.se.io.output.dto;

import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.entity.Coordinates;

@Getter
@Setter
public class CoordinatesForJsonDto {

    private double x;
    private float y;

    public CoordinatesForJsonDto(Coordinates coordinates) {
        x = coordinates.getX();
        y = coordinates.getY();
    }

}
