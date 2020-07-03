package com.company;

import ThreadTasks.ReaderTask;
import ThreadTasks.SendCmdTask;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Starter {
    public static Socket clientSocket = null; //сокет для общения
    public static Client curClient = new Client();

    public static void start() {
        checkSettings();
    }

    private static void checkSettings() {
        ArrayList<String> lines;
        if (System.getenv("TEST") != null) {
            String path = System.getenv("TEST");
            try {
                lines = new ArrayList<>(Files.readAllLines(Paths.get(path + "/Connection.txt"), StandardCharsets.UTF_8));
                boolean auto = Boolean.parseBoolean(lines.get(2).substring(lines.get(2).indexOf('=') + 1));
                if (auto) {
                    String host = lines.get(0).substring(lines.get(0).indexOf('=') + 1);
                    String port = lines.get(1).substring(lines.get(1).indexOf('=') + 1);
                    connect(host, Integer.parseInt(port));
                }
            } catch (IOException e) {
                //нет файла настроек
            }
        }
    }

    private static void connect(String host, int port) {
        boolean flag = false;
        do {
            try {
                //ждём подключения
                do {
                    try {
                        clientSocket = new Socket(host, port); //конектимся
                    } catch (IOException e) {
                        clientSocket = null;
                        Thread.sleep(1000);
                    }
                } while (clientSocket == null);
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(Localizator.connectionAlert.get() + clientSocket.getInetAddress().getHostAddress());
                    alert.setHeaderText(null);
                    alert.show();
                });

                flag = true;

                new Thread(new ReaderTask(clientSocket)).start();
                new Thread(new SendCmdTask(clientSocket)).start();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        } while (!flag);
    }
}
