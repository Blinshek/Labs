package com.company.Classes;

import com.company.Enums.*;
import com.company.AbstractClasses.*;

class BedSheet extends CraftableItem {
    final String material;
    private Enum condition;

    public String getCondition() {
        return this.condition.toString();
    }

    BedSheet(){
        this("", ItemCondition.NEW);
    }

    BedSheet(String material, Enum condition) {
        super("");
        this.material = material;
        this.condition = condition;
    }

    @Override
    protected int formId() {
        return material.hashCode() + condition.hashCode();
    }

    @Override
    public String toString() {
        return condition.toString() + " простыня";
    }
}