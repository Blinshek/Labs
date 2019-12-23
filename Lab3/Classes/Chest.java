package com.company.Classes;

import com.company.AbstractClasses.*;

class Chest extends ChestLike {
    private final String title;

    Chest(String title, int capacity) {
        super(capacity, 10);
        this.title = title;
        setId(formId());
    }

    @Override
    protected int formId() {
        return Math.abs(title.hashCode() + inv.getLength());
    }

    @Override
    public String toString() {
        return title;
    }


}