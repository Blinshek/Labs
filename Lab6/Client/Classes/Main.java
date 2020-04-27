package com.company;

import Commands.ExitCmd;
import Commands.HistoryCmd;
import Exceptions.CommandParseException;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Socket clientSocket = null; //сокет для общения
    private static Scanner scan = new Scanner(System.in); //читаем с консольки

    public static void main(String[] args) {
        while (true) {
            lifeCycle();
        }
    }

    private static void lifeCycle() {
        System.out.println("Введите ip сервера (или ничего, если он localhost)");
        String ip = scan.nextLine();
        connect((ip.trim().isEmpty()) ? "localhost" : ip);

        System.out.println("Перед началом работы с коллекцией введите /help для просмотра списка команд.");

        while (true) {
            System.out.println("Введите команду:");
            String input = scan.nextLine(); // ждём пока клиент что-нибудь не напишет в консоль

            Command inpCommand;
            try {
                inpCommand = CommandParser.parseCommand(input);
            } catch (CommandParseException e) {
                System.out.println(e.getMessage());
                continue;
            }

            if (inpCommand != null) {
                try {
                    if (inpCommand.getClass().equals(HistoryCmd.class)) {
                        CommandHistory.getHistory().forEach(System.out::println);
                        CommandHistory.add(inpCommand);
                    } else {
                        send(inpCommand);
                        recv();
                        ArrayList<String> ret = recv();
                        if (!ret.isEmpty()) {
                            ret.forEach(System.out::println);
                            if (!ret.get(0).startsWith("Ошибка"))
                                CommandHistory.add(inpCommand);
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Сервер умер");
                    e.getMessage();
                    clientSocket = null;
                    break;
                }
                //проверка на /exit
                if (inpCommand.getClass().equals(ExitCmd.class))
                    System.exit(0);
            } else
                System.out.println("Несуществующая команда. Введите /help для просмотра списка команд.");
        }
    }

    public static void send(Command inpCommand) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < 4; i++) baos.write(0);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(inpCommand);
        oos.close();
        final ByteBuffer wrap = ByteBuffer.wrap(baos.toByteArray());
        wrap.putInt(0, baos.size() - 4);
        clientSocket.getOutputStream().write(wrap.array());
    }

    private static final ByteBuffer lengthByteBuffer = ByteBuffer.wrap(new byte[4]);
    private static ByteBuffer dataByteBuffer = null;
    private static boolean readLength = true;

    public static ArrayList<String> recv() throws IOException, ClassNotFoundException {
        ReadableByteChannel clientChan = Channels.newChannel(clientSocket.getInputStream());
        if (readLength) {
            clientChan.read(lengthByteBuffer);
            if (lengthByteBuffer.remaining() == 0) {
                readLength = false;
                dataByteBuffer = ByteBuffer.allocate(lengthByteBuffer.getInt(0));
                lengthByteBuffer.clear();
            }
        } else {
            clientChan.read(dataByteBuffer);
            if (dataByteBuffer.remaining() == 0) {
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(dataByteBuffer.array()));
                final ArrayList<String> ret = (ArrayList<String>) ois.readObject();
                //clean up
                dataByteBuffer = null;
                readLength = true;
                return ret;
            }
        }
        return new ArrayList<>();
    }

    private static void connect(String ip) {
        boolean flag = false;
        do {
            try {
                System.out.println("Ждём подключения..");
                int tryes = 0;
                do {
                    try {
                        clientSocket = new Socket(ip, 4004); //конектимся
                    } catch (IOException e) {
                        clientSocket = null;
                        tryes++;
                        Thread.sleep(1000);
                    }
                } while (clientSocket == null && tryes < 10);
                System.out.println("Подключились к серверу " + clientSocket.getInetAddress().getHostAddress());
                flag = true;
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        } while (!flag);
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