package com.company;

import Enums.Country;
import Enums.MovieGenre;
import Enums.MpaaRating;
import Exceptions.CommandParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.time.LocalDate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static MoviesLib moviesLib = new MoviesLib();

    public static void main(String[] args){
        //инциализация всякого
        Invoker invoker = new Invoker();
        CommandParser parser = new CommandParser(moviesLib);
        Scanner scan = new Scanner(System.in);
        //--------------------

        //Дефолт либа
        try{
            parseXML(moviesLib);
        } catch (Exception e){
            System.out.println("Не удалось загрузить коллекцию из файла");
        }
        //------------

        System.out.println("Перед началом работы с коллекцией введите /help для просмотра списка команд.");
        while (true) {
            String input = scan.nextLine();
            Command inpCommand;
            try {
                inpCommand = parser.parseCommand(input);
            } catch (CommandParseException e){
                System.out.println(e.getMessage());
                continue;
            }
            if (inpCommand != null) {
                invoker.setCommand(inpCommand);
                invoker.executeCommand();
            } else
                System.out.println("Несуществующая команда. Введите /help для просмотра списка команд.");
        }
    }

    private static void parseXML(MoviesLib moviesLib) throws ParserConfigurationException, SAXException, IOException {
        String path = System.getenv("TEST");
        // Получение фабрики, чтобы после получить билдер документов.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // Получили из фабрики билдер, который парсит XML, создает структуру Document в виде иерархического дерева.
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Запарсили XML, создав структуру Document. Теперь у нас есть доступ ко всем элементам, каким нам нужно.
        Document document = builder.parse(new File(path + "/films.xml"));
        //Document document = builder.parse(new File(path));

        NodeList values = document.getDocumentElement().getElementsByTagName("value");
        NodeList keys = document.getDocumentElement().getElementsByTagName("key");

        for (int i = 0; i < values.getLength(); i++) {
            String[] props = new String[8];
            String[] coords = new String[2];
            String[] screenWriter = new String[4];
            String[] location = new String[4];
            Node mov = values.item(i);
            NodeList fields = mov.getChildNodes();

            for (int j = 0; j < fields.getLength(); j++) {
                Node prop = fields.item(j);
                if (prop.getNodeType() != Node.TEXT_NODE) {
                    if (prop.getNodeName().equals("coordinates")) {
                        NodeList coord = prop.getChildNodes();
                        coords[0] = coord.item(1).getChildNodes().item(0).getTextContent();
                        coords[1] = coord.item(3).getChildNodes().item(0).getTextContent();
                        continue;
                    }
                    if (prop.getNodeName().equals("genre")) {
                        props[6] = prop.getChildNodes().item(0).getTextContent();
                        continue;
                    }
                    if (prop.getNodeName().equals("mpaaRating")) {
                        props[7] = prop.getChildNodes().item(0).getTextContent();
                        continue;
                    }
                    if (prop.getNodeName().equals("screenWriter")) {
                        NodeList scrnWriter = prop.getChildNodes();
                        for (int k = 0; k < scrnWriter.getLength(); k++) {
                            Node writerProp = scrnWriter.item(k);
                            if (writerProp.getNodeType() != Node.TEXT_NODE) {
                                if (writerProp.getNodeName().equals("birthday")) {
                                    screenWriter[3] = scrnWriter.item(k).getChildNodes().item(0).getTextContent();
                                    continue;
                                }
                                if (writerProp.getNodeName().equals("location")) {
                                    NodeList locProps = writerProp.getChildNodes();
                                    for (int l = 0; l < locProps.getLength(); l++) {
                                        Node locProp = locProps.item(l);
                                        if (locProp.getNodeType() != Node.TEXT_NODE)
                                            location[l / 2] = locProp.getChildNodes().item(0).getTextContent();
                                    }
                                    continue;
                                }
                                screenWriter[k / 2] = writerProp.getChildNodes().item(0).getTextContent();
                            }
                        }
                        continue;
                    }
                    props[j / 2] = prop.getChildNodes().item(0).getTextContent();
                }
            }
            Coordinates coordinates = new Coordinates(Float.parseFloat(coords[0]), Float.parseFloat(coords[1]));

            Location location1 = new Location(Long.parseLong(location[0]), Long.parseLong(location[1]), Long.parseLong(location[2]), location[3]);

            LocalDate birthday = (screenWriter[3] == null) ? null : LocalDate.parse(screenWriter[3]);

            LocalDate creationDate = (props[3] == null) ? null : LocalDate.parse(props[3]);
            Person ScreenWriter = new Person(screenWriter[0], birthday, Country.valueOf(screenWriter[1]), location1);

            MovieGenre genre = (props[6] == null) ? null : MovieGenre.valueOf(props[6]);

            MpaaRating rating = (props[7] == null) ? null : MpaaRating.valueOf(props[7]);

            Movie movie = new Movie(Long.parseLong(props[0]), creationDate, props[1], coordinates,
                    Integer.parseInt(props[4]), Float.parseFloat(props[5]), genre, rating, ScreenWriter);

            int K = Integer.parseInt(keys.item(i).getChildNodes().item(0).getTextContent());

            moviesLib.films.put(K, movie);
        }
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