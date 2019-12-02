package com.company;

interface ActionsWithItems {
    default void take(Item takedObject) {
        take(takedObject, 1);
    }
    void take(Item takedObject, int count);

    default void give(Item item, Character to) {
        give(item, to, 1);
    }
    void give(Item item, Character to, int count);
}

interface SewAble {
    void sew(CraftableItem item, Item ingredient);
    double calcSewTime(CraftableItem craftResult);
}

interface Movements{
    void sit(String to);
    void standUp(int power, String from);
    void run(String to);
}

interface FlyAble {
    void fly();
    void fly(String to);
}