package com.company;

import ru.ifmo.se.pokemon.Move;
import ru.ifmo.se.pokemon.Pokemon;

import static ru.ifmo.se.pokemon.Type.*;

//Pokemons
class MyPokemon extends Pokemon {
    private final String Name;

    public MyPokemon(String Name, int Level) {
        super(Name, Level);
        this.Name = Name;
    }

    public String getName() {
        return this.Name;
    }
}

//Гиратина
class pok_Giratina extends MyPokemon {
    public pok_Giratina(String Name, int Level, Move[] moves) {
        super(Name, Level);
        setType(GHOST, DRAGON);
        setMove(moves[0], moves[5], moves[10], moves[8]);
        setStats(150, 100, 120, 100, 120, 90);
    }
}

//Карванха
class pok_Carvanha extends MyPokemon {
    public pok_Carvanha(String Name, int Level, Move[] moves) {
        super(Name, Level);
        setType(WATER, DARK);
        setMove(moves[9], moves[4], moves[6]);
        setStats(45, 90, 20, 65, 20, 65);
    }
}

//Шарпедо
class pok_Sharpedo extends pok_Carvanha {
    public pok_Sharpedo(String Name, int level, Move[] moves) {
        super(Name, level, moves);
        addMove(moves[2]);
        setStats(70, 120, 40, 95, 40, 95);
    }
}

//Поригон
class pok_Porygon extends MyPokemon {
    public pok_Porygon(String Name, int Level, Move[] moves) {
        super(Name, Level);
        setType(NORMAL);
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