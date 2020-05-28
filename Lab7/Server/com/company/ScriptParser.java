package com.company;

import Commands.*;
import Enums.Country;
import Enums.MovieGenre;
import Enums.MpaaRating;
import Exceptions.ScriptParseException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.company.Main.*;
import static com.company.Main.tryParseLong;

public final class ScriptParser {
    private static ConcurrentHashMap<Integer, Movie> collection;

    private static List<String> lines = null;
    private static int lineNum = 0;
    private static ArrayList<String> scriptHistory = new ArrayList<>();

    public static void setMap(ConcurrentHashMap<Integer, Movie> movieMap) {
        collection = movieMap;
    }

    public static void parseScript(String fileName, Client owner) throws ScriptParseException, IOException {
        if (System.getenv("TEST") == null)
            throw new ScriptParseException("невозможно выполнить скрипт, переменная среды не указана", 0);

        boolean scriptError = false;

        if (!scriptHistory.isEmpty())
            for (String script : scriptHistory)
                if (script.equals(fileName)) {
                    scriptError = true;
                    Receiver.output.add("Ошибка: рекурсивный вызов скрипта");
                    break;
                }
        if (!scriptError) {
            scriptHistory.add(fileName);
            Receiver.output.add("Выполнение скрипта: " + fileName);

            String path = System.getenv("TEST");
            lines = Files.readAllLines(Paths.get(path + "/" + fileName), StandardCharsets.UTF_8);

            for (lineNum = 0; lineNum < lines.size() && !scriptError; lineNum++) {
                String input = lines.get(lineNum);
                input = input.trim();
                input += " ";
                String command = input.substring(0, input.indexOf(" "));
                input = input.replace(command, "");
                String cmdBody = input.trim();
                switch (command) {
                    case LoginCmd.cmdName: {
                        if (cmdBody.isEmpty() & (lineNum + 2 < lines.size())) {
                            Client oldUser = readLogin(++lineNum);
                            if (oldUser == null)
                                throw new ScriptParseException("неправильно указаны логин и/или пароль", lineNum);
                            System.out.println("В скрипте был " + oldUser.getLogin() + " " + oldUser.getPassword());
                            scriptError = !executeScriptCmd(new LoginCmd(owner, oldUser), owner);
                            break;
                        } else
                            throw new ScriptParseException("введены лишние аргументы", lineNum);
                    }
                    case RegistrationCmd.cmdName: {
                        if (cmdBody.isEmpty() & (lineNum + 3 < lines.size())) {
                            Client newUser = readNewUser(++lineNum);
                            scriptError = !executeScriptCmd(new RegistrationCmd(owner, newUser), owner);
                            break;
                        } else
                            throw new ScriptParseException("введены лишние аргументы", lineNum);
                    }
                    case LogoutCmd.cmdName: {
                        if (cmdBody.isEmpty()) {
                            executeScriptCmd(new LogoutCmd(owner), owner);
                            break;
                        } else
                            throw new ScriptParseException("введены лишние аргументы", lineNum);
                    }
                    case HelpCmd.cmdName: {
                        if (cmdBody.isEmpty()) {
                            executeScriptCmd(new HelpCmd(), owner);
                            break;
                        } else
                            throw new ScriptParseException("введены лишние аргументы", lineNum);
                    }
                    case InfoCmd.cmdName: {
                        if (cmdBody.isEmpty()) {
                            executeScriptCmd(new InfoCmd(), owner);
                            break;
                        } else
                            throw new ScriptParseException("введены лишние аргументы", lineNum);
                    }
                    case ShowCmd.cmdName: {
                        if (cmdBody.isEmpty()) {
                            executeScriptCmd(new ShowCmd(), owner);
                            break;
                        } else
                            throw new ScriptParseException("введены лишние аргументы", lineNum);
                    }
                    case ClearCmd.cmdName: {
                        if (cmdBody.isEmpty()) {
                            executeScriptCmd(new ClearCmd(), owner);
                            break;
                        } else
                            throw new ScriptParseException("введены лишние аргументы", lineNum);
                    }
                    case ExecuteScriptCmd.cmdName: {
                        if (!cmdBody.isEmpty()) {
                            if (cmdBody.contains(".txt"))
                                ScriptParser.parseScript(cmdBody, owner);
                            else
                                ScriptParser.parseScript(cmdBody + ".txt", owner);
                        } else
                            throw new ScriptParseException("неправильно введено имя файла скрипта", lineNum);
                        break;
                    }
                    case ExitCmd.cmdName: {
                        if (cmdBody.isEmpty()) {
                            executeScriptCmd(new ExitCmd(), owner);
                            break;
                        } else
                            throw new ScriptParseException("введены лишние аргументы", lineNum);
                    }

                    case HistoryCmd.cmdName: {
                        if (cmdBody.isEmpty()) {
                            executeScriptCmd(new HistoryCmd(), owner);
                            break;
                        } else
                            throw new ScriptParseException("введены лишние аргументы", lineNum);
                    }
                    case MinByUsaBoxOfficeCmd.cmdName: {
                        if (cmdBody.isEmpty()) {
                            executeScriptCmd(new MinByUsaBoxOfficeCmd(), owner);
                            break;
                        } else
                            throw new ScriptParseException("введены лишние аргументы", lineNum);
                    }
                    case CountByUsaBoxOfficeCmd.cmdName: {
                        if (tryParseFloat(cmdBody) && Float.parseFloat(cmdBody) > 0) {
                            float amount = Float.parseFloat(cmdBody);
                            executeScriptCmd(new CountByUsaBoxOfficeCmd(amount), owner);
                            break;
                        } else
                            throw new ScriptParseException("USA box office должен быть неотрицательным числом", lineNum);
                    }
                    case CountGreaterThanUsaBoxOfficeCmd.cmdName: {
                        if (tryParseFloat(cmdBody) && Float.parseFloat(cmdBody) > 0) {
                            float amount = Float.parseFloat(cmdBody);
                            executeScriptCmd(new CountGreaterThanUsaBoxOfficeCmd(amount), owner);
                            break;
                        } else
                            throw new ScriptParseException("USA box office должен быть неотрицательным числом", lineNum);
                    }
                    case InsertCmd.cmdName: {
                        if (lineNum + 14 <= lines.size()) {
                            scriptError = !executeScriptCmd(new InsertCmd(readMovie(++lineNum)), owner);
                            break;
                        } else
                            throw new ScriptParseException("неправильно введён ключ или элемент коллекции", lineNum);
                    }
                    case UpdateCmd.cmdName: {
                        if (tryParseInt(cmdBody) && Integer.parseInt(cmdBody) > 0 && (lineNum + 14 <= lines.size())) {
                            boolean flag = false;
                            int id = Integer.parseInt(cmdBody);
                            for (Map.Entry<Integer, Movie> entry : collection.entrySet())
                                if (entry.getValue().getId() == id) {
                                    Movie nwMov = readMovie(++lineNum);
                                    if (nwMov != null) {
                                        scriptError = !executeScriptCmd(new UpdateCmd(id, nwMov), owner);
                                        flag = true;
                                    }
                                    break;
                                }
                            if (flag)
                                break;
                        }
                        throw new ScriptParseException("Неправильно введён id или элемент коллекции", lineNum);
                    }
                    case RemoveKeyCmd.cmdName: {
                        if (tryParseInt(cmdBody) && Integer.parseInt(cmdBody) > 0) {
                            int k = Integer.parseInt(cmdBody);
                            for (Map.Entry<Integer, Movie> entry : collection.entrySet())
                                if (entry.getKey() == k) {
                                    scriptError = !executeScriptCmd(new RemoveKeyCmd(k), owner);
                                    break;
                                }
                        } else
                            throw new ScriptParseException("ключ должен быть целым положительным числом", lineNum);
                    }
                    case ReplaceIfGreaterCmd.cmdName: {
                        if (tryParseInt(cmdBody) && Integer.parseInt(cmdBody) > 0 && (lineNum + 14 <= lines.size())) {
                            int k = Integer.parseInt(cmdBody);
                            Movie newMovie = readMovie(++lineNum);
                            scriptError = !executeScriptCmd(new ReplaceIfGreaterCmd(k, newMovie), owner);
                            break;
                        } else
                            throw new ScriptParseException("Неправильно введён ключ или элемент коллекции", lineNum);
                    }
                    case RemoveGreaterKeyCmd.cmdName: {
                        if (tryParseInt(cmdBody) && Integer.parseInt(cmdBody) > 0) {
                            int k = Integer.parseInt(cmdBody);
                            scriptError = !executeScriptCmd(new RemoveGreaterKeyCmd(k), owner);
                            break;
                        } else
                            throw new ScriptParseException("ключ должен быть целым положительным числом", lineNum);
                    }
                    case RemoveLowerKeyCmd.cmdName: {
                        if (tryParseInt(cmdBody) && Integer.parseInt(cmdBody) > 0) {
                            int k = Integer.parseInt(cmdBody);
                            scriptError = !executeScriptCmd(new RemoveLowerKeyCmd(k), owner);
                            break;
                        } else
                            throw new ScriptParseException("ключ должен быть целым положительным числом", lineNum);
                    }

                    default: {
                        throw new ScriptParseException("Несуществующая команда", lineNum);
                    }
                }
            }
            scriptHistory.remove(fileName);
        }
    }

    private static Client readLogin(int lineNumber) {
        String inp = lines.get(lineNumber);
        String login, pass;

        if (!inp.isEmpty()) {
            login = inp;
            inp = lines.get(lineNumber + 1);
        } else
            return null;

        if (!inp.isEmpty() && !inp.contains("/")) {
            pass = inp;
        } else
            return null;
        lineNum = lineNumber + 2;
        return new Client(login, pass);
    }

    private static Client readNewUser(int lineNumber) {
        String inp = lines.get(lineNumber);
        String login, pass1;

        if (!inp.isEmpty()) {
            login = inp;
            inp = lines.get(lineNumber + 1);
        } else
            return null;

        if (!inp.isEmpty() && !inp.contains("/")) {
            pass1 = inp;
            inp = lines.get(lineNumber + 1);
        } else
            return null;

        if (inp.equals(pass1)) {
            return new Client(login, pass1);
        } else
            return null;

    }

    private static Movie readMovie(int lineNumber) {
        String title = "";
        String inp = lines.get(lineNumber);
        int coordX = 0, coordY = 0, oscarCnt = 0;
        Float usaBoxOffice = -1f;
        boolean flag = false;
        MovieGenre genre = null;
        MpaaRating mpaaRating = null;
        Coordinates coordinates;
        Person screenwriter;

        //Ввод нового фильма
        //Название
        if (inp.trim().length() > 0)
            title = inp;
        else
            return null;

        //Ввод координат
        //X
        inp = lines.get(lineNumber + 1);
        if (tryParseInt(inp) && Integer.parseInt(inp) < 914)
            coordX = Integer.parseInt(inp);
        else
            return null;

        //Y
        inp = lines.get(lineNumber + 2);
        if (tryParseInt(inp) && Integer.parseInt(inp) < 914)
            coordY = Integer.parseInt(inp);
        else
            return null;
        coordinates = new Coordinates(coordX, coordY);

        //Количество полученных оскаров
        inp = lines.get(lineNumber + 3);
        if (tryParseInt(inp) && Integer.parseInt(inp) > 0) {
            oscarCnt = Integer.parseInt(inp);
        } else
            return null;

        //Кассовые сборы в США
        inp = lines.get(lineNumber + 4);
        if (tryParseFloat(inp) && Float.parseFloat(inp) > 0)
            usaBoxOffice = Float.parseFloat(inp);
        else
            return null;

        //Жанр
        flag = false;
        inp = lines.get(lineNumber + 5);
        if (!inp.isEmpty()) {
            for (MovieGenre gen : MovieGenre.values())
                if (inp.toUpperCase().equals(gen.toString())) {
                    flag = true;
                    genre = MovieGenre.valueOf(inp.toUpperCase());
                    break;
                }
            if (!flag)
                return null;
        }

        //MPAA-рейтинг
        flag = false;
        inp = lines.get(lineNumber + 6);
        if (!inp.isEmpty()) {
            for (MpaaRating rating : MpaaRating.values())
                if (inp.toUpperCase().equals(rating.toString())) {
                    flag = true;
                    mpaaRating = MpaaRating.valueOf(inp.toUpperCase());
                    break;
                }
            if (!flag)
                return null;
        }

        screenwriter = readPerson(lineNumber + 7);

        return (screenwriter != null) ? (new Movie(0, title, coordinates, oscarCnt, usaBoxOffice, genre, mpaaRating,
                screenwriter)) : null;
    }

    private static Person readPerson(int lineNumber) {
        String name = "", locationName = "";
        String inp;
        boolean flag = false;
        long locX;
        Long locY, locZ;
        Country country = null;
        Location location;
        java.time.LocalDate birthday = null;

        //Ввод сценариста
        //Имя
        inp = lines.get(lineNumber);
        if (!inp.isEmpty())
            name = inp;
        else
            return null;

        //Birthday
        inp = lines.get(lineNumber + 1);
        int[] nums;
        if (!inp.isEmpty()) {
            if (inp.matches("\\d\\d[-]\\d\\d[-]\\d{4}")) {
                nums = Arrays.stream(inp.split("-")).mapToInt(Integer::parseInt).toArray();
                if (nums[1] < 13 && nums[0] < YearMonth.of(nums[2], nums[1]).lengthOfMonth())
                    birthday = LocalDate.of(nums[2], nums[1], nums[0]);
                else
                    return null;
            } else
                return null;
        }

        //Страна
        inp = lines.get(lineNumber + 2);
        for (Country entry : Country.values())
            if (inp.toUpperCase().equals(entry.toString())) {
                flag = true;
                country = Country.valueOf(inp.toUpperCase());
                break;
            }
        if (!flag)
            return null;

        //Ввод локации
        //Ввод координат
        //X
        inp = lines.get(lineNumber + 3);
        if (tryParseLong(inp))
            locX = Long.parseLong(inp);
        else
            return null;

        //Y
        inp = lines.get(lineNumber + 4);
        if (tryParseLong(inp))
            locY = Long.parseLong(inp);
        else
            return null;

        //Z
        inp = lines.get(lineNumber + 5);
        if (tryParseLong(inp))
            locZ = Long.parseLong(inp);
        else
            return null;

        //Ввод названия локации
        inp = lines.get(lineNumber + 6);
        if (inp.length() != 0 && inp.length() < 578)
            locationName = inp;
        else
            return null;
        location = new Location(locX, locY, locZ, locationName);

        lineNum = lineNumber + 6;
        return new Person(name, birthday, country, location);
    }

    public static void clearHistory() {
        scriptHistory.clear();
    }

    private static boolean executeScriptCmd(Command command, Client owner) {
        command.setOwner(owner);
        //ExecuteTask.addTask(owner, command);
        System.out.println(command.modifier);
        switch (command.modifier) {
            case VARIATE:
            case PUBLIC: {
                executeCmd(command);
                break;
            }
            case PRIVATE: {
                if (owner.isLogged())
                    executeCmd(command);
                else {
                    Receiver.output.add("Ошибка: у вас нет доступа к данной команде");
                    return false;
                }
                break;
            }
            case PUBLIC_ONLY: {
                if (!owner.isLogged())
                    executeCmd(command);
                else {
                    Receiver.output.add("Ошибка: у вас нет доступа к данной команде");
                    return false;
                }
                break;
            }
            default: {
                break;
            }
        }
        return true;
    }

    private static void executeCmd(Command command) {
        Invoker invoker = new Invoker();
        invoker.setCommand(command);
        invoker.executeCommand();
    }
}