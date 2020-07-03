package Forms;

import ThreadTasks.ReaderTask;
import ThreadTasks.SendCmdTask;
import com.company.Localizator;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ConnectionForm extends Application {
    private GridPane mainGrid = new GridPane();
    private HBox btnHBox = new HBox(10);

    private Label hostLbl = new Label(Localizator.hostLbl.get());
    private Label portLbl = new Label(Localizator.portLbl.get());

    private TextField hostTBox = new TextField();
    private TextField portTBox = new TextField();

    private CheckBox rememberCheBox = new CheckBox(Localizator.rememberChBox.get());

    private Button okBtn = new Button("OK");
    private Button cancelBtn = new Button(Localizator.cancelBtn.get());

    private Socket clientSocket = null; //сокет для общения

    private Scene primaryScene = new Scene(mainGrid);

    @Override
    public void start(Stage primaryStage) throws Exception {
        readSettings();
        setListeners(primaryStage);
        fillGrid();
        setVisualDesign();
        showStage(primaryStage);
    }

    private void readSettings() {
        ArrayList<String> lines;
        if (System.getenv("TEST") != null) {
            String path = System.getenv("TEST");
            try {
                lines = new ArrayList<>(Files.readAllLines(Paths.get(path + "/Connection.txt"), StandardCharsets.UTF_8));
                parseSettings(lines);
            } catch (IOException e) {
                System.out.println("Нет файла настроек");
            }
        }
    }

    private void parseSettings(ArrayList<String> lines) {
        boolean auto = Boolean.parseBoolean(lines.get(5).substring(lines.get(5).indexOf('=') + 1));

        if (auto) {
            String host = lines.get(3).substring(lines.get(3).indexOf('=') + 1);
            String port = lines.get(4).substring(lines.get(4).indexOf('=') + 1);
            hostTBox.setText(host);
            portTBox.setText(port);
            rememberCheBox.setSelected(true);
        }
    }

    private void saveSettings() {
        if (System.getenv("TEST") != null) {
            String path = System.getenv("TEST");
            try {
                ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(path + "/Connection.txt"), StandardCharsets.UTF_8));
                String[] linesStr = new String[lines.size()];
                lines.toArray(linesStr);
                linesStr[3] = ("manualHost=" + hostTBox.getText());
                linesStr[4] = ("manualPort=" + portTBox.getText());
                linesStr[5] = ("remember=" + rememberCheBox.isSelected());
                Files.write(Paths.get(path + "/Connection.txt"), Arrays.asList(linesStr));
            } catch (IOException e) {
                System.out.println("Нет файла настроек");
            }
        }
    }

    private void setListeners(Stage primaryStage) {
        portTBox.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,5}?")) {
                portTBox.setText(oldValue);
            }
        });

        okBtn.setOnAction(event -> {
            if (!hostTBox.getText().isEmpty() && !portTBox.getText().isEmpty()) {
                connect(hostTBox.getText(), Integer.parseInt(portTBox.getText()));
                try {
                    this.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                primaryStage.close();
            }
            if (rememberCheBox.isSelected()) {
                saveSettings();
            }
        });

        cancelBtn.setOnAction(event -> {
            try {
                this.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            primaryStage.close();
        });
    }

    private void setVisualDesign() {
        mainGrid.setVgap(10);
        mainGrid.setHgap(10);
        mainGrid.setPadding(new Insets(10));
        portTBox.setPrefWidth(60);

        okBtn.setPrefWidth(80);
        cancelBtn.setPrefWidth(80);
        GridPane.setHalignment(rememberCheBox, HPos.RIGHT);
        GridPane.setHalignment(btnHBox, HPos.RIGHT);
    }

    private void fillGrid() {
        btnHBox.getChildren().addAll(okBtn, cancelBtn);

        mainGrid.add(hostLbl, 0, 0);
        mainGrid.add(hostTBox, 1, 0);
        mainGrid.add(portLbl, 2, 0);
        mainGrid.add(portTBox, 3, 0);
        mainGrid.add(rememberCheBox, 0, 1, 4, 1);
        mainGrid.add(btnHBox, 0, 2, 4, 1);
    }

    private void showStage(Stage primaryStage) {
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle(Localizator.connectionFormTitle.get());
        primaryStage.setResizable(false);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    private void connect(String ip, int port) {
        boolean flag = false;
        do {
            try {
                System.out.println("Ждём подключения..");
                do {
                    try {
                        clientSocket = new Socket(ip, port); //конектимся
                    } catch (IOException e) {
                        clientSocket = null;
                        Thread.sleep(1000);
                    }
                } while (clientSocket == null);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(Localizator.connectionAlert.get() + clientSocket.getInetAddress().getHostAddress());
                alert.setHeaderText(null);
                alert.showAndWait();

                flag = true;
                new Thread(new ReaderTask(clientSocket)).start();
                new Thread(new SendCmdTask(clientSocket)).start();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        } while (!flag);
    }
}