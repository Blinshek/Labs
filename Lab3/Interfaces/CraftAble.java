package com.company.Interfaces;

import com.company.Classes.*;

public interface CraftAble {
    default String craft(Recipe recipe) {
        return craft(recipe, new CraftStation("", 1));
    }

    String craft(Recipe recipe, CraftStation on);

    default double calcCraftTime(double craflLvl, int neededCraftLvl) {
        return calcCraftTime(craflLvl, neededCraftLvl, 1);
    }

    double calcCraftTime(double craftLvl, int neededCraftLvl, double timeModifier);
}