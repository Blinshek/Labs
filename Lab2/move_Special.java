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

class move_IceBeam extends move_Special {
    public move_IceBeam(Type type, double power, double accuracity, String moveName) {
        super(type, power, accuracity, moveName);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        if (0.1 > Math.random())
            Effect.freeze(pokemon);
    }
}

class move_Thunder extends move_Special {
    public move_Thunder(Type type, double power, double accuracity, String moveName) {
        super(type, power, accuracity, moveName);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        if (0.3 > Math.random())
            Effect.paralyze(pokemon);
    }
}