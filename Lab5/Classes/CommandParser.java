package com.company;

import Commands.*;
import Enums.Country;
import Enums.MovieGenre;
import Enums.MpaaRating;
import Exceptions.CommandParseException;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import static com.company.Main.*;
import static com.company.Main.tryParseLong;

public final class CommandParser {

    MoviesLib moviesLib;
    LinkedHashMap<Integer, Movie> collection;

    public CommandParser(MoviesLib moviesLib) {
        this.moviesLib = moviesLib;
        this.collection = moviesLib.films;
    }

    public Command parseCommand(String input) throws CommandParseException {
        input += " ";
        String command = input.substring(0, input.indexOf(" "));
        input = input.trim();
        String cmdBody = input.substring(input.indexOf(" ") + 1);
        switch (command) {
            case HelpCmd.cmdName: {
                return (command.equals(input)) ? new HelpCmd() : null;
            }
            case InfoCmd.cmdName: {
                return (command.equals(input)) ? new InfoCmd() : null;
            }
            case ShowCmd.cmdName: {
                return (command.equals(input)) ? new ShowCmd() : null;
            }
            case InsertCmd.cmdName: {
                if (tryParseInt(cmdBody) && Integer.parseInt(cmdBody) > -1) {
                    int k = Integer.parseInt(cmdBody);
                    if (collection.get(k) == null)
                        return new InsertCmd(k, readMovie());
                    else
                        throw new CommandParseException("элемент с таким ключом уже существует. Воспользуйтесь командой " +
                                "/update или введите неиспользуемый ключ");
                } else
                    throw new CommandParseException("ключ должен быть целым неотрицательным числом");
            }
            case UpdateCmd.cmdName: {
                if (tryParseInt(cmdBody) && Integer.parseInt(cmdBody) > -1) {
                    int id = Integer.parseInt(cmdBody);
                    for (Map.Entry<Integer, Movie> entry : collection.entrySet())
                        if (entry.getValue().getId() == id)
                            return new UpdateCmd(id, readMovie());
                    throw new CommandParseException("элемента с таким id не существует");
                } else
                    throw new CommandParseException("id должно быть целым неотрицательным числом");
            }
            case RemoveKeyCmd.cmdName: {
                if (tryParseInt(cmdBody) && Integer.parseInt(cmdBody) > -1) {
                    int K = Integer.parseInt(cmdBody);
                    if (collection.get(K) != null)
                        return new RemoveKeyCmd(K);
                    else
                        throw new CommandParseException("элемента с таким ключом не существует");
                } else
                    throw new CommandParseException("ключ должен быть целым неотрицательным числом");
            }
            case ClearCmd.cmdName: {
                return (command.equals(input)) ? new ClearCmd() : null;
            }
            case SaveCmd.cmdName: {
                return (command.equals(input)) ? new SaveCmd(moviesLib) : null;
            }
            case ExecuteScriptCmd.cmdName: {
                return (cmdBody.contains(".txt")) ? new ExecuteScriptCmd(cmdBody) : new ExecuteScriptCmd(cmdBody + ".txt");
            }
            case ExitCmd.cmdName: {
                return (command.equals(input)) ? new ExitCmd() : null;
            }
            case ReplaceIfGreaterCmd.cmdName: {
                if (tryParseInt(cmdBody) && Integer.parseInt(cmdBody) > -1) {
                    int K = Integer.parseInt(cmdBody);
                    if (collection.get(K) != null) {
                        Movie newMovie = readMovie();
                        return new ReplaceIfGreaterCmd(K, newMovie);
                    } else
                        throw new CommandParseException("элемента с таким ключом не существует");
                } else
                    throw new CommandParseException("ключ должен быть целым неотрицательным числом");
            }
            case HistoryCmd.cmdName: {
                return (command.equals(input)) ? new HistoryCmd() : null;
            }
            case RemoveGreaterKeyCmd.cmdName: {
                if (tryParseInt(cmdBody) && Integer.parseInt(cmdBody) > -1) {
                    int k = Integer.parseInt(cmdBody);
                    return new RemoveGreaterKeyCmd(k);
                } else
                    throw new CommandParseException("ключ должен быть целым неотрицательным числом");
            }
            case RemoveLowerKeyCmd.cmdName: {
                if (tryParseInt(cmdBody) && Integer.parseInt(cmdBody) > 0) {
                    int k = Integer.parseInt(cmdBody);
                    return new RemoveLowerKeyCmd(k);
                } else
                    throw new CommandParseException("ключ должен быть целым положительным числом");
            }
            case MinByUsaBoxOfficeCmd.cmdName: {
                return (command.equals(input)) ? new MinByUsaBoxOfficeCmd() : null;
            }
            case CountByUsaBoxOfficeCmd.cmdName: {
                if (tryParseFloat(cmdBody) && Float.parseFloat(cmdBody) > 0) {
                    float amount = Float.parseFloat(cmdBody);
                    return new CountByUsaBoxOfficeCmd(amount);
                } else
                    throw new CommandParseException("USA box office должен быть неотрицательным числом");
            }
            case CountGreaterThanUsaBoxOfficeCmd.cmdName: {
                if (tryParseFloat(cmdBody) && Float.parseFloat(cmdBody) > 0) {
                    float amount = Float.parseFloat(cmdBody);
                    return new CountGreaterThanUsaBoxOfficeCmd(amount);
                } else
                    throw new CommandParseException("USA box office должен быть неотрицательным числом");
            }
            default: {
                return null;
            }
        }
    }

    private static Movie readMovie() {
        Scanner scanner = new Scanner(System.in);
        String inp, title = "";
        int coordX = 0, coordY = 0, oscarCnt = 0;
        Float usaBoxOffice = -1f;
        boolean flag = false;
        MovieGenre genre = null;
        MpaaRating mpaaRating = null;
        Coordinates coordinates;
        Person screenwriter;

        System.out.println("Ввод нового фильма: ");
        do {
            System.out.print("\tНазвание: ");
            inp = scanner.nextLine();
            if (inp.trim().length() > 0)
                title = inp;
            else
                System.out.println("Неправильный формат ввода");
        } while (inp.trim().length() == 0);

        System.out.println("\tВвод координат:");
        do {
            System.out.print("\t\tX: ");
            inp = scanner.nextLine();
            if (tryParseInt(inp) && Integer.parseInt(inp) < 914)
                coordX = Integer.parseInt(inp);
            else
                System.out.println("Неправильный формат ввода");
        } while (!(tryParseInt(inp) && Integer.parseInt(inp) < 914));

        do {
            System.out.print("\t\tY: ");
            inp = scanner.nextLine();
            if (tryParseInt(inp) && Integer.parseInt(inp) < 914)
                coordY = Integer.parseInt(inp);
            else
                System.out.println("Неправильный формат ввода");
        } while (!(tryParseInt(inp) && Integer.parseInt(inp) < 914));
        coordinates = new Coordinates(coordX, coordY);

        do {
            System.out.print("\tКоличество полученных оскаров: ");
            inp = scanner.nextLine();
            if (tryParseInt(inp) && Integer.parseInt(inp) > 0) {
                oscarCnt = Integer.parseInt(inp);
                break;
            } else
                System.out.println("Неправильный формат ввода");
        } while (true);


        do {
            System.out.print("\tКассовые сборы в США: ");
            inp = scanner.nextLine();
            if (tryParseFloat(inp) && Float.parseFloat(inp) > 0)
                usaBoxOffice = Float.parseFloat(inp);
            else
                System.out.println("Ошибка: кассовые сборы должны быть >0. Повторите ввод");
        } while (usaBoxOffice == -1);

        System.out.println("\tВвести жанр? (y/!y)");
        if (scanner.nextLine().trim().equals("y")) {
            System.out.println("\tВввод жанра: ");
            System.out.print("\t\tДоступные жанры:\n\t\t\t { ");
            for (MovieGenre entry : MovieGenre.values())
                System.out.print(entry + " ");
            System.out.println("}");
            flag = false;
            do {
                System.out.print("\t\tЖанр: ");
                inp = scanner.nextLine();
                for (MovieGenre gen : MovieGenre.values())
                    if (inp.toUpperCase().equals(gen.toString())) {
                        flag = true;
                        genre = MovieGenre.valueOf(inp.toUpperCase());
                        break;
                    }
                if (!flag)
                    System.out.println("Неправильный формат ввода");
            } while (!flag);
        }

        System.out.println("\tВвести MPAA-рейтинг? (y/!y)");
        if (scanner.nextLine().trim().equals("y")) {
            System.out.println("\tВввод MPAA-рейтинга: ");
            System.out.print("\t\tДоступные рейтинги:\n\t\t\t { ");
            for (MpaaRating entry : MpaaRating.values())
                System.out.print(entry + " ");
            System.out.println("}");
            flag = false;
            do {
                System.out.print("\t\tMPAA-рейтинг: ");
                inp = scanner.nextLine();
                for (MpaaRating rating : MpaaRating.values())
                    if (inp.toUpperCase().equals(rating.toString())) {
                        flag = true;
                        mpaaRating = MpaaRating.valueOf(inp.toUpperCase());
                        break;
                    }
                if (!flag)
                    System.out.println("Неправильный формат ввода");
            } while (!flag);
        }
        screenwriter = readPerson();
        return new Movie(title, coordinates, oscarCnt, usaBoxOffice, genre, mpaaRating, screenwriter);
    }

    private static Person readPerson() {
        Scanner scanner = new Scanner(System.in);
        String inp, name = "", locationName = "";
        boolean flag = false;
        long locX = 0L;
        Long locY = 0L, locZ = 0L;
        Country country = null;
        Location location = null;
        java.time.LocalDate birthday = null;

        //Name
        System.out.println("\tВвод сценариста: ");
        do {
            System.out.print("\t\tИмя: ");
            inp = scanner.nextLine();
            if (!inp.isEmpty())
                name = inp;
            else
                System.out.println("Ошибка: стркоа не может быть пустой. Повторите ввод");
        } while (inp.isEmpty());

        //Birthday
        System.out.println("\t\tВвести дату рождения? (y/!y)");
        if (scanner.nextLine().trim().toLowerCase().equals("y"))
            do {
                System.out.print("\t\tДата рождения (в формате DD-MM-YYYY): ");
                inp = scanner.nextLine();
                int[] nums;
                if (inp.matches("\\d\\d[-]\\d\\d[-]\\d{4}")) {
                    nums = Arrays.stream(inp.split("-")).mapToInt(Integer::parseInt).toArray();
                    if (nums[1] < 13 && nums[0] < YearMonth.of(nums[2], nums[1]).lengthOfMonth())
                        birthday = LocalDate.of(nums[2], nums[1], nums[0]);
                    else
                        System.out.println("Неправильный формат ввода");
                } else
                    System.out.println("Неправильный формат ввода даты");
            } while (birthday == null);

        //Country
        System.out.println("\t\tВввод страны: ");
        System.out.print("\t\t\tДоступные страны:\n\t\t\t { ");
        for (Country entry : Country.values())
            System.out.print(entry + " ");
        System.out.println("}");
        do {
            System.out.print("\t\t\tСтрана: ");
            inp = scanner.nextLine();
            for (Country entry : Country.values())
                if (inp.toUpperCase().equals(entry.toString())) {
                    flag = true;
                    country = Country.valueOf(inp.toUpperCase());
                    break;
                }
            if (!flag)
                System.out.println("Ошибка: введена несуществующая страна. Повторите ввод");
        } while (!flag);

        //Location
        System.out.println("\t\tВвод локации:");
        System.out.println("\t\t\tВвод координат:");
        do {
            System.out.print("\t\t\t\tX: ");
            inp = scanner.nextLine();
            if (tryParseLong(inp))
                locX = Long.parseLong(inp);
            else
                System.out.println("Неправильный формат ввода");
        } while (!tryParseLong(inp));

        do {
            System.out.print("\t\t\t\tY: ");
            inp = scanner.nextLine();
            if (tryParseLong(inp))
                locY = Long.parseLong(inp);
            else
                System.out.println("Неправильный формат ввода");
        } while (!tryParseLong(inp));

        do {
            System.out.print("\t\t\t\tZ: ");
            inp = scanner.nextLine();
            if (tryParseLong(inp))
                locZ = Long.parseLong(inp);
            else
                System.out.println("Неправильный формат ввода");
        } while (!tryParseLong(inp));

        System.out.println("\t\t\tВвод названия локации: ");
        do {
            System.out.print("\t\t\t\tНазвание: ");
            inp = scanner.nextLine();
            if (!inp.isEmpty() && inp.length() < 578)
                locationName = inp;
            else
                System.out.println("Неправильный формат ввода");
        } while (inp.isEmpty() || inp.length() > 577);
        location = new Location(locX, locY, locZ, locationName);

        return new Person(name, birthday, country, location);
    }
}