package com.company;

import Commands.*;
import Exceptions.ScriptParseException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Receiver {
    private static LinkedHashMap<Integer, Movie> collection;

    public static ArrayList<String> output = new ArrayList<>();

    public static void setMap(LinkedHashMap<Integer, Movie> films) {
        collection = films;
    }

    public static ArrayList<String> getOutput() {
        ArrayList<String> newOutput = new ArrayList<>(output);
        output.clear();
        return newOutput;
    }

    public static void info() {
        output.add("Type: LinkedHashMap" +
                "\nSize: " + collection.size());
    }

    //Очистка
    public static void clearCollection() {
        collection.clear();
        output.add("Коллекция очищена");
    }

    //Показ всей коллекции
    public static void showCollection() {
        if (collection.size() > 0) {
            sortCollection();
            for (Map.Entry<Integer, Movie> entry : collection.entrySet())
                output.add("Ключ: " + entry.getKey() + " Значение: " + entry.getValue());
        } else
            output.add("Коллекция пуста");
    }

    //Удаление по ключу
    public static void removeByKey(int K) {
        output.add("Был удалён следующий элемент: " +
                "\n\tКлюч: " + K + " Значение: " + collection.remove(K));
    }

    //Удалить те что больше по ключу
    public static void removeLowerKey(int K) {
        LinkedHashMap<Integer, Movie> deletedMovies = new LinkedHashMap<>();
        for (Map.Entry<Integer, Movie> entry : collection.entrySet())
            if (entry.getKey() < K)
                deletedMovies.put(entry.getKey(), entry.getValue());

        for (Map.Entry<Integer, Movie> entry : deletedMovies.entrySet())
            collection.remove(entry.getKey());

        if (deletedMovies.size() > 0) {
            output.add("Были удалены следующие элементы:");
            for (Map.Entry<Integer, Movie> entry : deletedMovies.entrySet())
                output.add("\tКлюч: " + entry.getKey() + " Значение: " + entry.getValue());
        } else
            output.add("Ошибка: в коллекции нет элементов с ключом меньше чем " + K);
    }

    public static void removeGreaterKey(int K) {
        LinkedHashMap<Integer, Movie> deletedMovies = new LinkedHashMap<>();
        for (Map.Entry<Integer, Movie> entry : collection.entrySet())
            if (entry.getKey() > K)
                deletedMovies.put(entry.getKey(), entry.getValue());

        for (Map.Entry<Integer, Movie> entry : deletedMovies.entrySet())
            collection.remove(entry.getKey());

        if (deletedMovies.size() > 0) {
            output.add("Были удалены следующие элементы:");
            for (Map.Entry<Integer, Movie> entry : deletedMovies.entrySet())
                output.add("\tКлюч: " + entry.getKey() + " Значение: " + entry.getValue());
        } else
            output.add("Ошибка: в коллекции нет элементов с ключом больше чем " + K);
    }

    //Сохраняем в файл
    public static void saveCollection() throws JAXBException {
        sortCollection();
        String path = System.getenv("TEST");
        new File(path + "/films.xml").delete(); //удаление файла

        MovieMap moviesLib = new MovieMap();
        moviesLib.setMovieMap(collection);

        JAXBContext contextObj = JAXBContext.newInstance(MovieMap.class, Movie.class, Person.class, Coordinates.class, Location.class);
        Marshaller marshallerObj = contextObj.createMarshaller();

        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshallerObj.marshal(moviesLib, new File(path + "/films.xml"));
        output.add("Коллекция сохранена");
    }

    public static void countByUsaBoxOffice(float usaBoxOffice) {
        int counter = 0;
        for (Map.Entry<Integer, Movie> entry : collection.entrySet())
            if (entry.getValue().getUsaBoxOffice() == usaBoxOffice)
                counter++;
        output.add("Количество фильмов с кассовыми сборами = " + usaBoxOffice + ": " + counter);
    }

    public static void countGreaterByUsaBoxOffice(float usaBoxOffice) {
        long cnt = collection.entrySet()
                .stream()
                .filter(t -> t.getValue().getUsaBoxOffice() > usaBoxOffice)
                .count();
        output.add("Количество фильмов с кассовыми сборами > " + usaBoxOffice + ": " + cnt);
    }

    public static void minByUsaBoxOffice() {
        if (collection.size() > 0) {
            Movie rez = collection.entrySet()
                    .stream()
                    .min((o1, o2) -> (int) (o1.getValue().getUsaBoxOffice() - o2.getValue().getUsaBoxOffice()))
                    .get().getValue();
            output.add("Элемент с минимальными кассовыми сборами: \n" + rez);
        } else
            output.add("Коллекция пуста");
    }

    public static void replaceIfGreater(int k, Movie newMovie) {
        newMovie.updateID();
        if (collection.get(k).compareTo(newMovie) < 0) {
            collection.replace(k, newMovie);
            output.add("Элемент заменён");
        } else
            output.add("Ошибка: значение нового элемента меньше или равно предыдущему");
    }

    public static void executeScript(String filename) {
        try {
            ScriptParser.setMap(collection);
            ScriptParser.parseScript(filename);
        } catch (ScriptParseException e) {
            output.add("При исполнении скрпита возникла ошибка");
            ScriptParser.clearHistory();
            output.add(e.getMessage());
        } catch (IOException e) {
            output.add("Ошибка, связанная с файлом скрипта");
        }
    }

    //Выход с сохранением коллекции
    public static void exit() {
        try {
            Receiver.saveCollection();
        } catch (JAXBException e) {
            System.out.println("Ошибка при сохранении коллекции");
            e.printStackTrace();
        }
        output.add("Выходим из программы..");
    }

    public static void help() {
        output.add("Доступные команды:");
        output.add("\t" + HelpCmd.cmdName + " - " + HelpCmd.description);
        output.add("\t" + InfoCmd.cmdName + " - " + InfoCmd.description);
        output.add("\t" + ShowCmd.cmdName + " - " + ShowCmd.description);
        output.add("\t" + HistoryCmd.cmdName + " - " + HistoryCmd.description);
        output.add("\t" + InsertCmd.cmdName + " - " + InsertCmd.description);
        output.add("\t" + UpdateCmd.cmdName + " - " + UpdateCmd.description);
        output.add("\t" + RemoveKeyCmd.cmdName + " - " + RemoveKeyCmd.description);
        output.add("\t" + ClearCmd.cmdName + " - " + ClearCmd.description);
        output.add("\t" + ExecuteScriptCmd.cmdName + " - " + ExecuteScriptCmd.description);
        output.add("\t" + ExitCmd.cmdName + " - " + ExitCmd.description);
        output.add("\t" + ReplaceIfGreaterCmd.cmdName + " - " + ReplaceIfGreaterCmd.description);
        output.add("\t" + RemoveGreaterKeyCmd.cmdName + " - " + RemoveGreaterKeyCmd.description);
        output.add("\t" + RemoveLowerKeyCmd.cmdName + " - " + RemoveLowerKeyCmd.description);
        output.add("\t" + MinByUsaBoxOfficeCmd.cmdName + " - " + MinByUsaBoxOfficeCmd.description);
        output.add("\t" + CountByUsaBoxOfficeCmd.cmdName + " - " + CountByUsaBoxOfficeCmd.description);
        output.add("\t" + CountGreaterThanUsaBoxOfficeCmd.cmdName + " - " + CountGreaterThanUsaBoxOfficeCmd.description);
    }

    public static void insertKey(int K, Movie newMovie) {
        newMovie.updateID();
        collection.put(K, newMovie);
        sortCollection();
        output.add("Новый элемент добавлен");
    }

    public static void updateId(int id, Movie newMovie) {
        newMovie.updateID();
        boolean flag = false;
        for (Map.Entry<Integer, Movie> entry : collection.entrySet())
            if (entry.getValue().getId() == id) {
                flag = true;
                collection.replace(entry.getKey(), newMovie);
                sortCollection();
                break;
            }
        if (flag)
            output.add("Элемент обновлён");
    }

    private static void sortCollection() {
        if (collection.size() > 1) {
            LinkedHashMap<Integer, Movie> result2 = new LinkedHashMap<>();
            collection.entrySet().stream()
                    .sorted(Map.Entry.<Integer, Movie>comparingByValue().reversed())
                    .forEachOrdered(x -> result2.put(x.getKey(), x.getValue()));
            collection = result2;
        }
    }
}