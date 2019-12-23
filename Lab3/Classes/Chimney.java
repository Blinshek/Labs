package com.company.Classes;

import com.company.AbstractClasses.*;

class Chimney extends LightSource {
    private double temperature;

    Chimney(String title, int lightLvl, int firewoodCount, boolean isOn){
        super(title, lightLvl, isOn);
    }

    private class Firewood {
        String material;
        Firewood(String material) {
            this.material = material;
        }
    }

    @Override
    protected int formId() {
        return title.hashCode();
    }
}