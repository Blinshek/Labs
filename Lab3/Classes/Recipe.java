package com.company.Classes;

import com.company.AbstractClasses.*;

public class Recipe {
    public final CraftableItem craftResult;
    public final RequiredItems[] ingredients;
    public final int neededCraftLvl;

    Recipe(CraftableItem result, int neededCraftLvl, RequiredItems... ingredients) {
        this.craftResult = result;
        this.ingredients = ingredients;
        this.neededCraftLvl = neededCraftLvl;
    }

    public static class RequiredItems {
        Class item;
        int count;

        RequiredItems(Class item, int count) {
            this.item = item;
            this.count = count;
        }

        public int getCount() {
            return this.count;
        }

        public Class getItem() {
            return this.item;
        }
    }
}