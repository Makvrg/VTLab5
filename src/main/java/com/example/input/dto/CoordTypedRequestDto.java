package com.example.input.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordTypedRequestDto {

    private double x;
    private float y;

    public CoordTypedRequestDto(double x, float y) {
        this.x = x;
        this.y = y;
    }

}
