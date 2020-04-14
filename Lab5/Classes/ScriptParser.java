package com.company;

import Commands.*;
import Enums.Country;
import Enums.MovieGenre;
import Enums.MpaaRating;
import Exceptions.CommandParseException;
import Exceptions.ScriptParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import static com.company.Main.*;
import static com.company.Main.tryParseLong;

public final class ScriptParser {
    private MoviesLib moviesLib;
    private LinkedHashMap<Integer, Movie> collection;

    private static List<String> lines = null;
    private static int lineNum = 0;
    private static ArrayList<String> scriptHistory = new ArrayList<>();

    public ScriptParser(MoviesLib moviesLib) {
        this.moviesLib = moviesLib;
        this.collection = moviesLib.films;
    }

    public void parseScript(String fileName) throws ScriptParseException, IOException { //ex Test.txt
        final int bufferSize = 50;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        boolean scriptError = false;

        if (!scriptHistory.isEmpty())
            for (String script : scriptHistory) {
                if (script.equals(fileName)) {
                    scriptError = true;
                    System.out.println("Ошибка: рекурсивный вызов скрипта");
                    break;
                }
            }

        if (!scriptError) {
            scriptHistory.add(fileName);
            System.out.println("Выполнение скрипта: " + fileName);

            //String line = "";
            InputStreamReader reader = null;

            //reader = new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8);
        /*
        for (; ; ) {
            int rsz = reader.read(buffer, 0, buffer.length);
            //int rsz = reader.read();
            if (rsz == -1) break;

            if(rsz != 10)
                line += (char)rsz;
            else {
                System.out.println(line);
                line = "";
            }
            //out.append(buffer, 0, rsz);
        }
        for(char ch: buffer)
            if(ch !=10)
                System.out.println(ch);
                */
            String path = System.getenv("TEST");
            lines = Files.readAllLines(Paths.get(path + "/" + fileName), StandardCharsets.UTF_8);

            for (lineNum = 0; lineNum < lines.size(); lineNum++) {
                String input = lines.get(lineNum);
                input += " ";
                String command = input.substring(0, input.indexOf(" "));
                input = input.trim();
                String cmdBody = input.substring(input.indexOf(" ") + 1);
                switch (command) {
                    case HelpCmd.cmdName: {
                        if (command.equals(input)) {
                            executeScriptCmd(new HelpCmd());
                            break;
                        }
                    }
                    case InfoCmd.cmdName: {
                        if (command.equals(input)) {
                            executeScriptCmd(new InfoCmd());
                            break;
                        }
                    }
                    case ShowCmd.cmdName: {
                        if (command.equals(input)) {
                            executeScriptCmd(new ShowCmd());
                            break;
                        }
                    }
                    case InsertCmd.cmdName: {
                        if (tryParseInt(cmdBody) && (Integer.parseInt(cmdBody) > -1) && (lineNum + 14 <= lines.size())) {
                            int k = Integer.parseInt(cmdBody);
                            if (collection.get(k) == null)
                                executeScriptCmd(new InsertCmd(k, readMovie(++lineNum)));
                            else
                                throw new ScriptParseException("элемент с таким ключом уже существует. Воспользуйтесь командой " +
                                        "/update или введите неиспользуемый ключ", lineNum);
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
                                        executeScriptCmd(new UpdateCmd(id, nwMov));
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
                                    executeScriptCmd(new RemoveKeyCmd(k));
                                    break;
                                }
                            throw new ScriptParseException("ключ должен быть целым положительным числом", lineNum);
                        }
                    }
                    case ClearCmd.cmdName: {
                        if (command.equals(input)) {
                            executeScriptCmd(new ClearCmd());
                            break;
                        }
                    }
                    case SaveCmd.cmdName: {
                        if (command.equals(input)) {
                            executeScriptCmd(new SaveCmd(moviesLib));
                            break;
                        }
                    }
                    case ExecuteScriptCmd.cmdName: {
                        if (cmdBody.contains(".txt"))
                            new ScriptParser(moviesLib).parseScript(cmdBody);
                        else
                            new ScriptParser(moviesLib).parseScript(cmdBody + ".txt");
                        break;
                    }
                    case ExitCmd.cmdName: {
                        if (command.equals(input)) {
                            executeScriptCmd(new ExitCmd());
                            break;
                        }
                    }
                    case ReplaceIfGreaterCmd.cmdName: {
                        if (tryParseInt(cmdBody) && Integer.parseInt(cmdBody) > 0 && (lineNum + 14 <= lines.size())) {
                            int k = Integer.parseInt(cmdBody);
                            Movie newMovie = readMovie(++lineNum);
                            executeScriptCmd(new ReplaceIfGreaterCmd(k, newMovie));
                            break;
                        } else
                            throw new ScriptParseException("Неправильно введён ключ или элемент коллекции", lineNum);
                    }
                    case HistoryCmd.cmdName: {
                        if (command.equals(input)) {
                            executeScriptCmd(new HistoryCmd());
                        }
                    }
                    case RemoveGreaterKeyCmd.cmdName: {
                        if (tryParseInt(cmdBody) && Integer.parseInt(cmdBody) > 0) {
                            int k = Integer.parseInt(cmdBody);
                            executeScriptCmd(new RemoveGreaterKeyCmd(k));
                            break;
                        } else
                            throw new ScriptParseException("ключ должен быть целым положительным числом", lineNum);
                    }
                    case RemoveLowerKeyCmd.cmdName: {
                        if (tryParseInt(cmdBody) && Integer.parseInt(cmdBody) > 0) {
                            int k = Integer.parseInt(cmdBody);
                            executeScriptCmd(new RemoveLowerKeyCmd(k));
                            break;
                        } else
                            throw new ScriptParseException("ключ должен быть целым положительным числом", lineNum);
                    }
                    case MinByUsaBoxOfficeCmd.cmdName: {
                        executeScriptCmd(new MinByUsaBoxOfficeCmd());
                    }
                    case CountByUsaBoxOfficeCmd.cmdName: {
                        if (tryParseFloat(cmdBody) && Float.parseFloat(cmdBody) > 0) {
                            float amount = Float.parseFloat(cmdBody);
                            executeScriptCmd(new CountByUsaBoxOfficeCmd(amount));
                            break;
                        } else
                            throw new ScriptParseException("USA box office должен быть неотрицательным числом", lineNum);
                    }
                    case CountGreaterThanUsaBoxOfficeCmd.cmdName: {
                        if (tryParseFloat(cmdBody) && Float.parseFloat(cmdBody) > 0) {
                            float amount = Float.parseFloat(cmdBody);
                            executeScriptCmd(new CountGreaterThanUsaBoxOfficeCmd(amount));
                            break;
                        } else
                            throw new ScriptParseException("USA box office должен быть неотрицательным числом", lineNum);
                    }
                    default: {
                        throw new ScriptParseException("Несуществующая команда", lineNum);
                    }
                }
            }
        }
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

        return (screenwriter != null) ? (new Movie(title, coordinates, oscarCnt, usaBoxOffice, genre, mpaaRating,
                screenwriter)) : null;
    }

    private static Person readPerson(int lineNumber) {
        String name = "", locationName = "";
        String inp;
        boolean flag = false;
        long locX = 0;
        Long locY = 0L, locZ = 0L;
        Country country = null;
        Location location = null;
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

    private static void executeScriptCmd(Command command) {
        Invoker invoker = new Invoker();
        invoker.setCommand(command);
        invoker.executeCommand();
    }
}