package com.company;

public enum CharacterCondition {
    SIT("сидит"), STAND("стоит"), FLY("в полёте");

    private String text;

    CharacterCondition(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}