package com.example.input.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HumanTypedRequestDto {

    private double height;
    private Date birthday;

    public HumanTypedRequestDto(double height, Date birthday) {
        this.height = height;
        this.birthday = birthday;
    }

}
