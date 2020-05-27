package com.company;

import java.io.Serializable;

public class Location implements Serializable {
    private static final long serialVersionUID = 5L;

    private long x;

    private long y;

    private long z;

    private String name; //Поле не может быть null

    public Location(long x, long y, long z, String name) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location() {
        this(0, 0, 0, "Локация");
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public long getZ() {
        return z;
    }

    public String getName() {
        return name;
    }
}