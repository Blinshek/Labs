package com.company;
import Enums.Country;

import java.io.Serializable;
import java.time.LocalDate;

public class Person implements Serializable {
    private static final long serialVersionUID = 0L;

    private String name; //Поле не может быть null, Строка не может быть пустой

    private LocalDate birthday;

    private Country nationality; //Поле не может быть null

    private Location location; //Поле не может быть null

    public Person(){
        this("Clooney", null, Country.getRandomCountry(), new Location());
    }

    public Person(String name, LocalDate birthday, Country nationality, Location location){
        this.name = name;
        this.nationality = nationality;
        this.location = location;
        this.birthday = birthday;
    }

    public String getBirthday() {
        return (this.birthday == null) ? null : this.birthday.toString();
    }

    public String getName() {
        return name;
    }

    public Country getNationality() {
        return nationality;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return name;
    }
}