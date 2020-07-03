package com.company;

import javafx.beans.property.SimpleBooleanProperty;

import java.io.Serializable;
import java.nio.channels.SocketChannel;
import java.security.SecureRandom;

public class Client implements Serializable {
    private static final long serialVersionUID = 6L;

    private String login, password;
    private String salt;

    public transient SimpleBooleanProperty logged = new SimpleBooleanProperty();
    private SocketChannel clientChan;

    public Client() {
        logged.setValue(false);
        //logged = false;
    }

    public Client(SocketChannel clientChan) {
        //this.logged = false;
        logged.setValue(false);
        this.clientChan = clientChan;
    }

    public Client(String login, String pass) {
        this(login, pass, generateSalt());
    }

    public Client(String login, String pass, String salt) {
        this.login = login;
        this.password = pass;
        this.salt = salt;
        //this.logged = true;
        logged.setValue(true);
    }

    public void logIn(Client newUser) {
        logIn(newUser.getLogin(), newUser.getPassword(), newUser.getSaltStr());
    }

    public void logIn(String login, String password, String salt) {
        this.login = login;
        this.password = password;
        this.salt = salt;
        //this.logged = true;
        logged.setValue(true);
    }

    public void logOut() {
        //this.logged = false;
        logged.setValue(false);
        this.login = null;
        this.password = null;
        this.salt = null;
    }

    public boolean isLogged() {
        return logged.get();
    }

    private static String generateSalt() {
        String str = "";
        int rand;
        SecureRandom r = new SecureRandom();
        for (int i = 0; i < 16; i++) {
            rand = r.nextInt(26) + 65; //диапазон по ASCII)
            str += (char) rand;
        }
        return str;
    }

    public byte[] getSalt() {
        return salt.getBytes();
    }

    public String getSaltStr() {
        return salt;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public SocketChannel getClientChan() {
        return clientChan;
    }

    @Override
    public String toString() {
        return "Client {" +
                "login = '" + login + '\'' +
                " | password = '" + password + '\'' +
                " | salt = '" + salt + '\'' +
                " | logged = " + logged +
                " | clientChan = " + clientChan +
                '}';
    }
}