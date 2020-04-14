package com.company;
import Enums.Country;

import javax.xml.bind.annotation.*;
import java.time.LocalDate;

@XmlRootElement
public class Person {
    @XmlElement
    private String name; //Поле не может быть null, Строка не может быть пустой

    private LocalDate birthday;
    @XmlElement
    private Country nationality; //Поле не может быть null

    @XmlElement
    private Location location; //Поле не может быть null

    public Person(){
        this("Clooney", LocalDate.now(), Country.getRandomCountry(), new Location());
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