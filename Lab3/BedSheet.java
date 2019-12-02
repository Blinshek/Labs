package com.company;

class BedSheet extends Item{
    final String material;
    private Enum condition;

    public String getCondition() {
        return this.condition.toString();
    }

    BedSheet(String material, Enum condition) {
        super(material.hashCode() + condition.hashCode());
        this.material = material;
        this.condition = condition;
    }

    @Override
    public String toString() {
        return condition.toString() + " простыня";
    }
}