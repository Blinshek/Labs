package com.company.Classes;

import com.company.AbstractClasses.*;
import com.company.Enums.*;
import com.company.Exceptions.InputException;

import java.util.Scanner;

public class Thief extends MyCharacter {
    private double stealLvl;

    public Thief(double stealLvl) {
        this("Вор", stealLvl);
    }

    public Thief(String name, double stealLvl) {
        super(name, 18, Sex.UNKNOWN);
        this.stealLvl = stealLvl;
    }

    public String steal(ChestLike target) {
        System.out.println(this + " пытается обокрасть " + target);
        int neededLvl = target.getStealProofLvl();
        double currentStealLvl = (int) this.stealLvl;

        if ((this.stealLvl >= target.getStealProofLvl()) ||
                (currentStealLvl / (neededLvl * (neededLvl - currentStealLvl)) > Math.random())) {
            System.out.println("*успешный взлом*");
            target.printInventory();
            Scanner in = new Scanner(System.in);
            System.out.println("Введите № предмета, который хотите украсть: ");
            String itemInd_str = in.nextLine();
            int itemInd_int = 0;
            try {
                itemInd_int = Main.inputAnalyze(itemInd_str, target.getCapacity()) - 1;
            } catch (InputException e) {
                System.out.println(e);
            }
            String item = target.getItem(itemInd_int).toString();
            take(target, target.getItem(itemInd_int), 1);
            return this + " своровал " + item;

        } else
            return this + " не смог обокрасть " + target;
    }

    @Override
    public String setEmotion(CharEmotions emotion) {
        String out1 = super.setEmotion(emotion), out2 = "";
        if (this.getEmotion() == emotion)
            out2 =  this.goTo(null);
        return out2 + out1;
    }
}