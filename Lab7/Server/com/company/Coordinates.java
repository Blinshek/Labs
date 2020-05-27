package com.company;

import java.io.Serializable;


public class Coordinates implements Serializable {
    private static final long serialVersionUID = 4L;

    private float x; //Поле не может быть null

    private float y; //Поле не может быть null

    public Coordinates() {
        this(0, 0);
    }

    public Coordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}