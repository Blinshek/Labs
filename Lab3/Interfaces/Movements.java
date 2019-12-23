package com.company.Interfaces;

import com.company.AbstractClasses.Place;

public interface Movements {
    String sit(Object to);

    void standUp(int power, String from);

    void run(String to);

    String goTo(Place location);
}
