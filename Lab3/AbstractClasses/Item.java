package com.company.AbstractClasses;

public abstract class Item {
    private int id;

    public Item() {
        this.id = -1;
    }

    @Override
    public boolean equals(Object otherObject) {
        Item otherItem = (Item) otherObject;
        if(this.id == otherItem.id)
            return true;
        else
            return false;
    }

    protected void setId(int id){
        this.id = id;
    }

    protected abstract int formId();

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}