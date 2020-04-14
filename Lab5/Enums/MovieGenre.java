package Enums;

import java.util.Random;

public enum MovieGenre {
    WESTERN(0),
    DRAMA(1),
    HORROR(2),
    FANTASY(3),
    SCIENCE_FICTION(4);

    private int index;
    private MovieGenre(int index){
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static MovieGenre getRandomGenre(){
        return MovieGenre.values()[new Random().nextInt(MovieGenre.values().length)];
    }
}