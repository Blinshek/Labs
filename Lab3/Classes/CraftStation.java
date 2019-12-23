package com.company.Classes;

import com.company.AbstractClasses.Item;

public class CraftStation extends Item {
    private String title;
    private double timeModifier;

    public CraftStation(String title, double timeModifier){
        this.title = title;
        this.timeModifier = timeModifier;
    }

    public double getTimeModifier() {
        return timeModifier;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    protected int formId() {
        return (int)(title.hashCode() * timeModifier);
    }
}