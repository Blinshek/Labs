package com.company.Enums;

public enum Sex {
    MALE("Муж"), FEMALE("Жен"), NOT_STATED("Не указан"), UNKNOWN("Не известен");

    private String text;

    Sex(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}