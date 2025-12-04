package com.example.output.dto;

import com.example.entity.Human;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HumanForJsonDto {

    private double height;
    private Date birthday;

    public HumanForJsonDto(Human human) {
        height = human.getHeight();
        birthday = (human.getBirthday() != null)
                        ? new Date(human.getBirthday().getTime())
                        : null;
    }

}
