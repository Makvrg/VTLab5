package com.example.entity;

import java.util.Objects;

public class Coordinates {

    private double x;
    private float y;

    public Coordinates(double x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
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
