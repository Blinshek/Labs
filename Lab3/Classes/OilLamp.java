package com.company.Classes;

import com.company.AbstractClasses.*;

class OilLamp extends LightSource {

    OilLamp(String title, int lightLvl){
        super(title, lightLvl);
    }

    OilLamp(String title, int lightLvl, boolean isOn){
        super(title, lightLvl, isOn);
    }

    @Override
    protected int formId() {
        return title.hashCode();
    }

    @Override
    public String toString() {
        if(isOn)
            return "Горящая " + title;
        else
            return "Выключенная " + title;
    }
}