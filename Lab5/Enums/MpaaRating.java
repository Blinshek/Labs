package Enums;

import java.util.Random;

public enum MpaaRating {
    G(0),
    PG(1),
    PG_13(2),
    NC_17(3),
    R(4);

    private int index;
    private MpaaRating(int index){
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static MpaaRating getRandomMpaaRating(){
        return MpaaRating.values()[new Random().nextInt(MpaaRating.values().length)];
    }
}