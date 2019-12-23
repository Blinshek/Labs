package com.company.AbstractClasses;

public abstract class Place {
    private String title;
    private MyCharacter owner;

    Place(String title, MyCharacter owner) {
        this.title = title;
        this.owner = owner;
    }

    public MyCharacter getOwner() {
        return owner;
    }

    public static class Room extends Place {
        private int currentLightLvl;
        public LightSource[] lightSources;

        public Room(String title, MyCharacter owner, LightSource... lightSources) {
            super(title, owner);
            this.lightSources = lightSources;
            updateLightLvl();
        }

        private void updateLightLvl() {
            int lightlvl = 0;
            for (LightSource ls : this.lightSources)
                lightlvl += ls.getCurrentLightLvl();
            this.currentLightLvl = lightlvl;
        }

        public int getCurrentLightLvl() {
            updateLightLvl();
            return currentLightLvl;
        }

        public String getDescription(){
            String str = "";
            if(getCurrentLightLvl() > 4)
                str += "В комнате " + getOwner() + " светло";
            if(getCurrentLightLvl() > 8)
                str += " и уютно";
            return str;
        }

        @Override
        public String toString() {
            return "комната " + this.getOwner();
        }
    }
}