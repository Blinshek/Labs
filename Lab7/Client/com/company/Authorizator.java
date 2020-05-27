package com.company;

import Commands.Command;
import Commands.LoginCmd;
import Commands.RegistrationCmd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static com.company.Main.*;

public final class Authorizator {

    public static LoginCmd Login() {
        String login = "", pass = "";
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("Введите логин:");
            login = scan.nextLine();
        } while (login.isEmpty());
        do {
            System.out.println("Введите пароль:");
            pass = scan.nextLine();
        } while (pass.isEmpty());
        Client newUser = new Client(login, pass);
        //tryAuthorization(new LoginCmd(curClient, newUser), newUser);
        return new LoginCmd(curClient, newUser);
    }

    public static RegistrationCmd Registration() {
        String login = "", pass1 = "", pass2 = "";
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("Введите логин:");
            login = scan.nextLine();
        } while (login.isEmpty());
        do {
            System.out.println("Введите пароль:");
            pass1 = scan.nextLine();
        } while (pass1.isEmpty());
        do {
            System.out.println("Повторите пароль:");
            pass2 = scan.nextLine();
            if(!pass1.equals(pass2))
                System.out.println("Ошибка: пароли не совпадают");
        } while (!pass2.equals(pass1));

        Client newUser = new Client(login, pass1);
        //tryAuthorization(new RegistrationCmd(curClient, newUser), newUser);
        return new RegistrationCmd(curClient, newUser);
    }

    private static void tryAuthorization(Command cmd, Client user) {
        try {
            send(cmd);
            System.out.println("Отправили от " + cmd.getOwner());
            recv();
            ArrayList<String> ret = recv();
            System.out.println(ret.size());
            if (ret.get(0).equals("true"))
                curClient = user;
            System.out.println(ret.get(1));
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}