package com.company;

import ru.ifmo.se.pokemon.*;

//Спец атаки
class move_Special extends SpecialMove {
    private String moveName;

    public move_Special(Type type, double power, double accuracity) {
        super(type, power, accuracity);
    }

    public move_Special(Type type, double power, double accuracity, String moveName) {
        super(type, power, accuracity);
        this.moveName = moveName;
    }

    @Override
    protected String describe() {
        String descriptuion = "атакует врага с помощью " + Color.Colorize(moveName, type);
        return descriptuion;
    }
}
