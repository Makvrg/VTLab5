package ru.ifmo.se.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Coordinates {

    private double x;
    private float y;

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
