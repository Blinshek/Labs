package com.company;

import sun.awt.image.ShortInterleavedRaster;

import java.util.Scanner;

import static com.company.Main.*;
import static com.company.CharacterCondition.*;

class Junior extends Character implements Movements, SewAble {
    private double sewLvl = 2d;

    Junior(String name, int age, Enum sex) {
        super(name, age, sex);
    }

    @Override
    public void sit(String to) {
        this.setCharCondition(SIT);
    }

    @Override
    public void standUp(int power, String from) {
        this.setCharCondition(STAND);
    }

    @Override
    public void run(String to) {
        System.out.println(this + " бежит к " + to);
    }

    @Override
    void say(String phrase, int delayMillis) {
        try {
            System.out.println(this.getName() + ": ");
            System.out.println("\t" + phrase);
            Thread.sleep(delayMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sew(CraftableItem item, Item ingredient) {
        if (this.ItemCount[getItemIndex(ingredient)] >= 2) {

            double sewTime = calcSewTime(item);
            int sewTimeSec = (int) Math.round(sewTime * 5);
            int delayMillis = (int) (sewTimeSec * 10);
            Scanner in = new Scanner(System.in);
            System.out.println("Пошив " + item + " займёт у " + this.getName() + " " + String.format("%.2f", sewTime) + " ч.");
            System.out.println("Приступить к пошиву? (y/n)");
            if (in.nextLine().toLowerCase().equals("y")) {
                //PROGRESS BAR MIGHT BE HERE //~~DONE~~
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

                this.removeItem(ingredient, 2);
                this.take(item);

                if (item.neededSewLvl > sewLvl) {
                    sewLvl *= 1 + (item.neededSewLvl - sewLvl) * 0.2;
                    if (sewLvl > item.neededSewLvl)
                        sewLvl = item.neededSewLvl;
                }
            } else {
                //?????
            }
        } else {
            System.out.println("Недостаточно материала для крафта");
        }
    }

    @Override
    public double calcSewTime(CraftableItem craftResult) {
        double time = 0;
        double characterSewLvl = sewLvl;
        int neededSewLvl = craftResult.neededSewLvl;
        time = neededSewLvl * 0.25 + (neededSewLvl - characterSewLvl) * 0.4;
        return time;
    }
}