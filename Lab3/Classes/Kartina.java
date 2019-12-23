package com.company.Classes;

import com.company.AbstractClasses.*;

class Kartina extends CraftableItem {
    private String title;

    Kartina(String title) {
        super("");
        this.title = title;
        setId(formId());
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    protected int formId() {
        return title.hashCode();
    }
}