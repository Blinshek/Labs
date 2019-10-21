package com.company;

import ru.ifmo.se.pokemon.*;

//Статусные атаки
class move_Status extends StatusMove {
    private String moveName;

    move_Status(Type type, double power, double accuracity, String moveName) {
        super(type, power, accuracity);
        this.moveName = moveName;
    }

    @Override
    protected String describe() {
        String description = "";
        description = "накладывает эффект " + Color.Colorize(moveName, type);
        return description;
    }
}