package com.company.AbstractClasses;

import com.company.Classes.*;

public abstract class CraftableItem extends Item {
    //public final int neededCraftLvl;
    public final String attribute;
    public Recipe recipe;

    /*
    CraftableItem(int neededCraftLvl, int id) {
        this(neededCraftLvl, id, "");
    }
    */

    public CraftableItem(String attribute) {
        super();
        //this.recipe = recipe;
        this.attribute = attribute;
    }

    protected void setRecipe(Recipe recipe){
        this.recipe = recipe;
    }
}