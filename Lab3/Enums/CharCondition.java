package com.company.Enums;

public enum CharCondition {
    SIT("сидит"), STAND("стоит"), FLY("в полёте");

    private String text;

    CharCondition(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}