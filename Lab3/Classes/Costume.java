package com.company.Classes;

import com.company.Enums.ItemCondition;
import com.company.Enums.Size;
import com.company.AbstractClasses.*;

class Costume extends CraftableItem {
    //final int neededSewLvl;
    final Enum size;
    final String title;
    private Enum condition;

    public Costume(){
        this("", Size.MEDIUM, ItemCondition.NEW);
    }

    public Costume (String title, Enum size, Enum condition) {
        super( "");
        this.title = title;
        //this.neededSewLvl = neededSewLvl;
        this.size = size;
        this.condition = condition;
        setId(formId());
    }

    public String getCondition() {
        return condition.toString();
    }

    @Override
    protected int formId() {
        return title.hashCode() + size.hashCode() + condition.hashCode();
    }

    @Override
    public String toString() {
        return title;
    }
}