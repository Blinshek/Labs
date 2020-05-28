package com.company;

import Commands.*;
import Enums.Country;
import Enums.MovieGenre;
import Enums.MpaaRating;
import Exceptions.ScriptParseException;

import static com.company.Main.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class Receiver {
    private static ConcurrentHashMap<Integer, Movie> collection;
    private static DataBase db;
    private static Statement statement;
    public static ArrayList<String> output = new ArrayList<>();

    public static void setMap(ConcurrentHashMap<Integer, Movie> films) {
        collection = films;
    }

    public static void setDB(DataBase base) throws SQLException {
        db = base;
        statement = db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        syncCollection();
    }

    public static ArrayList<String> getOutput() {
        ArrayList<String> newOutput = new ArrayList<>(output);
        output.clear();
        return newOutput;
    }

    private static void sortCollection() {
        if (collection.size() > 1) {
            ConcurrentHashMap<Integer, Movie> result2 = new ConcurrentHashMap<>();
            collection.entrySet().stream()
                    .sorted(Map.Entry.<Integer, Movie>comparingByValue().reversed())
                    .forEachOrdered(x -> result2.put(x.getKey(), x.getValue()));
            collection = result2;
        }
    }

    private static void syncCollection() throws SQLException {
        String selectAlSql = "select * from movies";
        ResultSet res = statement.executeQuery(selectAlSql);

        ArrayList<Movie> movs = sqlToMovie(res);
        collection.clear();
        movs.forEach(t -> collection.put(t.getId(), t));
    }

    private static String movieToSqlValue(int id, Movie movie, Client owner) {
        String sql;
        Person screenWriter = movie.getScreenWriter();
        Location loc = screenWriter.getLocation();
        Coordinates coords = movie.getCoordinates();
        String idStr = (id != -1) ? ("" + id) : "default";

        String strBirthday = (screenWriter.getBirthday() == null) ? "null" : "'" + screenWriter.getBirthday() + "'";
        String strGenre = (movie.getGenre() == null) ? "null" : "'" + movie.getGenre().toString() + "'";
        String strRating = (movie.getMpaaRating() == null) ? "null" : "'" + movie.getMpaaRating() + "'";

        sql = String.format("(%s, '%s', '%s', ('%s', %s, '%s', (%d, %d, %d, '%s')), %s, %s, " +
                        movie.getUsaBoxOffice() + ", %d, (" +
                        coords.getX() + ", " +
                        coords.getY() + "), '%s')",
                idStr, movie.getTitle(), movie.getCreationDate(), screenWriter.getName(), strBirthday,
                screenWriter.getNationality(), loc.getX(), loc.getY(), loc.getZ(), loc.getName(), strGenre,
                strRating, movie.getOscarsCount(), owner.getLogin());
        return sql;
    }

    private static String movieToSqlValue(Movie movie, Client owner) {
        return movieToSqlValue(movie.getId(), movie, owner);
    }

    private static ArrayList<Movie> sqlToMovie(ResultSet resultSet) throws SQLException {
        ArrayList<Movie> output = new ArrayList<>();
        int id;
        String title;
        LocalDate creationDate;
        Person screenWriter;
        String screenWriterStr;
        LocalDate birthday;
        Location loc;
        MovieGenre genre;
        MpaaRating rating;
        float boxOffice;
        int oscars;
        Coordinates coords;
        String coordsStr;
        resultSet.beforeFirst();
        while (resultSet.next()) {
            id = resultSet.getInt(1);
            title = resultSet.getString(2);
            creationDate = resultSet.getDate(3).toLocalDate();
            boxOffice = resultSet.getFloat(7);
            oscars = resultSet.getInt(8);

            genre = (resultSet.getString(5) == null) ? null : MovieGenre.valueOf(resultSet.getString(5));
            rating = (resultSet.getString(6) == null) ? null : MpaaRating.valueOf(resultSet.getString(6));

            //-Person------------------------------------------------------------------
            screenWriterStr = resultSet.getString(4);
            screenWriterStr = screenWriterStr.replaceAll("\"", "");
            screenWriterStr = screenWriterStr.replaceAll("\\(", "");
            screenWriterStr = screenWriterStr.replaceAll("\\)", "");

            String[] A = screenWriterStr.split(",");

            loc = new Location(Long.parseLong(A[3]), Long.parseLong(A[4]), Long.parseLong(A[5]), A[6]);

            birthday = (A[1].isEmpty()) ? null : LocalDate.parse(A[1]);

            screenWriter = new Person(A[0], birthday, Country.valueOf(A[2]), loc);
            //-------------------------------------------------------------------------

            coordsStr = resultSet.getString(9);
            coords = new Coordinates(Float.parseFloat(coordsStr.substring(1, coordsStr.indexOf(','))),
                    Float.parseFloat(coordsStr.substring(coordsStr.indexOf(',') + 1, coordsStr.length() - 1)));

            Movie movie = new Movie(id, title, creationDate, coords, oscars, boxOffice, genre, rating, screenWriter);
            output.add(movie);
        }
        resultSet.beforeFirst();
        return output;
    }

    private static Client findUser(Client client) throws SQLException {
        String selectTable = "select * from Users";
        ResultSet res = statement.executeQuery(selectTable);
        String hashedPass = "", oldSalt = "";
        while (res.next()) {
            if (res.getString("login").equals(client.getLogin())) {
                hashedPass = res.getString("password");
                oldSalt = res.getString("salt");
                return new Client(client.getLogin(), hashedPass, oldSalt);
            }
        }
        return null;
    }

    private static boolean checkSecondLogin(String login) {
        for (Client entry : clients)
            if (entry.isLogged() && entry.getLogin().equals(login))
                return true;
        return false;
    }


    //-------------------------------------------------------------------------------------------------
    //Регистрация
    public static void registration(Client owner, Client newUser) throws SQLException {
        System.out.println("Нью данные: " + newUser);
        if (findUser(newUser) != null) {
            output.add("Пользователь с таким логином уже существует");
        } else {
            PreparedStatement pState = db.getConnection().prepareStatement("insert into Users values (?, ?, ?)");
            pState.setString(1, newUser.getLogin());
            pState.setString(2, getCryptPass(newUser));
            pState.setString(3, newUser.getSaltStr());
            pState.executeUpdate();
            owner.logIn(newUser);
            output.add("Пользователь " + newUser.getLogin() + " успешно зарегестрирован");
        }
    }

    //Логин
    public static void login(Client owner, Client newUser) throws SQLException {
        Client old = findUser(newUser);
        if (old != null && getCryptPass(newUser, old.getSalt()).equals(old.getPassword())) {
            if (!checkSecondLogin(newUser.getLogin())) {
                owner.logIn(newUser);
                output.add("Добро пожаловать, " + owner.getLogin());
            } else {
                System.out.println("Вторая сессия");
                output.add("Ошибка: " + newUser.getLogin() + " уже подключён с другого адреса");
            }
        } else {
            output.add("Ошибка: неверный логин и/или пароль");
        }
    }

    //выход
    public static void logout(Client owner) {
        output.add("Вы вышли из учётной записи " + owner.getLogin());
        owner.logOut();
    }

    private static String getCryptPass(Client user) {
        return getHashedPass(user, user.getSalt());
    }

    private static String getCryptPass(Client user, byte[] salt) {
        return getHashedPass(user, salt);
    }

    private static String getHashedPass(Client user, byte[] salt) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hashedPassword = md.digest(user.getPassword().getBytes(StandardCharsets.UTF_8));
        String hashPass = new String(hashedPassword);
        if (hashPass.contains("\0")) {
            hashPass = hashPass.replace("\0", "");
        }
        return hashPass;
    }
    //-------------------------------------------------------------------------------------------------

    public static void info() {
        output.add("Type: LinkedHashMap" +
                "\nSize: " + collection.size());
    }

    //Очистка
    public static void clearCollection(Client owner) throws SQLException {
        String clearDB = "delete from Movies where owner = '" + owner.getLogin() + "' returning *;";
        ResultSet res = statement.executeQuery(clearDB);
        if (res != null) {
            ArrayList<Movie> deleted = sqlToMovie(res);
            output.add("Из коллекции были удалены элементы:");
            deleted.forEach(m -> output.add(m.toString()));
            syncCollection();
        } else
            output.add("В коллекции нет элементов, доступных для удаления");
    }

    //Показ всей коллекции
    public static void showCollection() throws SQLException {
        syncCollection();
        if (collection.size() > 0) {
            sortCollection();
            for (Map.Entry<Integer, Movie> entry : collection.entrySet())
                output.add("Ключ: " + entry.getKey() + " Значение: " + entry.getValue());
        } else
            output.add("Коллекция пуста");
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

    //Удаление по id
    public static void removeById(int id, Client owner) throws SQLException {
        String remove = "delete from Movies where id = " + id + " AND owner = '" + owner.getLogin() + "' RETURNING *;";
        ResultSet res = statement.executeQuery(remove);
        if (res.next()) {
            output.add("Из коллекции был удален элемент:");
            output.add(sqlToMovie(res).get(0).toString());
            syncCollection();
        } else
            output.add("Ошибка: в коллекции нет элементов, доступных для удаления");
    }

    //Удалить те что больше по id
    public static void removeLowerId(int id, Client owner) throws SQLException {
        String remove = "delete from Movies where id < " + id + " AND owner = '" + owner.getLogin() + "' returning *;";
        ResultSet res = statement.executeQuery(remove);
        if (res.next()) {
            ArrayList<Movie> deleted = sqlToMovie(res);
            output.add("Из коллекции были удалены элементы:");
            deleted.forEach(m -> output.add(m.toString()));
            syncCollection();
        } else
            output.add("Ошибка: в коллекции нет элементов, доступных для удаления");
    }

    public static void removeGreaterId(int id, Client owner) throws SQLException {
        String remove = "delete from Movies where id > " + id + " AND owner = '" + owner.getLogin() + "' returning *;";
        ResultSet res = statement.executeQuery(remove);
        if (res.next()) {
            ArrayList<Movie> deleted = sqlToMovie(res);
            output.add("Из коллекции были удалены элементы:");
            deleted.forEach(m -> output.add(m.toString()));
            syncCollection();
        } else
            output.add("Ошибка: в коллекции нет элементов, доступных для удаления");
    }

    public static void replaceIfGreater(int id, Movie newMovie, Client owner) throws SQLException {
        String selectOldSql = "select from movies where id = " + id + " AND owner = '" + owner.getLogin() + "';";
        ResultSet res = statement.executeQuery(selectOldSql);
        if (res.next()) {
            ArrayList<Movie> old = sqlToMovie(res);
            Movie oldMovie = old.get(0);
            if (newMovie.compareTo(oldMovie) > 0) {
                String update = "UPDATE movies SET (id, title, creationdate, screenwriter, genre, mpaarating, boxoffice," +
                        "oscars, coordinates, owner) = " + movieToSqlValue(id, newMovie, owner) + ";";
                statement.executeUpdate(update);
                output.add("Элемент с id = " + id + " был обновлён");
                syncCollection();
            } else
                output.add("Ошибка: старое значение больше или равно новому");
        } else
            output.add("Ошибка: в коллекции нет элемента, доступного для обновления");
    }

    public static void insert(Movie newMovie, Client owner) throws SQLException {
        String insert = "insert into movies values " + movieToSqlValue(newMovie, owner) + ";";
        statement.executeUpdate(insert);
        syncCollection();
        sortCollection();
        output.add("Новый элемент добавлен");
    }

    public static void updateId(int id, Movie newMovie, Client owner) throws SQLException {
        String update = "UPDATE movies SET (id, title, creationdate, screenwriter, genre, mpaarating, boxoffice," +
                "oscars, coordinates, owner) = " + movieToSqlValue(id, newMovie, owner) + " WHERE id = " + id +
                " AND owner = '" + owner.getLogin() + "';";
        int i = statement.executeUpdate(update);
        if (i == 1) {
            syncCollection();
            output.add("Элемент с id = " + id + " был обновлён");
        } else
            output.add("Ошибка: в коллекции нет элемента, доступного для обновления");
    }

    public static void executeScript(String filename, Client owner) {
        try {
            ScriptParser.parseScript(filename, owner);
        } catch (ScriptParseException e) {
            output.add("При исполнении скрпита возникла ошибка");
            ScriptParser.clearHistory();
            output.add(e.getMessage());
        } catch (IOException e) {
            output.add("Ошибка, связанная с файлом скрипта");
        }
    }

    //Выход
    public static void exit(Client owner) {
        clients.remove(owner);
        output.add("Выходим из программы..");
    }

    public static void help(Client owner) {
        output.add("Доступные команды:");
        output.add("\t" + HelpCmd.cmdName + " - " + HelpCmd.description);
        output.add("\t" + InfoCmd.cmdName + " - " + InfoCmd.description);
        output.add("\t" + ShowCmd.cmdName + " - " + ShowCmd.description);
        output.add("\t" + HistoryCmd.cmdName + " - " + HistoryCmd.description);
        output.add("\t" + ExitCmd.cmdName + " - " + ExitCmd.description);
        output.add("\t" + MinByUsaBoxOfficeCmd.cmdName + " - " + MinByUsaBoxOfficeCmd.description);
        output.add("\t" + CountByUsaBoxOfficeCmd.cmdName + " - " + CountByUsaBoxOfficeCmd.description);
        output.add("\t" + CountGreaterThanUsaBoxOfficeCmd.cmdName + " - " + CountGreaterThanUsaBoxOfficeCmd.description);
        output.add("\t" + ExecuteScriptCmd.cmdName + " - " + ExecuteScriptCmd.description);

        if (owner.isLogged()) {
            output.add("\t" + LogoutCmd.cmdName + " - " + LogoutCmd.description);
            output.add("\t" + InsertCmd.cmdName + " - " + InsertCmd.description);
            output.add("\t" + UpdateCmd.cmdName + " - " + UpdateCmd.description);
            output.add("\t" + RemoveKeyCmd.cmdName + " - " + RemoveKeyCmd.description);
            output.add("\t" + ClearCmd.cmdName + " - " + ClearCmd.description);
            output.add("\t" + ReplaceIfGreaterCmd.cmdName + " - " + ReplaceIfGreaterCmd.description);
            output.add("\t" + RemoveGreaterKeyCmd.cmdName + " - " + RemoveGreaterKeyCmd.description);
            output.add("\t" + RemoveLowerKeyCmd.cmdName + " - " + RemoveLowerKeyCmd.description);
        } else {
            output.add("\t" + LoginCmd.cmdName + " - " + LoginCmd.description);
            output.add("\t" + RegistrationCmd.cmdName + " - " + RegistrationCmd.description);
        }
    }
}