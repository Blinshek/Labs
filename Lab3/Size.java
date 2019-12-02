package com.company;

public enum Size {
    EXTRA_SMALL("XS"), SMALL("S"), MEDIUM("M"), LARGE("L"), EXTRA_LARGE("XL");

    private String abbreviation;

    Size(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public String toString() {
        return this.abbreviation;
    }
}