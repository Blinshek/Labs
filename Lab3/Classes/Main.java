package com.company.Classes;

import com.company.AbstractClasses.*;

import static com.company.Enums.CharEmotions.*;

import com.company.Exceptions.*;

import java.util.Scanner;

import static com.company.Enums.Sex.*;
import static com.company.Enums.Size.*;
import static com.company.Enums.ItemCondition.*;

public class Main {

    public static void main(String[] args) {
        //Initializations-----------------------------------------
        int route = 0;
        Scanner in = new Scanner(System.in);

        Junior Malish = new Junior("Малыш", 7, MALE);
        Karlson Karl = new Karlson("Карлсон", 39, MALE);
        Mom mom = new Mom("Мама", 40);
        Thief Karmik = new Thief("Коэльный", 5);

        BedSheet prost = new BedSheet("Cotton", OLD);
        BedSheet noway = new BedSheet("Linen", NEWEST) {
            @Override
            public String toString() {
                return "Мамина простыня";
            }
        };
        OilLamp lamp = new OilLamp("Керосиновая лампа", 5, true);
        Chimney chimney = new Chimney("Каминчик", 10, 1, true);
        Costume ghostCostume = new Costume("Костюм приведения", MEDIUM, NEW);
        CraftStation workbench = new CraftStation("Верстак", 0.8d);
        CraftStation inspiration = new CraftStation("Вдохновение", 0.5d);

        Chest chest = new Chest("Бельевой шкаф", 2);
        chest.addItem(noway, 2);

        Place.Room karlRoom = new Place.Room("Комната", Karl, chimney, lamp);

        Recipe costumeRecipe = new Recipe(ghostCostume, 4, new Recipe.RequiredItems(BedSheet.class, 2));
        Recipe repairedBedSheet = new Recipe(prost, 1, new Recipe.RequiredItems(Costume.class, 1));

        Inventory karlInventory = Karl.getInventory();
        //--------------------------------------------------------
        Chest speedrun = new Chest("speedrun.com", 10);
        speedrun.addItem(new Item() {
            @Override
            protected int formId() {
                return 0;
            }

            @Override
            public String toString() {
                return "Изи вр";
            }
        });

        Item brush = new Item() {
            @Override
            protected int formId() {
                return 0;
            }

            @Override
            public String toString() {
                return "типа кисть";
            }
        };

        delayedPrint("Вы готовы? (y/n)", 1000);
        if (in.nextLine().toLowerCase().equals("y")) {
            delayedPrint("Тогда начнём", 500);

            Malish.setEmotion(NERVOUS);
            delayedPrint(Malish.setEmotion(NERVOUS));

            delayedPrint(Malish.setEmotion(SURRENDERED));

            Item things = new Item() {
                @Override
                protected int formId() {
                    return 0;
                }

                @Override
                public String toString() {
                    return "любые штуки";
                }
            };
            Karl.take(things);

            String str1, str2, str3;
            str1 = Karl.throwThing(things);
            str2 = Karl.doSomethingCool(ghostCostume);
            str3 = Karl.doSomethingCool(new FrekenBok("Фрекен бок"));
            delayedPrint("Пусть " + str1 + ", " + str2 + ", " + str3);

            String memVar = Karmik.steal(speedrun);
            System.out.println(memVar);
            Malish.addMemory(memVar);

            Malish.addMemory(Karl.doSomethingCool(ghostCostume));

            memVar = Karl.doSomethingCool(Karmik);
            System.out.println(memVar);
            Malish.addMemory(memVar);

            System.out.println(Malish.getMemories());

            Karl.say("Помнишь, как нам тогда было весело?");
            Karl.say("Да, кстати, где же мой " + ghostCostume + "?");

            //Malish.clearMemories();
            Malish.say(mom.take(ghostCostume));
            mom.setEmotion(ANGRY);
            System.out.println(mom.setEmotion(ANGRY));
            System.out.println(mom.repairCostume(ghostCostume));

            Karl.say("Меня просто бесит эта любовь к порядку! В вашем доме ничего нельзя оставить.", 2000);
            Karl.sit("стул");
            waitMillis(500);
            Karl.say("Нет, так дело не пойдет, так я не играю. Можешь сам стать привидением, если хочешь.", 2500);
            Karl.standUp(1, "стула");
            waitMillis(1000);
            Karl.run(chest + "");
            waitMillis(1000);
            Karl.say("Здесь наверняка найдется еще какая-нибудь простынка.");
            waitMillis(500);

            Karl.take(chest, noway, 2);

            //Выбор №1
            route = updateRoute(route, printActions("Остановить Карлсона", "Ничего не делать"));

            if (route == 1) {
                Malish.say("О нет, эти не надо! Положи их... Вот тут есть и старые, чиненые.", 2000);
                Karl.say("Старые, чиненые простыни! Я думал, маленькое привидение из Вазастана должно щеголять в " +
                        "нарядных воскресных одеждах. \nВпрочем... раз уж у вас такой дом... давай сюда эти лохмотья.", 3500);

                //Выбор №2
                route = updateRoute(route, printActions("Дать Карлсону старые простыни", "Позволить шить из новых"));

                if (route % 10 == 1) {
                    karlInventory.removeItem(noway, 2);
                    Malish.take(prost, 2);
                    Malish.give(prost, Karl, 2);
                } else
                    Malish.say("Ладно, бери новые", 1500);
            }

            Malish.say("Если ты их сошьешь, то вполне может получиться одежда для привидения.", 2000);
            Karl.say("Если я их сошью? Ты хочешь сказать, если ты их сошьешь...", 1500);
            System.out.println(Karl.give(karlInventory.getItem(0), Malish, 2));
            Karl.say("давай полетим ко мне, чтобы домомучительница не застала нас врасплох!", 1500);

            System.out.println(Karl.fly(karlRoom, Malish));

            Malish.say("Ты бы хоть скроил", 800);

            Karl.say("Уж если что кроить, то я охотнее всего раскроил бы твою " + mom + "! Да, да! " +
                    "Зачем это ей понадобилось загубить мой " + ghostCostume +
                    "Это только справедливо. Ну, живей за дело и, пожалуйста, не ной! ", 3500);

            Karl.say("Всегда надо все бросать, если тебя посетило вдохновение, понимаешь, а меня оно " +
                    "сейчас посетило. \"Ла, ла, ла\", - поет что-то во мне, и я знаю, что это вдохновение. ", 2500);

            Karl.take(brush);

            Recipe picture = new Recipe(new Kartina("Картина кролика"), 5, new Recipe.RequiredItems(brush.getClass(), 1));
            Karl.craft(picture, inspiration);

            System.out.println(Malish.sit(workbench));

            System.out.println(Malish.craft(costumeRecipe, workbench));

            delayedPrint(karlRoom.getDescription());
        }
    }

    //Умножает строки
    public static String repeat(int count, String with) {
        return new String(new char[count]).replace("\0", with);
    }

    //Чекаем нужная нам чиселка или нет
    public static int inputAnalyze(String inputStr, int choisesCnt) throws InputException {
        inputStr = inputStr.replaceAll("\\s+", "");
        for (int i = 0; i < choisesCnt; i++)
            if (inputStr.equals("" + (i + 1)))
                return Integer.parseInt(inputStr);
        throw new InputException();
    }

    //Вывод вариантов действий
    private static int printActions(String... actions) {
        Scanner in = new Scanner(System.in);
        int number = -1;
        do {
            System.out.println("Ваши действия:");
            for (int i = 0; i < actions.length; i++) {
                System.out.print("\t[" + (i + 1) + "] " + actions[i] + "\t");
            }
            System.out.println();

            try {
                number = inputAnalyze(in.nextLine(), actions.length);
            } catch (InputException e) {
                System.out.println(e);
            }

        } while (number == -1);
        return number;
    }

    //Апдейт рута
    private static int updateRoute(int oldRoute, int nextnum) {
        int newRoute = oldRoute * 10 + nextnum;
        return newRoute;
    }

    //Вывод с задержкой
    private static void delayedPrint(String str) {
        delayedPrint(str, 1500);
    }

    private static void delayedPrint(String str, int delayMillis) {
        try {
            System.out.println(str);
            Thread.sleep(delayMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void waitMillis() {
        waitMillis(1500);
    }

    private static void waitMillis(int delayMillis) {
        try {
            Thread.sleep(delayMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}