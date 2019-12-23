package com.company.AbstractClasses;

import com.company.Classes.*;
import com.company.Enums.*;
import com.company.Interfaces.*;

import java.util.Scanner;

import static com.company.Classes.Main.*;
import static com.company.Enums.CharCondition.*;

public abstract class MyCharacter implements ActionsWithItems, CraftAble, Movements {
    private String name;
    private int age;
    private Sex sex;
    private CharCondition charCondition;
    private CharEmotions emotion;
    protected double craftLvl;
    private Place location;

    private Inventory inv = new Inventory(10);

    //Constructors------------------------
    public MyCharacter() {
        this("Nameless", 0, Sex.NOT_STATED);
    }

    public MyCharacter(String name, int age, Sex sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
    //------------------------------------

    //Get/set-----------------------------
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    public Place getLocation() {
        return location;
    }

    public Enum getCharCondition() {
        return charCondition;
    }

    public String setEmotion(CharEmotions emotion) {
        String output = this + ((this.emotion == emotion) ? (" is very " + emotion) : (" is " + emotion));
        this.emotion = emotion;
        return output;
    }

    public void setLocation(Place location) {
        this.location = location;
    }

    public CharEmotions getEmotion() {
        return emotion;
    }

    public void setCharCondition(CharCondition charCondition) {
        this.charCondition = charCondition;
    }

    public Inventory getInventory() {
        return inv;
    }
    //------------------------------------

    @Override
    public String take(Item takenItem, int count) {
        inv.addItem(takenItem, count);
        return this + " взял " + takenItem + " x" + count;
    }

    @Override
    public String give(Item item, MyCharacter to, int count) {
        int itemsInInventory = inv.getItemCount(item);
        int tradedItemsCnt = Math.min(count, itemsInInventory);
        to.take(item, tradedItemsCnt);
        inv.removeItem(item, tradedItemsCnt);
        return this + " передал " + to + " " + item + " x" + tradedItemsCnt;
    }

    @Override
    public String sit(Object to) {
        if (to instanceof Item) {
            this.setCharCondition(SIT);
            return this + " садится на " + to;
        } else
            return this + " не может сесть на " + to;
    }

    @Override
    public void run(String to) {
        System.out.println(this + " бежит к " + to);
    }

    @Override
    public void standUp(int power, String from) {
        if (power == 0)
            System.out.println(this + " встал с " + from);
        else
            System.out.println(this + " вскочил с " + from);
        this.setCharCondition(STAND);
    }

    @Override
    public String goTo(Place location) {
        if (location != null) {
            this.location = location;
            return this + " идёт в " + location + ". ";
        } else {
            this.location = null;
            return this + " куда-то уходит. ";
        }
    }

    public void doSomethingUseless(String how, String what) {
        System.out.println(this + " " + how + " " + what);
    }

    public void say(String phrase) {
        say(phrase, 0);
    }

    public void say(String phrase, int delayMillis) {
        try {
            System.out.println(this + ": ");
            System.out.println("\t" + phrase);
            Thread.sleep(delayMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String craft(Recipe recipe, CraftStation on) {
        int itemInd = -1;

        for (int i = 0; i < inv.getLength(); i++)
            if ((inv.getItem(i) != null) && (inv.getItem(i).getClass() == recipe.ingredients[0].getItem()))
                if (inv.getItemCount(i) >= recipe.ingredients[0].getCount()) {
                    itemInd = i;
                    break;
                } else
                    break;

        if (itemInd != -1) {
            double craftTime = calcCraftTime(this.craftLvl, recipe.neededCraftLvl, on.getTimeModifier());
            int craftTimeSec = (int) Math.round(craftTime * 5);
            int delayMillis = craftTimeSec * 10;
            Scanner in = new Scanner(System.in);
            System.out.println("Создание " + recipe.craftResult + " займёт у " + this.getName() + " " + String.format("%.2f", craftTime) + " ч.");
            System.out.println("Приступить к созданию? (y/n)");
            if (in.nextLine().toLowerCase().equals("y")) {
                //PROGRESS BAR MIGHT BE HERE //~~DONE~~
                this.inv.removeItem(itemInd, recipe.ingredients[0].getCount());

                for (int i = 0; i < 100; i++)
                    try {
                        int k = (i + 1) / 5;
                        System.out.print(repeat(30, "" + (char) 8));
                        System.out.print("[" + repeat(k, "#") + repeat(20 - k, " ") + "] " + (i + 1) + "%");
                        Thread.sleep(delayMillis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                System.out.println();

                if (recipe.neededCraftLvl > this.craftLvl) {
                    this.craftLvl *= 1 + (recipe.neededCraftLvl - this.craftLvl) * 0.2;
                    if (this.craftLvl > recipe.neededCraftLvl)
                        this.craftLvl = recipe.neededCraftLvl;
                }

                return this.take(recipe.craftResult);
            }
        } else
            return "Недостаточно материала для крафта";
        return "";
    }

    @Override
    public double calcCraftTime(double craftLvl, int neededCraftLvl, double timeModifier) {
        double time;
        if (craftLvl >= neededCraftLvl)
            time = neededCraftLvl * 0.25;
        else
            time = neededCraftLvl * 0.25 + (neededCraftLvl - craftLvl) * 0.4;
        return time * timeModifier;
    }

    //Object's methods--------------------
    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int hashCode() {
        return (name.hashCode() + sex.hashCode() + age) * 31;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() == obj.getClass())
            return super.hashCode() == obj.hashCode();
        return false;
    }
    //------------------------------------

    //Локальный класс---------------------
    public interface CharStatContainer {
        void printAllStats();
    }

    public CharStatContainer getStatsContainer() {
        class CharacterInventory implements CharStatContainer {
            public final String name = getName();
            public final int age = getAge();
            public final Sex sex = getSex();
            public final Place currentLocation = getLocation();

            @Override
            public void printAllStats() {
                System.out.println("Name: " + name + ", \nAge: " + age + ", \nSex: " + sex + ", \nLocation: " + currentLocation);
            }
        }

        return new CharacterInventory();
    }
    //------------------------------------

}