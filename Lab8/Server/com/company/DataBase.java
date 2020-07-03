package com.company;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    private String url;
    private String user, password;
    private String driverPath;
    private Connection connection;

    public DataBase(String url, String user, String password, String driverPath) {
        this.user = user;
        this.password = password;
        this.url = url;
        this.driverPath = driverPath;
    }

    private void downloadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void connectToDB() {
        this.downloadDriver();
        while (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                System.out.println("Connection Failed : " + e.getMessage());
            }
            if (connection != null) {
                System.out.println("You made it, take control your database now!");
            } else {
                //System.out.println("Failed to make connection!");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
}