package com.company;

import Enums.MovieGenre;
import Enums.MpaaRating;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Random;
import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Movie implements Comparable<Movie>, Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @XmlElement
    private String title; //Поле не может быть null, Строка не может быть пустой
    @XmlElement
    private Coordinates coordinates; //Поле не может быть null

    //@XmlElement
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @XmlElement(name = "creationDate")
    private String date;

    @XmlElement
    private Integer oscarsCount; //Значение поля должно быть больше 0, Поле не может быть null
    @XmlElement
    private float usaBoxOffice; //Значение поля должно быть больше 0
    @XmlElement
    private MovieGenre genre; //Поле может быть null
    @XmlElement
    private MpaaRating mpaaRating; //Поле может быть null
    @XmlElement
    private Person screenWriter;

    private static long freeID = 0;

    public Movie() {
        this("Unnamed", new Coordinates(), 1, 1F, null, null, new Person());
    }

    public Movie(String title, Coordinates coordinates, Integer oscarsCount, float usaBoxOffice, MovieGenre genre, MpaaRating mpaaRating, Person screenWriter) {
        this.title = title;
        this.coordinates = coordinates;
        this.oscarsCount = oscarsCount;
        this.usaBoxOffice = usaBoxOffice;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.screenWriter = screenWriter;
        this.id = freeID++;
        this.creationDate = rndLocalDate();
        this.date = creationDate.toString();
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
    public void updateID(){
        this.id = freeID++;
    }
    public static void setFreeID(long newFreeID){
        if(newFreeID > 0)
            freeID = newFreeID;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public Float getUsaBoxOffice() {
        return usaBoxOffice;
    }

    @Override
    public int compareTo(Movie o) {
        return (o != null) ? -this.title.compareTo(o.title) : -1;
    }

    @Override
    public String toString() {
        return "Movie{ " +
                "id: " + id +
                "| Title: " + '\'' + title + '\'' +
                "| Creation date: " + creationDate +
                "| Screenwriter: " + screenWriter +
                "| Genre: " + genre +
                "| MPAA Rating: " + mpaaRating +
                "| USA Box Office: " + usaBoxOffice + '$' +
                "| Oscars: " + oscarsCount +
                "| Coordinates: " + coordinates +
                " }";
    }
}