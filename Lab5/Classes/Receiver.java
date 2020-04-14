package com.company;

import Commands.*;
import Exceptions.ScriptParseException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Receiver {
    private MoviesLib moviesLib;
    private LinkedHashMap<Integer, Movie> collection;

    public Receiver(MoviesLib moviesLib) {
        this.moviesLib = moviesLib;
        this.collection = moviesLib.films;
    }

    public void info() {
        System.out.println("Type: LinkedHashMap" +
                "\nSize: " + collection.size());
    }

    //Очистка
    public void clearCollection() {
        collection.clear();
        System.out.println("Коллекция очищена");
    }

    //Показ всей коллекции
    public void showCollection() {
        if (collection.size() > 0) {
            sortCollection();
            for (Map.Entry<Integer, Movie> entry : collection.entrySet())
                System.out.println("Ключ: " + entry.getKey() + " Значение: " + entry.getValue());
        } else
            System.out.println("Коллекция пуста");
    }

    //История команд
    public void showCommandHistory() {
        CommandHistory.show();
    }

    //Удаление по ключу
    public void removeByKey(int K) {
        System.out.println("Был удалён следующий элемент: " +
                "\n\tКлюч: " + K + " Значение: " + collection.remove(K));
    }

    //Удалить те что больше по ключу
    public void removeLowerKey(int K) {
        LinkedHashMap<Integer, Movie> deletedMovies = new LinkedHashMap<>();
        for (Map.Entry<Integer, Movie> entry : collection.entrySet())
            if (entry.getKey() < K)
                deletedMovies.put(entry.getKey(), entry.getValue());

        for (Map.Entry<Integer, Movie> entry : deletedMovies.entrySet())
            collection.remove(entry.getKey());

        if (deletedMovies.size() > 0) {
            System.out.println("Были удалены следующие элементы:");
            for (Map.Entry<Integer, Movie> entry : deletedMovies.entrySet())
                System.out.println("\tКлюч: " + entry.getKey() + " Значение: " + entry.getValue());
        } else
            System.out.println("В коллекции нет элементов с ключом меньше чем " + K);
    }

    public void removeGreaterKey(int K) {
        LinkedHashMap<Integer, Movie> deletedMovies = new LinkedHashMap<>();
        for (Map.Entry<Integer, Movie> entry : collection.entrySet())
            if (entry.getKey() > K)
                deletedMovies.put(entry.getKey(), entry.getValue());

        for (Map.Entry<Integer, Movie> entry : deletedMovies.entrySet())
            collection.remove(entry.getKey());

        if (deletedMovies.size() > 0) {
            System.out.println("Были удалены следующие элементы:");
            for (Map.Entry<Integer, Movie> entry : deletedMovies.entrySet())
                System.out.println("\tКлюч: " + entry.getKey() + " Значение: " + entry.getValue());
        } else
            System.out.println("В коллекции нет элементов с ключом больше чем " + K);
    }

    //Сохраняем в файл
    public void saveCollection(MoviesLib moviesLib) throws Exception {
        sortCollection();
        String path = System.getenv("TEST");
        try {
            FileWriter fstream1 = new FileWriter(path + "/films.xml");// конструктор с одним параметром - для перезаписи
            BufferedWriter out1 = new BufferedWriter(fstream1); //  создаём буферезированный поток
            out1.write(""); // очищаем, перезаписав поверх пустую строку
            out1.close(); // закрываем
        } catch (Exception e) {
            System.err.println("Error in file cleaning: " + e.getMessage());
        }

        FileWriter writer = new FileWriter(path + "/films.xml");
        JAXBContext contextObj = JAXBContext.newInstance(MoviesLib.class, Movie.class, Person.class, Coordinates.class, Location.class);
        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshallerObj.marshal(moviesLib, writer);
        System.out.println("Коллекция сохранена");
    }

    public void countByUsaBoxOffice(float usaBoxOffice) {
        int counter = 0;
        for (Map.Entry<Integer, Movie> entry : collection.entrySet())
            if (entry.getValue().getUsaBoxOffice() == usaBoxOffice)
                counter++;
        System.out.println("Количество фильмов с кассовыми сборами = " + usaBoxOffice + ": " + counter);
    }

    public void countGreaterByUsaBoxOffice(float usaBoxOffice) {
        int counter = 0;
        for (Map.Entry<Integer, Movie> entry : collection.entrySet())
            if (entry.getValue().getUsaBoxOffice() > usaBoxOffice)
                counter++;
        System.out.println("Количество фильмов с кассовыми сборами > " + usaBoxOffice + ": " + counter);
    }

    public void minByUsaBoxOffice() {
        float min = Float.MAX_VALUE;
        int minK = -1;
        for (Map.Entry<Integer, Movie> entry : collection.entrySet())
            if (entry.getValue().getUsaBoxOffice() <= min) {
                min = entry.getValue().getUsaBoxOffice();
                minK = entry.getKey();
            }
        System.out.println("Элемент с минимальными кассовыми сборами: \n" + collection.get(minK));
    }

    public void replaceIfGreater(int k, Movie newMovie) {
        if (collection.get(k).compareTo(newMovie) < 0) {
            collection.replace(k, newMovie);
            System.out.println("Элемент заменён");
        } else
            System.out.println("Значение нового элемента меньше или равно предыдущему");
    }

    public void executeScript(String filename) {
        try {
            new ScriptParser(moviesLib).parseScript(filename);
        } catch (ScriptParseException e) {
            System.out.println("Ошибка при исполнении скрпита");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Возникла ошибка, связанная с файлом скрипта");
        }
    }

    //Выход без сейвы в файл
    public void exit() {
        System.out.println("Выходим из программы..");
        System.exit(0);
    }

    public void help() {
        System.out.println("Доступные команды:");
        System.out.println("\t" + HelpCmd.cmdName + " - " + HelpCmd.description);
        System.out.println("\t" + InfoCmd.cmdName + " - " + InfoCmd.description);
        System.out.println("\t" + ShowCmd.cmdName + " - " + ShowCmd.description);
        System.out.println("\t" + HistoryCmd.cmdName + " - " + HistoryCmd.description);
        System.out.println("\t" + InsertCmd.cmdName + " - " + InsertCmd.description);
        System.out.println("\t" + UpdateCmd.cmdName + " - " + UpdateCmd.description);
        System.out.println("\t" + RemoveKeyCmd.cmdName + " - " + RemoveKeyCmd.description);
        System.out.println("\t" + ClearCmd.cmdName + " - " + ClearCmd.description);
        System.out.println("\t" + SaveCmd.cmdName + " - " + SaveCmd.description);
        System.out.println("\t" + ExecuteScriptCmd.cmdName + " - " + ExecuteScriptCmd.description);
        System.out.println("\t" + ExitCmd.cmdName + " - " + ExitCmd.description);
        System.out.println("\t" + ReplaceIfGreaterCmd.cmdName + " - " + ReplaceIfGreaterCmd.description);
        System.out.println("\t" + RemoveGreaterKeyCmd.cmdName + " - " + RemoveGreaterKeyCmd.description);
        System.out.println("\t" + RemoveLowerKeyCmd.cmdName + " - " + RemoveLowerKeyCmd.description);
        System.out.println("\t" + MinByUsaBoxOfficeCmd.cmdName + " - " + MinByUsaBoxOfficeCmd.description);
        System.out.println("\t" + CountByUsaBoxOfficeCmd.cmdName + " - " + CountByUsaBoxOfficeCmd.description);
        System.out.println("\t" + CountGreaterThanUsaBoxOfficeCmd.cmdName + " - " + CountGreaterThanUsaBoxOfficeCmd.description);
    }

    public void insertKey(int K, Movie movie) {
        collection.put(K, movie);
        sortCollection();
        System.out.println("Новый элемент добавлен");
    }

    public void updateId(int id, Movie movie) {
        boolean flag = false;
        for (Map.Entry<Integer, Movie> entry : collection.entrySet())
            if (entry.getValue().getId() == id) {
                flag = true;
                collection.replace(entry.getKey(), movie);
                sortCollection();
                break;
            }
        if (flag)
            System.out.println("Элемент обновлён");
    }

    private void sortCollection() {
        if (collection.size() > 1) {
            LinkedHashMap<Integer, Movie> result2 = new LinkedHashMap<>();
            collection.entrySet().stream()
                    .sorted(Map.Entry.<Integer, Movie>comparingByValue().reversed())
                    .forEachOrdered(x -> result2.put(x.getKey(), x.getValue()));
            collection = result2;
        }
    }
}