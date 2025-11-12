package com.example.input.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HumanDto {

    private double height;
    private Date birthday;

    public HumanDto(double height, Date birthday) {
        this.height = height;
        this.birthday = birthday;
    }

}
