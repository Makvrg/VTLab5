package com.example.output.dto;

import com.example.entity.Coordinates;
import lombok.Getter;
import lombok.Setter;

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
