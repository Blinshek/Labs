package com.company;

import java.util.Scanner;

import static com.company.Sex.*;
import static com.company.Size.*;
import static com.company.Condition.*;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        Junior Malish = new Junior("Малыш", 7, MALE);
        Karlson Karl = new Karlson("Карлсон", 39, MALE);
        BedSheet prost = new BedSheet("Cotton", OLD);
        Costume ghostsCostume = new Costume("Костюм приведения", 4, MEDIUM, NEW);

        int route = 0;
        delayedPrint("Добро пожаловать в интерактивный отрывок произведения \"Малыш и Карлсон\"!");
        delayedPrint(Malish + " и " + Karl + " хотят сшить " + ghostsCostume + ", и вы примите в этом непосредственное участие", 2500);
        delayedPrint("Вы - " + Malish + ", ваша цель - сшить " + ghostsCostume, 2000);
        delayedPrint("Вы готовы? (y/n)", 1000);
        if (in.nextLine().toLowerCase().equals("y")) {
            delayedPrint("Тогда начнём", 500);
            Karl.say("Меня просто бесит эта любовь к порядку! В вашем доме ничего нельзя оставить.", 2000);
            Karl.sit("стул");
            waitMillis(500);
            Karl.say("Нет, так дело не пойдет, так я не играю. Можешь сам стать привидением, если хочешь.", 2500);
            Karl.standUp(1, "стула");
            waitMillis(1000);
            Karl.run("бельевому шкафу");
            waitMillis(1000);
            Karl.say("Здесь наверняка найдется еще какая-нибудь простынка.");
            waitMillis(500);
            BedSheet noway = new BedSheet("linen", NEWEST);
            Karl.take(noway, 2);

            //Выбор №1
            route = updateRoute(route, printActions("Остановить Карлсона", "Ничего не делать"));

            if (route == 1) {
                Malish.say("О нет, эти не надо! Положи их... Вот тут есть и старые, чиненые.", 2000);
                Karl.say("Старые, чиненые простыни! Я думал, маленькое привидение из Вазастана должно щеголять в " +
                        "нарядных воскресных одеждах. Впрочем... раз уж у вас такой дом... давай сюда эти лохмотья.", 3500);

                //Выбор №2
                route = updateRoute(route, printActions("Дать Карлсону старые простыни", "Позволить шить из новых"));

                if (route % 10 == 1) {
                    Karl.removeItem(noway, 2);
                    Malish.take(prost, 2);
                    Malish.give(prost, Karl, 2);
                } else
                    Malish.say("Ладно, бери новые", 1500);
            }

            Malish.say("Если ты их сошьешь, то вполне может получиться одежда для привидения.", 2000);
            Karl.say("Если я их сошью? Ты хочешь сказать, если ты их сошьешь... Давай полетим ко мне, " +
                    "чтобы домомучительница не застала нас врасплох!", 3500);

            Karl.fly("к себе домой");
            Karl.give(Karl.Items[0], Malish, 2);
            Malish.sew(ghostsCostume, Malish.Items[0]);
        }
    }

    //Умножает строки
    static String repeat(int count, String with) {
        return new String(new char[count]).replace("\0", with);
    }

    //Чекаем нужная нам чиселка или нет
    private static int inputAnalyze(String inputStr, int choisesCnt) {
        inputStr = inputStr.replaceAll("\\s+", "");
        for (int i = 0; i < choisesCnt; i++)
            if (inputStr.equals("" + (i + 1)))
                return Integer.parseInt(inputStr);
        return -1;
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
            number = inputAnalyze(in.nextLine(), actions.length);
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