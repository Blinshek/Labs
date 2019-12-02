package com.company;

import static com.company.CharacterCondition.*;

class Karlson extends Character implements FlyAble, Movements {
    Karlson(String name, int age, Enum sex) {
        super(name, age, sex);
    }

    @Override
    void say(String phrase, int
            delayMillis) {
        try {
            System.out.println(this.getName() + ": ");
            System.out.println("\t" + phrase);
            Thread.sleep(delayMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fly() {
        System.out.println(this + " взлетел");
        this.setCharCondition(FLY);
    }

    @Override
    public void fly(String to) {
        System.out.println(this + " полетел " + to);
        this.setCharCondition(FLY);
    }

    @Override
    public void sit(String to) {
        this.setCharCondition(SIT);
        System.out.println(this + " садится на " + to);
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
}