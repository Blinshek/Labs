package com.company;

import javax.xml.bind.annotation.*;

import java.io.Serializable;
import java.util.LinkedHashMap;

@XmlRootElement(name = "Movies")
@XmlAccessorType(XmlAccessType.FIELD)
public class MovieMap implements Serializable {
    private static final long serialVersionUID = 6L;

    private LinkedHashMap<Integer, Movie> movieMap = new LinkedHashMap<>();

    public LinkedHashMap<Integer, Movie> getMovieMap() {
        return movieMap;
    }

    public void setMovieMap(LinkedHashMap<Integer, Movie> movieMap) {
        this.movieMap = movieMap;
    }
}