package com.company.AbstractClasses;

public abstract class LightSource extends Item{
    protected boolean isOn;
    protected String title;
    private int lightLvl;
    private int currentLightLvl;

    public LightSource(String title, int lightLvl) {
        this(title, lightLvl, false);
    }

    public LightSource(String title, int lightLvl, boolean isOn){
        this.title = title;
        this.lightLvl = lightLvl;
        this.isOn = isOn;
        updateCurrentLightLvl();
    }

    public int getCurrentLightLvl() {
        updateCurrentLightLvl();
        return this.currentLightLvl;
    }

    public void switchLight() {
        this.isOn = !this.isOn;
        updateCurrentLightLvl();
    }

    private void updateCurrentLightLvl(){
        this.currentLightLvl = (this.isOn) ? this.lightLvl : 0;
    }
}