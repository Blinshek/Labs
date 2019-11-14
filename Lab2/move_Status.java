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
        String description = "накладывает эффект " + Color.Colorize(moveName, type);
        return description;
    }
}

class move_Leer extends move_Status {
    public move_Leer(Type type, double power, double accuracity, String moveName) {
        super(type, power, accuracity, moveName);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.SPEED, -1);
    }
}

class move_WorkUp extends move_Status {
    public move_WorkUp(Type type, double power, double accuracity, String moveName) {
        super(type, power, accuracity, moveName);
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.ATTACK, +1);
        pokemon.setMod(Stat.SPECIAL_ATTACK, +1);
    }
}

class move_Agility extends move_Status {
    public move_Agility(Type type, double power, double accuracity, String moveName) {
        super(type, power, accuracity, moveName);
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.SPEED, +2);
    }
}

class move_Confide extends move_Status {
    public move_Confide(Type type, double power, double accuracity, String moveName) {
        super(type, power, accuracity, moveName);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.SPECIAL_ATTACK, -1);
    }
}