package com.company;

import ru.ifmo.se.pokemon.Type;

public class Color {
    protected static final String RESET = ("\033[0m");

    // Pokemon types colors
    protected static final String tNORMAL = ("\u001b[38;5;255m");
    //protected static final String tNORMAL = ("\u001b[38;5;16m");
    protected static final String tFIRE = ("\u001b[38;5;202m");
    protected static final String tWATER = ("\u001b[38;5;27m");
    protected static final String tELECTRIC = ("\u001b[38;5;11m");
    protected static final String tGRASS = ("\u001b[38;5;47m");
    protected static final String tICE = ("\u001b[38;5;45m");
    protected static final String tFIGHTING = ("\u001b[38;5;124m");
    protected static final String tPOISON = ("\u001b[38;5;127m");
    protected static final String tGROUND = ("\u001b[38;5;3m");
    protected static final String tFLYING = ("\u001b[38;5;69m");
    protected static final String tPSYCHIC = ("\u001b[38;5;204m");
    protected static final String tBUG = ("\u001b[38;5;106m");
    protected static final String tROCK = ("\u001b[38;5;3m");
    protected static final String tGHOST = ("\u001b[38;5;98m");
    protected static final String tDRAGON = ("\u001b[38;5;105m");
    protected static final String tDARK = ("\u001b[38;5;94m");
    protected static final String tSTEEL = ("\u001b[38;5;250m");
    protected static final String tFAIRY = ("\u001b[38;5;212m");

    public Color() {
    }

    public static String Colorize(String moveName, Type type) {
        String moveDescription = "";
        switch (type) {
            case NORMAL:
                moveDescription = tNORMAL + moveName + RESET;
                break;
            case FIRE:
                moveDescription = tFIRE + moveName + RESET;
                break;
            case WATER:
                moveDescription = tWATER + moveName + RESET;
                break;
            case ELECTRIC:
                moveDescription = tELECTRIC + moveName + RESET;
                break;
            case GRASS:
                moveDescription = tGRASS + moveName + RESET;
                break;
            case ICE:
                moveDescription = tICE + moveName + RESET;
                break;
            case FIGHTING:
                moveDescription = tFIGHTING + moveName + RESET;
                break;
            case POISON:
                moveDescription = tPOISON + moveName + RESET;
                break;
            case GROUND:
                moveDescription = tGROUND + moveName + RESET;
                break;
            case FLYING:
                moveDescription = tFLYING + moveName + RESET;
                break;
            case PSYCHIC:
                moveDescription = tPSYCHIC + moveName + RESET;
                break;
            case BUG:
                moveDescription = tBUG + moveName + RESET;
                break;
            case ROCK:
                moveDescription = tROCK + moveName + RESET;
                break;
            case GHOST:
                moveDescription = tGHOST + moveName + RESET;
                break;
            case DRAGON:
                moveDescription = tDRAGON + moveName + RESET;
                break;
            case DARK:
                moveDescription = tDARK + moveName + RESET;
                break;
            case STEEL:
                moveDescription = tSTEEL + moveName + RESET;
                break;
            case FAIRY:
                moveDescription = tFAIRY + moveName + RESET;
                break;
        }
        return moveDescription;
    }
}
