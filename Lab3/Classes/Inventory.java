package com.company.Classes;

import com.company.AbstractClasses.*;
import com.company.Exceptions.OutOfItemException;

public class Inventory {
    private final Item[] items;
    private final int[] itemCount;

    public Inventory(int capacity) {
        items = new Item[capacity];
        itemCount = new int[capacity];
    }

    public int getItemCount(Item item) {
        return (getItemIndex(item) != -1) ? getItemCount(getItemIndex(item)) : 0;
    }

    public int getItemCount(int itemIndex) {
        return this.itemCount[itemIndex];
    }

    public Item getItem(int itemIndex) {
        return this.items[itemIndex];
    }

    public int getLength() {
        return this.items.length;
    }

    public int getItemIndex(Item item) {
        for (int i = 0; i < this.items.length; i++)
            if (items[i] != null && items[i].equals(item))
                return i;
        return -1;
    }

    public void addItem(Item takenItem) {
        addItem(takenItem, 1);
    }

    public void addItem(Item takenItem, int count) {
        int freeCell = -1;
        int itemInd = getItemIndex(takenItem);

        if (itemInd != -1)
            itemCount[itemInd] += count;
        else {
            for (int i = 0; i < items.length; i++)
                if (items[i] == null) {
                    freeCell = i;
                    break;
                }
            if (freeCell != -1) {
                items[freeCell] = takenItem;
                itemCount[freeCell] += count;
            }
        }
    }

    public void removeItem(Item item){
        try {
            removeItem(item, 1);
        } catch (OutOfItemException e) {
            System.out.println(e);
        }
    }

    public void removeItem(Item item, int count) throws OutOfItemException {

        if (getItemIndex(item) != -1)
            removeItem(getItemIndex(item), count);
        else
            throw new OutOfItemException();
    }

    public void removeItem(int itemIndex){
        removeItem(itemIndex, 1);
    }

    public void removeItem(int itemIndex, int count) {
        int trueCount = Math.min(count, itemCount[itemIndex]);
        itemCount[itemIndex] -= trueCount;
        if (itemCount[itemIndex] == 0)
            items[itemIndex] = null;
    }

    public void print() {
        int symvcnt = 0;
        int itemsCountLen = 0;

        for (int i = 0; i < items.length; i++)
            if (items[i] != null) {
                if (items[i].toString().length() > symvcnt)
                    symvcnt = items[i].toString().length();
                if (("" + itemCount[i]).length() > itemsCountLen)
                    itemsCountLen = ("" + itemCount[i]).length();
            }
        System.out.println("Инвентарь " + this + ":");
        //Вывод на экран
        boolean InvIsEmpty = true;
        for (int i = 0; i < items.length; i++)
            if ((items[i] != null)) {
                InvIsEmpty = false;
                System.out.println("+" + Main.repeat(symvcnt + 4, "-") + "+" + Main.repeat(itemsCountLen + 2, "-") + "+");
                System.out.println("| " + String.format("%-" + (symvcnt + 3) + "s", items[i].toString()) + "| " + String.format("%-" + (itemsCountLen + 1) + "s", items[i]) + "|");
            }
        if (!InvIsEmpty)
            System.out.println("+" + Main.repeat(symvcnt + 4, "-") + "+" + Main.repeat(itemsCountLen + 2, "-") + "+");
    }
}