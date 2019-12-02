package com.company;

public enum Condition {
    NEWEST("Новейшая"), NEW("Новая"), ORDINARY("Обычная"), SEMIUSED("Немного использованная"), USED("Использованная"), OLD("Старая");

    private String text;

    Condition(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}