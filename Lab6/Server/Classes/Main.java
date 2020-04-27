package com.company;

import Exceptions.CmdValidationException;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

public class Main {
    private static LinkedHashMap<Integer, Movie> films = new LinkedHashMap<>();
    private static ServerSocketChannel serverSocketChan;
    private static Selector selector;
    //для чтения
    private static final ByteBuffer lengthByteBuffer = ByteBuffer.wrap(new byte[4]);
    private static ByteBuffer dataByteBuffer = null;
    private static boolean readLength = true;

    public static void main(String[] args) throws InterruptedException, IOException {
        boolean isStarted = false;
        do {
            try {
                //System.out.println("Введите ip сервера (или ничего, если хотите оставить localhost)");
                //Scanner scan = new Scanner(System.in);
                //String ip = scan.nextLine();
                //startServer((ip.trim().isEmpty()) ? "localhost" : ip);
                startServer("localhost");
                isStarted = true;
            } catch (IOException e) {
                System.err.println("сервер не запустился");
                System.out.println(e.getMessage());
                Thread.sleep(2000);
            }
        } while (!isStarted);

        while (true) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();

                if (key.isAcceptable()) {
                    register();
                    System.out.println("Зарегали " + key.channel().toString().substring(36, key.channel().toString().indexOf(']')));
                }

                if (key.isReadable()) {
                    try {
                        System.out.println("Читаем команду..");
                        recv(key);
                        Command inp = recv(key);
                        ArrayList<String> output = new ArrayList<>();
                        try {
                            if (CmdValidator.validateCmd(inp)) {
                                executeCmd(inp);
                                output = Receiver.getOutput();
                            }
                        } catch (CmdValidationException e) {
                            System.out.println(e.getMessage());
                            output.add(e.getMessage());
                        }
                        answer(output, key);
                    } catch (ClassNotFoundException e) {
                        System.err.println("Ошибка десериализации");
                        e.printStackTrace();
                    } catch (IOException e) {
                        System.err.println("Клиент умер (но сервер живёт)");
                        System.err.println(e.getMessage());
                        iter.remove();
                        key.cancel();
                        break;
                    }
                }
                iter.remove();
            }
        }
    }

    private static void register() throws IOException {
        SocketChannel clientChan = serverSocketChan.accept();
        clientChan.configureBlocking(false);
        clientChan.register(selector, SelectionKey.OP_READ);
    }

    private static void startServer(String ip) throws IOException {
        try {
            films = XmlParser.parseXML();
            Receiver.setMap(films);
            CmdValidator.setMap(films);
            ScriptParser.setMap(films);
            System.out.println("Collection loaded successfully");
        } catch (JAXBException e) {
            System.err.println("Не удалось загрузить коллекцию из файла");
            System.out.println(e.getMessage());
            System.err.println("Stopping server..");
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.err.println("Looks like an environment variable isn't set");
            System.err.println("Stopping server..");
            System.exit(0);
        }

        selector = Selector.open();
        serverSocketChan = ServerSocketChannel.open();
        serverSocketChan.bind(new InetSocketAddress(ip, 4004));
        serverSocketChan.configureBlocking(false);
        serverSocketChan.register(selector, SelectionKey.OP_ACCEPT);
    }

    public static void answer(ArrayList<String> list, SelectionKey key) throws IOException {
        SocketChannel clientChan = (SocketChannel) key.channel();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < 4; i++) baos.write(0);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(list);
        oos.close();
        final ByteBuffer wrap = ByteBuffer.wrap(baos.toByteArray());
        wrap.putInt(0, baos.size() - 4);
        clientChan.write(wrap);
        System.out.println("Ответили клиенту");
    }

    public static Command recv(SelectionKey key) throws IOException, ClassNotFoundException {
        SocketChannel clientChan = (SocketChannel) key.channel();
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
                final Command ret = (Command) ois.readObject();
                // clean up
                dataByteBuffer = null;
                readLength = true;
                System.out.println("\tПолучена команда: " + ret);
                return ret;
            }
        }
        return null;
    }

    private static void executeCmd(Command command) {
        Invoker invoker = new Invoker();
        invoker.setCommand(command);
        invoker.executeCommand();
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
