package com.company.Interfaces;

import com.company.AbstractClasses.MyCharacter;
import com.company.AbstractClasses.Place;

public interface FlyAble {
    void fly();

    String fly(Place to);

    String fly(Place to, MyCharacter with);
}