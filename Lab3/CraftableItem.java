package com.company;

abstract class CraftableItem extends Item{
    final int neededSewLvl;

    CraftableItem(int neededSewLvl, int id) {
        super(id);
        this.neededSewLvl = neededSewLvl;
    }
}