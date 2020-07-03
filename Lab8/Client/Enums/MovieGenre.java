package Enums;

import java.io.Serializable;
import java.util.Random;

public enum MovieGenre implements Serializable {
    WESTERN(0, "Western"),
    DRAMA(1, "Drama"),
    HORROR(2, "Horror"),
    FANTASY(3, "Fantasy"),
    SCIENCE_FICTION(4, "Science fiction");

    private int index;
    private String description;

    MovieGenre(int index, String description) {
        this.index = index;
        this.description = description;
    }

    public int getIndex() {
        return index;
    }

    public String getDescription() {
        return description;
    }

    public static MovieGenre getRandomGenre() {
        return MovieGenre.values()[new Random().nextInt(MovieGenre.values().length)];
    }

    public static MovieGenre getByStr(String description) {
        for (MovieGenre entry : MovieGenre.values())
            if (entry.description.equals(description))
                return entry;
        return null;
    }

    @Override
    public String toString() {
        return description;
    }
}