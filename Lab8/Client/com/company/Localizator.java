package com.company;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.Arrays;

public class Localizator {
    public static int curLanguageId = 0;
    public static SimpleIntegerProperty curLanguageIdd = new SimpleIntegerProperty(0);

    public static void setCurLanguageId(int id) {
        curLanguageId = Math.max(id, 0);
        curLanguageIdd.set(Math.max(id, 0));
    }

    public static class Recourse {
        private ArrayList<String> translations = new ArrayList<>();

        public Recourse(String... translations) {
            this.translations.addAll(Arrays.asList(translations));
        }

        public String get() {
            if (curLanguageId <= translations.size())
                return translations.get(curLanguageId);
            else
                return translations.get(0);
        }
    }

    //MainForm----------------------------------------------------------------------------------------------------------
    public static Recourse cmdInputLbl = new Recourse("Ввод команды:", "Command input:");
    public static Recourse cmdOverviewLbl = new Recourse("Предпросмотр команды:", "Command overview:");
    public static Recourse allDbLbl = new Recourse("Вся бд:", "All db:");
    public static Recourse serverAnswersLbl = new Recourse("Ответы сервера:", "Server's answers:");
    public static Recourse boxOfficeLbl = new Recourse("Кассовые сборы:", "Box office:");
    public static Recourse fileNameLbl = new Recourse("Имя файла:", "File name:");

    public static Recourse addMovieBtn = new Recourse("Ввести фильм", "Add movie");
    public static Recourse sendCmdBtn = new Recourse("Отправить команду", "Send command");
    public static Recourse loginBtn = new Recourse("Вход", "Login");
    public static Recourse registrationBtn = new Recourse("Регистрация", "Registration");
    public static Recourse saveChangesBtn = new Recourse("Сохранить\nизменения", "Save\nchanges");
    public static Recourse clearBtn = new Recourse("Очистить", "Clear");
    public static Recourse deleteBtn = new Recourse("Удалить", "Delete");

    public static Recourse exitMenuItem = new Recourse("Выход", "Exit");

    public static Recourse mainFormTitle = new Recourse("Главная форма", "Main form");

    public static Recourse BoxOfficeCmdParseException = new Recourse("значение USA box office должно быть больше 0",
            "USA box office value must be equals or higher than 0");
    public static Recourse fileNameCmdParseException = new Recourse("не введено имя файла", "All db:");
    public static Recourse movieCmdParseException = new Recourse("не введен фильм", "All db:");
    public static Recourse idCmdParseException = new Recourse("не введено значение ID", "All db:");
    public static Recourse argumentsCmdParseException = new Recourse("введены не все аргументы", "All db:");
    //------------------------------------------------------------------------------------------------------------------

    //MovieForm---------------------------------------------------------------------------------------------------------
    public static Recourse titleLbl = new Recourse("Название", "Title");
    public static Recourse creationDateLbl = new Recourse("Дата создания", "Creation date");
    public static Recourse coordinatesLbl = new Recourse("Координаты", "Coordinates");
    public static Recourse oscarsLbl = new Recourse("Оскары", "Oscars");
    public static Recourse screenwriterLbl = new Recourse("Сценарист", "Screenwriter");
    public static Recourse genreLbl = new Recourse("Жанр", "Genre");
    public static Recourse mpaaRatingLbl = new Recourse("MPAA рейтинг", "MPAA rating");

    public static Recourse addPersonBtn = new Recourse("Ввести", "Add");
    public static Recourse changeBtn = new Recourse("Изменить", "Change");
    public static Recourse cancelBtn = new Recourse("Отмена", "Cancel");

    public static Recourse movieFormTitle = new Recourse("Ввод фильма", "Movie input");
    //------------------------------------------------------------------------------------------------------------------

    //ScreenwriterForm--------------------------------------------------------------------------------------------------
    public static Recourse nameLbl = new Recourse("Имя", "Name");
    public static Recourse birthdayLbl = new Recourse("Дата рождения", "Birthday");
    public static Recourse nativeCountryLbl = new Recourse("Родная страна", "Native country");
    public static Recourse locationLbl = new Recourse("Локация:", "Location:");

    public static Recourse screenwriterFormTitle = new Recourse("Ввод сценариста", "Screenwriter input");
    //------------------------------------------------------------------------------------------------------------------

    //LoginForm---------------------------------------------------------------------------------------------------------
    public static Recourse loginLbl = new Recourse("Логин", "Login");
    public static Recourse passLbl = new Recourse("Пароль", "Password");

    public static Recourse loginFormTitle = new Recourse("Вход", "Login");
    //------------------------------------------------------------------------------------------------------------------

    //RegistrationForm--------------------------------------------------------------------------------------------------
    public static Recourse repeatPassLbl = new Recourse("Повторите пароль", "Repeat pass");

    public static Recourse registrationFormTitle = new Recourse("Регистрация", "Registration");
    public static Recourse passDoesNotMatchAlert = new Recourse("Пароли не совпадают", "Passwords does not match");
    //------------------------------------------------------------------------------------------------------------------


    //ConnectionForm----------------------------------------------------------------------------------------------------
    public static Recourse rememberChBox = new Recourse("Запомнить", "Remember");

    public static Recourse hostLbl = new Recourse("Хост", "Host");
    public static Recourse portLbl = new Recourse("Порт", "Port");

    public static Recourse connectionFormTitle = new Recourse("Подключение", "Connection");

    public static Recourse connectionAlert = new Recourse("Подключились к серверу ", "Connected to server ");
    //------------------------------------------------------------------------------------------------------------------

    //SettingsForm------------------------------------------------------------------------------------------------------
    public static Recourse connectOnLaunchChBox = new Recourse("Подключатсья при запуске", "Connect on launch");

    public static Recourse saveBtn = new Recourse("Сохранить", "Save");

    public static Recourse settingsFormTitle = new Recourse("Настройки подключения", "Connection settings");
    //------------------------------------------------------------------------------------------------------------------

    //Chart-------------------------------------------------------------------------------------------------------------
    public static Recourse chartTitle = new Recourse("Просмотр фильмов", "Movies overview");
    //------------------------------------------------------------------------------------------------------------------

    //Menubar-----------------------------------------------------------------------------------------------------------
    public static Recourse settingsMenu = new Recourse("Настройки", "Settings");
    public static Recourse connectionMenu = new Recourse("Подключение", "Connection");
    public static Recourse connectMenu = new Recourse("Подключиться", "Connect");
    public static Recourse helpMenu = new Recourse("Инфо", "Info");
    public static Recourse aboutCollectionMenu = new Recourse("О коллекции", "About collection");
    public static Recourse historyMenu = new Recourse("История комманд", "Command history");
    //------------------------------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------------------------
    public static Recourse historyList = new Recourse("История комманд:", "Command history:");
    public static Recourse historyIsEmpty = new Recourse("История команд пуста", "Command history is empty");
    //------------------------------------------------------------------------------------------------------------------

    //Filter------------------------------------------------------------------------------------------------------------
    public static Recourse filterLbl = new Recourse("Фильтр", "Filter");
    public static Recourse valueLbl = new Recourse("Значение", "Value");
    public static Recourse byColumnChBox = new Recourse("По столбцу", "By column");
    //------------------------------------------------------------------------------------------------------------------

    //Table-------------------------------------------------------------------------------------------------------------
    public static Recourse titleCol = new Recourse("Название", "Title");
    public static Recourse coordinatesCol = new Recourse("Координаты", "Coordinates");
    public static Recourse creationDateCol = new Recourse("Дата создания", "Creation date");
    public static Recourse oscarsCol = new Recourse("Оскары", "Oscars");
    public static Recourse boxOfficeCol = new Recourse("Кассовые сборы", "USA box\noffice");
    public static Recourse genreCol = new Recourse("Жанр", "Genre");
    public static Recourse ratingCol = new Recourse("MPAA\nрейтинг", "MPAA\nrating");
    public static Recourse screenwriterCol = new Recourse("Сценарист", "Screenwriter");
    public static Recourse personNameCol = new Recourse("Имя", "Name");
    public static Recourse birthdayCol = new Recourse("Дата рождения", "Birthday");
    public static Recourse nativeCountryCol = new Recourse("Страна", "Native\ncountry");
    public static Recourse locationCol = new Recourse("Локация", "Location");
    public static Recourse locNameCol = new Recourse("Название", "Name");
    public static Recourse ownerCol = new Recourse("Владелец", "Owner");
    //------------------------------------------------------------------------------------------------------------------
}