package com.company;
import Enums.Country;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.time.LocalDate;

@XmlRootElement
public class Person implements Serializable {
    private static final long serialVersionUID = 0L;

    @XmlElement
    private String name; //Поле не может быть null, Строка не может быть пустой

    private LocalDate birthday;
    @XmlElement
    private Country nationality; //Поле не может быть null

    @XmlElement
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
    @XmlElement
    public String getBirthday() {
        return (this.birthday == null) ? null : this.birthday.toString();
    }

    @Override
    public String toString() {
        return name;
    }
}