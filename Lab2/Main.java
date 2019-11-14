package com.company;

//import com.sun.jdi.event.StepEvent;

import ru.ifmo.se.pokemon.*;

//import java.awt.dnd.DragGestureEvent;

import javax.swing.*;
import javax.swing.text.TabExpander;
import javax.swing.text.TextAction;
import java.nio.charset.CoderResult;
import java.util.Arrays;
import java.util.Scanner;

import static ru.ifmo.se.pokemon.Type.*;

public class Main {

    public static void main(String[] args) {
        //Moves-------------------------------------------------------
        move_Spark Spark = new move_Spark(ELECTRIC, 65, 100, "Spark");
        move_Facade Facade = new move_Facade(NORMAL, 70, 100, "Facade");
        move_Bulldoze Bulldoze = new move_Bulldoze(GROUND, 60, 100, "Bulldoze");
        move_RockSlide RockSlide = new move_RockSlide(ROCK, 75, 90, "Rock Slide");
        move_ShadowClaw ShadowClaw = new move_ShadowClaw(GHOST, 70, 100, "Shadow Claw");

        move_Leer Leer = new move_Leer(NORMAL, 0, 100, "Leer");
        move_WorkUp WorkUp = new move_WorkUp(NORMAL, 0, 100, "Work Up");
        move_Agility Agility = new move_Agility(PSYCHIC, 0, 100, "Agility");
        move_Confide Confide = new move_Confide(NORMAL, 0, 100, "Confide");

        move_IceBeam IceBeam = new move_IceBeam(ICE, 90, 100, "Ice Beam");
        move_Thunder Thunder = new move_Thunder(ELECTRIC, 110, 70, "Thunder");

        Move[] moves = {Spark, Facade, Bulldoze, RockSlide, ShadowClaw, Leer, WorkUp, Agility, Confide, IceBeam, Thunder};
        //------------------------------------------------------------

        //Pokemons----------------------------------------------------
        pok_Giratina pGir = new pok_Giratina("Giratina", 30, moves);
        pok_Carvanha pCar = new pok_Carvanha("Carvanha", 20, moves);
        pok_Sharpedo pShar = new pok_Sharpedo("Sharpedo", 30, moves);
        pok_Porygon pPor = new pok_Porygon("Porygon", 20, moves);
        pok_Porygon2 pPor2 = new pok_Porygon2("Porygon 2", 30, moves);
        pok_PorygonZ pPorZ = new pok_PorygonZ("Porygon Z", 40, moves);

        MyPokemons[] poks = {pGir, pCar, pShar, pPor, pPor2, pPorZ};
        //MyPokemons[] poks = {pGir, pCar, pShar, pPor, pPor2, pPorZ, pGir, pCar, pShar, pPor, pPor2, pPorZ, pGir, pCar, pShar, pPor};
        //------------------------------------------------------------

        //colorList();
        StartRepeatedBattle(poks);
    }

    //Повторяющийся бой
    private static void StartRepeatedBattle(MyPokemons[] poks) {
        boolean repeat = false;
        do {
            PrintTable(poks);
            setTeam(poks);
            System.out.println("Начать новый бой? (Y - да, остальное - нет)");
            Scanner in = new Scanner(System.in);

            if (in.nextLine().trim().toUpperCase().equals("Y"))
                repeat = true;
            else
                repeat = false;

        } while (repeat);
    }

    //Рисует омега таблицу
    private static void PrintTable(MyPokemons[] poks) {
        int maxNameLen = 0, maxTypeLen = 0;
        for (MyPokemons p : poks) {
            int TypeLen = 0;
            if (p.getName().length() > maxNameLen)
                maxNameLen = p.getName().length();

            for (Type type : p.getTypes()) {
                TypeLen += type.toString().length();
            }
            if (TypeLen > maxTypeLen)
                maxTypeLen = TypeLen;
        }
        int spacecnt = Math.max(maxNameLen, maxTypeLen + 2);
        spacecnt += 9;

        int ROWS, COLS = 7;

        if (poks.length < 4)
            COLS = poks.length * 2 + 1;
        ROWS = (int) Math.ceil(poks.length / 3.0) * 5 + 1;

        String[][] table = new String[ROWS][COLS];

        for (int i = 1; i < table.length - 1; i++)
            for (int j = 0; j < table[0].length; j += 2)
                if (i != 5)
                    table[i][j] = "| ";

        for (int i = 0; i < table.length; i += 5)
            for (int j = 0; j < table[0].length; j++)
                if (j % 2 == 1)
                    table[i][j] = repeat(spacecnt + 1, "-");
                else
                    table[i][j] = "+";

        int cnt = 1;
        for (MyPokemons pok : poks) {
            boolean cntChanged = false;
            int symvcnt = 0; //кол-во видимых символов
            Type types[] = pok.getTypes();
            String typeName = "";

            if (types.length == 1) {
                typeName = Color.Colorize(types[0].toString(), types[0]);
                symvcnt += types[0].toString().length();
            } else {
                for (int i = 0; i < types.length; i++) {
                    typeName = typeName.concat(Color.Colorize(types[i].toString(), types[i]));
                    symvcnt += types[i].toString().length();
                    if (i != types.length - 1)
                        typeName = typeName.concat(", ");
                }
                symvcnt += 2;
            }

            for (int i = 1; i < table.length; i += 5)
                for (int j = 1; j < table[0].length; j += 2)
                    if ((table[i][j] == null) & (!cntChanged)) {
                        table[i][j] = String.format("%-" + spacecnt + "s", "№: " + cnt);
                        table[i + 1][j] = String.format("%-" + spacecnt + "s", "Pokemon: " + pok.getName());
                        table[i + 2][j] = String.format("%-" + spacecnt + "s", "Level: " + pok.getLevel());
                        table[i + 3][j] = String.format("%-" + (typeName.length() + spacecnt - symvcnt) + "s", "Type: " + typeName);
                        cnt++;
                        cntChanged = true;
                    }

        }

        for (int i = 0; i < table.length; i++)
            for (int j = 0; j < table[0].length; j++)
                if (table[i][j] == null)
                    table[i][j] = String.format("%-" + spacecnt + "s", "");

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++)
                System.out.print(table[i][j]);
            System.out.println();
        }
    }

    //Создание команд по вводу пользователя
    private static void setTeam(MyPokemons[] poks) {
        Battle b = new Battle();
        Scanner in = new Scanner(System.in);
        int ERROR;
        Pokemon[] Team, Ally = poks, Foe = poks;

        //массив-счётчик занятых покемонов
        int[] UsedPoksId = new int[poks.length];
        for (int i = 1; i < poks.length + 1; i++)
            UsedPoksId[i - 1] = i;

        for (int TeamNumber = 1; TeamNumber < 3; TeamNumber++) {
            do {
                ERROR = -1;

                System.out.println("Введите через пробел номера покемонов, которые будут в команде №" + TeamNumber + ":");
                String input = in.nextLine().trim().replaceAll("\\s+", " "); //Считываем ввод и удаляем лишние пробелы
                String[] pokStrInds = input.split(" "); //строковый массив введённых индексов
                int[] pokIntInds = new int[pokStrInds.length];

                int maxLen;
                if (poks.length > 9) {
                    maxLen = poks.length + poks.length - 9;
                } else {
                    maxLen = poks.length;
                }

                if ((pokStrInds.length > 0) & (pokStrInds.length < maxLen)) {
                    Team = new Pokemon[pokStrInds.length]; //создаём массив для покемонов

                    //Проверяеем что пользователь ввёл номера покемонов
                    boolean CorrectInput = true; //Введены номера покемонов
                    for (String StrIndex : pokStrInds)
                        //Проверяем каждый введённый элемент на соответствие  числу
                        for (int j = 1; j <= poks.length; j++)
                            if (StrIndex.equals("" + j)) {
                                CorrectInput = true;
                                break;
                            } else
                                CorrectInput = false;

                    //Проверка на уникальность номеров
                    boolean AllIndsUniq = false;
                    if (CorrectInput) {
                        for (int k = 0; k < pokStrInds.length; k++)
                            pokIntInds[k] = Integer.parseInt(pokStrInds[k]);

                        for (int CurrentIndex : pokIntInds) {
                            if (UsedPoksId[CurrentIndex - 1] != -1)
                                AllIndsUniq = true;
                            else {
                                AllIndsUniq = false;
                                break;
                            }
                        }
                    } else
                        ERROR = 2;

                    //Формирование команд и пометка покемонов как использованных
                    if (AllIndsUniq) {
                        for (int i = 0; i < pokIntInds.length; i++) {
                            int currentPokId = pokIntInds[i] - 1; //Индексы начиная от 0
                            UsedPoksId[currentPokId] = -1;
                            Team[i] = poks[currentPokId];
                        }
                    } else if (ERROR == -1)
                        ERROR = 3;

                } else {
                    ERROR = 1;
                    Team = new Pokemon[0]; //Чтоб явка не ругалась
                }

                if (ERROR != -1)
                    ErrorDisc(ERROR, poks.length);

            } while (ERROR != -1);

            if (TeamNumber == 1)
                Ally = Team;
            else
                Foe = Team;
        }
        StartBattle(Ally, Foe);
    }

    //Начинает бой
    private static void StartBattle(Pokemon[] Ally, Pokemon[] Foe) {
        Battle b = new Battle();
        for (Pokemon pok : Ally)
            b.addAlly(pok);
        for (Pokemon pok : Foe)
            b.addFoe(pok);
        b.go();
    }

    //Вывод описания ошибки
    private static void ErrorDisc(int ErrorInd, int PoksCount) {
        System.out.println("Ошибка ввода: ");
        switch (ErrorInd) {
            case 1:
                System.out.println("\tВведено неверное количество покемонов");
                break;
            case 2:
                System.out.println("\tВведите номера покемонов от 1 до " + PoksCount);
                break;
            case 3:
                System.out.println("\tКаждый покемон может быть только в одной команде");
                break;
            default:
                break;
        }
    }

    //умножает строки
    private static String repeat(int count, String with) {
        return new String(new char[count]).replace("\0", with);
    }

    //Выводит список ANSII цветов
    private static void colorList() {
        int code;
        Color clr = new Color();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                code = i * 16 + j;
                //System.out.printf("%4s%n", "\u001b[38;5;" + code + "m" + code + clr.RESET);
                System.out.printf("%-20s", "\u001b[38;5;" + code + "m" + code + "   " + clr.RESET);
            }
            System.out.println();
        }
    }
}