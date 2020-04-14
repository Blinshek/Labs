package com.company;

import javax.xml.bind.annotation.*;

import java.util.LinkedHashMap;

@XmlType(name = "Movies collection")

@XmlRootElement
public class MoviesLib {
    @XmlElementWrapper(name = "Movies", nillable = true)
    public LinkedHashMap<Integer, Movie> films = new LinkedHashMap<>();
}