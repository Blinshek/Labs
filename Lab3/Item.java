package com.company;

abstract class Item {
    final int id;

    Item(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object otherObject) {
        Item otherItem = (Item) otherObject;
        if(this.id == otherItem.id)
            return true;
        else
            return false;
    }
}