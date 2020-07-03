package Enums;

import java.io.Serializable;
import java.util.Random;

public enum Country implements Serializable {
    FRANCE(0, "France"),
    INDIA(1, "India"),
    THAILAND(2, "Thailand"),
    NORTH_KOREA(3, "North Korea"),
    JAPAN(4, "Japan"),
    RUSSIA(5, "Russian Federation"),
    AMERICA(6, "USA");

    private int index;
    private String description;

    Country(int index, String description) {
        this.index = index;
        this.description = description;
    }

    public int getIndex() {
        return index;
    }

    public String getDescription() {
        return description;
    }

    public static Country getRandomCountry() {
        return Country.values()[new Random().nextInt(Country.values().length)];
    }

    public static Country getByStr(String description) {
        for (Country entry : Country.values())
            if (entry.description.equals(description))
                return entry;
        return null;
    }

    @Override
    public String toString() {
        return description;
    }
}