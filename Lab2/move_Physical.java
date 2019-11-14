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

class move_Spark extends move_Physical {
    public move_Spark(Type type, double power, double accuracity, String moveName) {
        super(type, power, accuracity, moveName);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        if (0.3 > Math.random())
            Effect.paralyze(pokemon);
    }
}

class move_Facade extends move_Physical {
    public move_Facade(Type type, double power, double accuracity, String moveName) {
        super(type, power, accuracity, moveName);
    }

    @Override
    protected double calcBaseDamage(Pokemon pokemon, Pokemon pokemon1) {
        Status cond = pokemon1.getCondition();
        if ((cond == Status.BURN) | (cond == Status.POISON) | (cond == Status.PARALYZE))
            return (0.4D * (double) pokemon.getLevel() + 2.0D) * this.power * 2 / 150.0D;
        else
            return super.calcBaseDamage(pokemon, pokemon1);
    }
}

class move_Bulldoze extends move_Physical {
    public move_Bulldoze(Type type, double power, double accuracity, String moveName) {
        super(type, power, accuracity, moveName);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.SPEED, -1);
    }
}

class move_RockSlide extends move_Physical {
    public move_RockSlide(Type type, double power, double accuracity, String moveName) {
        super(type, power, accuracity, moveName);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        if (0.3 > Math.random())
            Effect.flinch(pokemon);
    }
}

class move_ShadowClaw extends move_Physical {
    public move_ShadowClaw(Type type, double power, double accuracity, String moveName) {
        super(type, power, accuracity, moveName);
    }

    @Override
    protected double calcCriticalHit(Pokemon pokemon, Pokemon pokemon1) {
        if (pokemon.getStat(Stat.SPEED) / (512.0D * 2) > Math.random()) {
            System.out.println(pokemon + " наносит критический удар");
            return 2.0D;
        } else {
            return 1.0D;
        }
    }
}