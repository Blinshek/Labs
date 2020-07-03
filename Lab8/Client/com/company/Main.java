package com.company;

import Forms.MainForm;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static MainForm mainFormController;

    public static void main(String[] args) throws Exception {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) {
        new Thread(Starter::start).start();
        primaryStage.close();
        mainFormController = new MainForm();
        mainFormController.start(new Stage());
    }
}