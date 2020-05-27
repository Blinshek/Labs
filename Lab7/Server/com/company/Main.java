package com.company;

import ThreadTasks.AnswerTask;
import ThreadTasks.ExecuteTask;
import ThreadTasks.ReaderTask;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    private static LinkedHashMap<Integer, Movie> films = new LinkedHashMap<>();

    public static ArrayList<Client> clients = new ArrayList<>();

    private static DataBase database = new DataBase("jdbc:postgresql://pg:5432/studs", "s285702",
            "cmq337", "org.postgresql.Driver");

    private static Connection dbConnection = null;
    private static Statement statement = null;

    private static ServerSocketChannel serverSocketChan;

    public static void main(String[] args) throws InterruptedException, SQLException {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        boolean isStarted = false;
        do {
            try {
                startServer("localhost");
                isStarted = true;
            } catch (IOException e) {
                System.err.println("сервер не запустился");
                System.out.println(e.getMessage());
                Thread.sleep(2000);
            }
        } while (!isStarted);

        while (true) {
            try {
                SocketChannel clientChan = serverSocketChan.accept();
                if (clientChan != null && clientChan.isConnected()) {
                    clients.add(new Client(clientChan));
                    threadPoolExecutor.execute(new ReaderTask(clients.get(clients.size() - 1)));
                    System.out.println("Подключён клиент: " + clientChan.getRemoteAddress());
                }
            } catch (IOException e) {

                System.err.println("Клиент умер (но сервер живёт)");
                System.err.println(e.getMessage());
            }
            Thread.sleep(1000);
        }
    }

    private static void startServer(String ip) throws IOException {
        if (System.getenv("TEST") == null) {
            System.out.println("Переменная среды не указана. Выполнение скрипта будет недоступным");
            System.out.println("Хотите продолжить? (y / !y)");
            Scanner in = new Scanner(System.in);
            if (!in.nextLine().toLowerCase().equals("y"))
                System.exit(0);
        }

        Receiver.setMap(films);
        CmdValidator.setMap(films);
        ScriptParser.setMap(films);

        serverSocketChan = ServerSocketChannel.open();
        serverSocketChan.bind(new InetSocketAddress(ip, 4006));
        serverSocketChan.configureBlocking(false);
        try {
            database.connectToDB();
            statement = database.getConnection().createStatement();
            Receiver.setDB(database);
        } catch (SQLException | NullPointerException e) {
            System.out.println("Чото не так с бд");
            e.printStackTrace();
            System.out.println("Останавливаем сервер..");
            System.exit(0);
        }
        new ExecuteTask().fork();
        new Thread(new AnswerTask()).start();
    }

    public static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean tryParseFloat(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean tryParseLong(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
