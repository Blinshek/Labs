package com.company;

class Costume extends CraftableItem{
    final int neededSewLvl;
    final Enum size;
    final String title;
    private Enum condition;

    public String getCondition() {
        return condition.toString();
    }

    public Costume (String title, int neededSewLvl,  Enum size, Enum condition) {
        super(neededSewLvl, size.hashCode() + condition.hashCode());
        this.title = title;
        this.neededSewLvl = neededSewLvl;
        this.size = size;
        this.condition = condition;
    }

    @Override
    public String toString() {
        return title;
    }
}