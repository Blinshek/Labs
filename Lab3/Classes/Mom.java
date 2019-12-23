package com.company.Classes;

import com.company.AbstractClasses.MyCharacter;
import com.company.Enums.ItemCondition;
import com.company.Enums.Sex;

public class Mom extends MyCharacter {
    public Mom(){
        super();
    }

    public Mom(String name, int age) {
        super(name, age, Sex.FEMALE);
        this.craftLvl = 10;
    }


    String repairCostume(Costume costume){
        Recipe rep = new Recipe(new BedSheet("linen", ItemCondition.SEMIUSED), 1, new Recipe.RequiredItems(Costume.class, 1));
        return this.craft(rep);
    }
}