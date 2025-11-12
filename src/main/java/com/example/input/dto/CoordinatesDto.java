package com.example.input.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordinatesDto {

    private double x;
    private float y;

    public CoordinatesDto(double x, float y) {
        this.x = x;
        this.y = y;
    }

}
