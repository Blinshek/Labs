package com.company;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Coordinates {
    @XmlElement
    private float x; //Поле не может быть null
    @XmlElement
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