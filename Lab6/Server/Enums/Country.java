package Enums;

import java.io.Serializable;
import java.util.Random;

public enum Country implements Serializable {
    FRANCE(0),
    INDIA(1),
    THAILAND(2),
    NORTH_KOREA(3),
    JAPAN(4),
    RUSSIA(5),
    AMERICA(6);

    private int index;
    private Country(int index){
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static Country getRandomCountry(){
        return Country.values()[new Random().nextInt(Country.values().length)];
    }
}