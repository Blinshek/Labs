package com.company;
import Enums.Country;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Person implements Serializable {
    private static final long serialVersionUID = 0L;

    private String name; //Поле не может быть null, Строка не может быть пустой

    private LocalDate birthday; //Поле не может быть null

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

    public LocalDate getBirthday() {
        return birthday;
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

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", nationality=" + nationality +
                ", location=" + location +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
                Objects.equals(birthday, person.birthday) &&
                nationality == person.nationality &&
                Objects.equals(location, person.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday, nationality, location);
    }
}