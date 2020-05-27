package com.company;

import Enums.MovieGenre;
import Enums.MpaaRating;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Random;
import javax.xml.bind.annotation.*;

public class Movie implements Comparable<Movie>, Serializable {
    private static final long serialVersionUID = 1L;

    private int id;

    private String title; //Поле не может быть null, Строка не может быть пустой

    private Coordinates coordinates; //Поле не может быть null

    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    private Integer oscarsCount; //Значение поля должно быть больше 0, Поле не может быть null

    private float usaBoxOffice; //Значение поля должно быть больше 0

    private MovieGenre genre; //Поле может быть null

    private MpaaRating mpaaRating; //Поле может быть null

    private Person screenWriter;

    public Movie(int id, String title, LocalDate creationDate, Coordinates coordinates, Integer oscarsCount,
                 float usaBoxOffice, MovieGenre genre, MpaaRating mpaaRating, Person screenWriter) {

        this(id, title, coordinates, oscarsCount, usaBoxOffice, genre, mpaaRating, screenWriter);
        this.creationDate = creationDate;
    }

    public Movie(int id, String title, Coordinates coordinates, Integer oscarsCount,
                 float usaBoxOffice, MovieGenre genre, MpaaRating mpaaRating, Person screenWriter) {

        this.id = id;
        this.title = title;
        this.coordinates = coordinates;
        this.oscarsCount = oscarsCount;
        this.usaBoxOffice = usaBoxOffice;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.screenWriter = screenWriter;
        this.creationDate = rndLocalDate();
    }

    private static LocalDate rndLocalDate() {
        Random rnd = new Random();
        LocalDate ld;
        long days;

        // Get an Epoch value roughly between 1940 and 2010
        // -946771200000L = January 1, 1940
        // Add up to 80 years to it (using modulus on the next long)
        days = Math.abs(rnd.nextLong()) % (50L * 365);

        // Construct a date
        ld = LocalDate.ofEpochDay(days);
        return ld;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return this.id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Person getScreenWriter() {
        return screenWriter;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public Float getUsaBoxOffice() {
        return usaBoxOffice;
    }

    public Integer getOscarsCount() {
        return oscarsCount;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public int compareTo(Movie o) {
        return (o != null) ? -this.title.compareTo(o.title) : -1;
    }

    @Override
    public String toString() {
        return "Movie{ " +
                "id = " + id +
                "| title = '" + title + '\'' +
                "| coordinates = " + coordinates +
                "| creationDate = " + creationDate +
                "| oscarsCount = " + oscarsCount +
                "| usaBoxOffice = " + usaBoxOffice +
                "| genre = " + genre +
                "| mpaaRating = " + mpaaRating +
                "| screenWriter = " + screenWriter +
                '}';
    }
}