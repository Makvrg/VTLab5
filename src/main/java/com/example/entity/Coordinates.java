package com.example.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Coordinates {

    private double x;
    private float y;

    public Coordinates(double x, float y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(Coordinates other) {
        this(
                other.x,
                other.y
        );
    }


    @Override
    public String toString() {
        return String.format("Coordinates{x=%f, y=%f}", x, y);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Coordinates that)) {
            return false;
        }
        return Double.compare(x, that.x) == 0
               && Float.compare(y, that.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
