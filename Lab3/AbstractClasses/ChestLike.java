package com.company.AbstractClasses;

import com.company.Classes.*;

public abstract class ChestLike extends Item  {
    protected Inventory inv;

    private int stealProofLvl;

    public ChestLike(int capacity) {
        this(capacity, 0);
    }

    public ChestLike(int capacity, int stealProofLvl) {
        super();
        this.stealProofLvl = stealProofLvl;
        inv = new Inventory(capacity);
    }

    public int getStealProofLvl() {
        return stealProofLvl;
    }

    public int getCapacity(){
        return inv.getLength();
    }

    public Item getItem(int itemIndex) {
        return inv.getItem(itemIndex);
    }

    public int getItemCount(int itemIndex) {
        return inv.getItemCount(itemIndex);
    }

    public int getItemCount(Item item) {
        return inv.getItemCount(item);
    }

    //Вывод инвенторя
    public void printInventory() {
        inv.print();
    }

    public String addItem(Item takenObject){
        inv.addItem(takenObject);
        return "";
    }

    public String addItem(Item takenObject, int count) {
        inv.addItem(takenObject, count);
        return "";
    }

    public void removeItem(Item itemThatMustBeKilled, int count) {
        inv.removeItem(itemThatMustBeKilled, count);
    }

    public void removeItem(int itemThatMustBeKilledInd, int count) {
        inv.removeItem(itemThatMustBeKilledInd, count);
    }
}