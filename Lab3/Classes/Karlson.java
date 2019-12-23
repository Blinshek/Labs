package com.company.Classes;

import com.company.AbstractClasses.*;
import com.company.Enums.Sex;
import com.company.Interfaces.*;

import static com.company.Enums.CharCondition.*;
import static com.company.Enums.CharEmotions.*;

class Karlson extends MyCharacter implements FlyAble {

    Karlson(String name, int age, Sex sex) {
        super(name, age, sex);
    }

    public String doSomethingCool(Object obj){

        if(obj instanceof  MyCharacter) {
            String str = (obj instanceof Thief) ? ((MyCharacter) obj).setEmotion(SCARED) : "";
            return this + " разыгрывает " + obj + ". " + str;

        }
        if(obj instanceof Costume)
             return this + " изображает " + obj;
        return "";
    }

    public String throwThing(Item item){
        Inventory karlInv = this.getInventory();
        karlInv.removeItem(item);
        return this + " выкидывает " + item;
    }

    @Override
    public void fly() {
        System.out.println(this + " взлетел");
        this.setCharCondition(FLY);
    }

    @Override
    public String fly(Place to) {
        this.setLocation(to);
        this.setCharCondition(FLY);
        return this + " полетел в " + to;
    }

    @Override
    public String fly(Place to, MyCharacter with) {
        this.setLocation(to);
        with.setLocation(to);
        this.setCharCondition(FLY);
        return this + " и " + with + " полетели в " + to;
    }
}