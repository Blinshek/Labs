package com.company;

import ru.ifmo.se.pokemon.*;

import static ru.ifmo.se.pokemon.Type.*;

public class Main {

    public static void main(String[] args) {
        //Moves-------------------------------------------------------
        move_Spark Spark = new move_Spark(ELECTRIC, 65, 100, "Spark");
        move_Facade Facade = new move_Facade(NORMAL, 70, 100, "Facade");
        move_Bulldoze Bulldoze = new move_Bulldoze(GROUND, 60, 100,  "Bulldoze");
        move_RockSlide RockSlide = new move_RockSlide(ROCK, 75, 90,  "Rock Slide");
        move_ShadowClaw ShadowClaw = new move_ShadowClaw(GHOST, 70, 100,  "Shadow Claw");

        move_Leer Leer = new move_Leer(NORMAL, 0, 100, "Leer");
        move_WorkUp WorkUp = new move_WorkUp(NORMAL, 0, 100, "Work Up");
        move_Agility Agility = new move_Agility(PSYCHIC, 0, 100,"Agility");
        move_Confide Confide = new move_Confide(NORMAL, 0, 100,"Confide");

        move_IceBeam IceBeam = new move_IceBeam(ICE, 90, 100, "Ice Beam");
        move_Thunder Thunder = new move_Thunder(ELECTRIC, 110, 70,"Thunder");

        Move[] moves = {Spark, Facade, Bulldoze, RockSlide, ShadowClaw, Leer, WorkUp, Agility, Confide, IceBeam, Thunder};
        //------------------------------------------------------------

        //Pokemons----------------------------------------------------
        pok_Giratina pGir = new pok_Giratina("Gira", 30, moves);
        pok_Carvanha pCar = new pok_Carvanha("Carva", 20, moves);
        pok_Sharpedo pShar = new pok_Sharpedo("Sharp", 30, moves);
        pok_Porygon pPor = new pok_Porygon("Por", 20, moves);
        pok_Porygon2 pPor2 = new pok_Porygon2("Por2", 30, moves);
        pok_PorygonZ pPorZ = new pok_PorygonZ("PorZ", 40, moves);
        //------------------------------------------------------------

        Pokemon[] Ally = {pGir};//, pCar, pPor};
        Pokemon[] Foe = {pShar};//, pPor2, pPorZ};
        startBattle(Ally, Foe);
    }

    public static void  startBattle(Pokemon[] Ally, Pokemon[] Foe) {
        Battle b = new Battle();
        for(Pokemon pok: Ally)
            b.addAlly(pok);
        for(Pokemon pok: Foe)
            b.addFoe(pok);
        b.go();
    }

    public static void colorList () {
        int code;
        Color clr = new Color();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                code = i * 16 + j;
                //System.out.printf("%4s%n", "\u001b[38;5;" + code + "m" + code + clr.RESET);
                System.out.print("\u001b[38;5;" + code + "m" + code + "   " + clr.RESET);
            }
            System.out.println();
        }
    }
}

//Moves's classes---------------------------------------------------------
class move_Spark extends move_Physical {
    public move_Spark(Type type, double power, double accuracity, String moveName) {
        super(type, power, accuracity, moveName);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        if(0.3 > Math.random())
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
            return (0.4D * (double)pokemon.getLevel() + 2.0D) * this.power * 2 / 150.0D;
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
        if(0.3 > Math.random())
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


class move_IceBeam extends move_Special {
    public move_IceBeam(Type type, double power, double accuracity, String moveName) {
        super(type, power, accuracity, moveName);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        if(0.1 > Math.random())
            Effect.freeze(pokemon);
    }
}

class move_Thunder extends move_Special {
    public move_Thunder(Type type, double power, double accuracity, String moveName) {
        super(type, power, accuracity, moveName);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        if(0.3 > Math.random())
            Effect.paralyze(pokemon);
    }
}
//------------------------------------------------------------------------

//Pokemons classes--------------------------------------------------------
//Гиратина
class pok_Giratina extends Pokemon {
    public pok_Giratina(String Name, int Level, Move[] moves) {
        super(Name, Level);
        setType(GHOST, DRAGON);
        setMove(moves[0], moves[5], moves[10], moves[8]); //оригинальный
        setStats(150, 100, 120, 100, 120, 90);
    }
}

//Карванха
class pok_Carvanha extends Pokemon {
    public pok_Carvanha(String Name, int Level, Move[] moves) {
        super(Name, Level);
        setType(WATER, DARK);
        setMove(moves[9], moves[4], moves[6]);
        setStats(45, 90, 20, 65, 20, 65);
    }
}

//Шарпедо
class pok_Sharpedo extends pok_Carvanha {
    public pok_Sharpedo(String name, int level, Move[] moves) {
        super(name, level, moves);
        addMove(moves[2]);
        setStats(70, 120, 40, 95, 40, 95);
    }
}

//Поригон
class pok_Porygon extends Pokemon {
    public pok_Porygon(String Name, int Level, Move[] moves) {
        super(Name, Level);
        setMove(moves[1], moves[8]);
        setStats(65, 60, 70, 85, 75, 40);
    }
}

//Поригон2
class pok_Porygon2 extends pok_Porygon {
    public pok_Porygon2(String Name, int Level, Move[] moves) {
        super(Name, Level, moves);
        addMove(moves[7]);
        setStats(85, 80, 90, 105, 95, 60);
    }
}

//Поригон-Z
class pok_PorygonZ extends pok_Porygon2 {
    public pok_PorygonZ(String Name, int Level, Move[] moves) {
        super(Name, Level, moves);
        addMove(moves[3]);
        setStats(85, 80, 70, 135, 75, 90);
    }
}
//------------------------------------------------------------------------