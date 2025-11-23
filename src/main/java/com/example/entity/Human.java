package com.example.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class Human {

    private double height;
    private Date birthday;

    public Human(double height, Date birthday) {
        this.height = height;
        this.birthday = birthday;
    }

    public Human(Human other) {
        this(
                other.height,
                (other.birthday != null)
                        ? new Date(other.birthday.getTime())
                        : null
        );
    }


    @Override
    public String toString() {
        return String.format("Human{height=%f, birthday=%s}",
                             height, birthday);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Human human)) {
            return false;
        }
        return Double.compare(height, human.height) == 0
               && Objects.equals(birthday, human.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, birthday);
    }

}
