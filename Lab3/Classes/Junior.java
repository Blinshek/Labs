package com.company.Classes;

import com.company.AbstractClasses.*;
import com.company.Enums.*;

class Junior extends MyCharacter {

    private String[] memories = new String[5];
    public final Inventory inv = new Inventory(10);

    Junior() {
        super();
    }

    Junior(String name, int age, Sex sex) {
        super(name, age, sex);
        this.craftLvl = 2d;
    }

    public void addMemory(String mem) {
        int freeInd = -1;
        for (int i = 0; i < memories.length; i++)
            if (memories[i] == null) {
                freeInd = i;
                break;
            }

        if (freeInd == -1) {
            for (int i = 1; i < memories.length; i++)
                memories[i - 1] = memories[i];

            memories[memories.length - 1] = mem;
        } else
            memories[freeInd] = mem;
    }

    public void clearMemories(){
        for(String mem : memories)
            mem = null;
    }

    public String getMemories() {
        String memories = this + " вспоминает, что";
        for (int i = 0; i < memories.length(); i++) {
            String currentMem = this.memories[i];
            if (currentMem == null) break;
            memories += " " + currentMem + ((i == this.memories.length - 1) || (this.memories[i+1] == null) ? "." : ",");
        }
        return memories;
    }
}