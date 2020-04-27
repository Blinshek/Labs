package com.company;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class Location implements Serializable {
    private static final long serialVersionUID = 5L;
    @XmlElement
    private long x;
    @XmlElement
    private Long y;
    @XmlElement
    private Long z;
    @XmlElement
    private String name; //Поле не может быть null

    public Location(long x, Long y, Long z, String name) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location() {
        this(0, 0L, 0L, "Локация");
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