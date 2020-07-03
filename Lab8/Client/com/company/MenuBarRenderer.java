package com.company;

import Commands.InfoCmd;
import Enums.Command;
import Forms.ConnectionForm;
import Forms.SettingsForm;
import ThreadTasks.ReaderTask;
import ThreadTasks.SendCmdTask;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MenuBarRenderer {
    private static MenuBar menuBar = new MenuBar();
    private static Menu connectMenu = new Menu(Localizator.connectionMenu.get());
    private static Menu infoMenu = new Menu(Localizator.helpMenu.get());

    private static MenuItem connectMenuItem = new MenuItem(Localizator.connectMenu.get());
    private static SeparatorMenuItem separator = new SeparatorMenuItem();
    private static MenuItem connectionSettingsMenuItem = new MenuItem(Localizator.settingsMenu.get());

    private static MenuItem infoMenuItem = new MenuItem(Localizator.aboutCollectionMenu.get());
    private static MenuItem historyMenuItem = new MenuItem(Localizator.historyMenu.get());

    public static MenuBar render() {
        addItems();
        setListeners();
        setText();
        return menuBar;
    }

    private static void setListeners() {
        connectMenuItem.setOnAction(event -> {
            ConnectionForm frm = new ConnectionForm();
            try {
                frm.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        connectionSettingsMenuItem.setOnAction(event -> {
            SettingsForm frm = new SettingsForm();
            try {
                frm.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        infoMenuItem.setOnAction(event -> {
            SendCmdTask.addCmd(new InfoCmd());
            ArrayList<String> out = ReaderTask.getOutput();
            if (out.get(0).equals("yes")) {
                out.remove(0);
                Main.mainFormController.setAnswer(out);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText(out.get(1));
                alert.setHeaderText(null);
                alert.show();
            }
        });

        historyMenuItem.setOnAction(event -> {
            Main.mainFormController.setAnswer(CommandHistory.getHistory());
        });
    }

    private static void setText() {
        Localizator.curLanguageIdd.addListener((observable, oldValue, newValue) -> {
            connectMenu.setText(Localizator.connectionMenu.get());
            infoMenu.setText(Localizator.helpMenu.get());
            connectMenuItem.setText(Localizator.connectMenu.get());
            connectionSettingsMenuItem.setText(Localizator.settingsMenu.get());
        });
    }

    private static void addItems() {
        menuBar.getMenus().addAll(connectMenu, infoMenu);
        connectMenu.getItems().addAll(connectMenuItem, separator, connectionSettingsMenuItem);
        infoMenu.getItems().addAll(historyMenuItem, infoMenuItem);
    }
}
