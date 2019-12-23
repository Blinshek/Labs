package com.company.Enums;

public enum ItemCondition {
    NEWEST("Новейшая"), NEW("Новая"), ORDINARY("Обычная"), SEMIUSED("Немного использованная"), USED("Использованная"), OLD("Старая");

    private String text;

    ItemCondition(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}