package ru.ifmo.se.io.output.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.entity.Human;

import java.util.Date;

@Getter
@Setter
public class HumanForJsonDto {

    private double height;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Europe/Moscow")
    private Date birthday;

    public HumanForJsonDto(Human human) {
        height = human.getHeight();
        birthday = (human.getBirthday() != null)
                        ? new Date(human.getBirthday().getTime())
                        : null;
    }

}
