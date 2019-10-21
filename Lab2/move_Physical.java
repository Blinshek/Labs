package com.company;

import ru.ifmo.se.pokemon.*;

public class move_Physical extends PhysicalMove {
    private String moveName;
    private int k = 1;

    public move_Physical(Type type, double power, double accuracity) {
        super(type, power, accuracity);
    }

    public move_Physical(Type type, double power, double accuracity, String moveName) {
        super(type, power, accuracity);
        this.moveName = moveName;
    }

    @Override
    protected String describe() {
        String descriptuion = "атакует врага с помощью " + Color.Colorize(moveName, type);
        return descriptuion;
    }
}