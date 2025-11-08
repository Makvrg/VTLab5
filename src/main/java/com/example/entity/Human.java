package com.example.entity;

import java.util.Date;
import java.util.Objects;

public class Human {

    private double height;
    private Date birthday;

    public void setHeight(double height) {
        this.height = height;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return String.format("Human{height=%f, birthday=%s}",
                             height, birthday.toString());
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
