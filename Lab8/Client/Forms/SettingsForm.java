package Forms;

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
import java.util.List;
import java.util.stream.Collectors;

public class SettingsForm extends Application {
    private GridPane mainGrid = new GridPane();
    private HBox hostHBox = new HBox(10);
    private HBox btnHBox = new HBox(10);

    private Label hostLbl = new Label(Localizator.hostLbl.get());
    private Label portLbl = new Label(Localizator.portLbl.get());

    private TextField hostTBox = new TextField();
    private TextField portTBox = new TextField();

    private CheckBox connectOnLaunchChBox = new CheckBox(Localizator.connectOnLaunchChBox.get());

    private Button saveBtn = new Button(Localizator.saveBtn.get());
    private Button cancelBtn = new Button(Localizator.cancelBtn.get());

    private Scene primaryScene = new Scene(mainGrid);

    @Override
    public void start(Stage primaryStage) throws Exception {
        readSettings();
        setListeners(primaryStage);
        setBindings();
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

    private void saveSettings() {
        if (System.getenv("TEST") != null) {
            String path = System.getenv("TEST");
            try {
                ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(path + "/Connection.txt"), StandardCharsets.UTF_8));
                String[] linesStr = new String[lines.size()];
                lines.toArray(linesStr);
                linesStr[0] = ("autoHost=" + hostTBox.getText());
                linesStr[1] = ("autoPort=" + portTBox.getText());
                linesStr[2] = ("auto_connect=" + connectOnLaunchChBox.isSelected());
                Files.write(Paths.get(path + "/Connection.txt"), Arrays.asList(linesStr));
            } catch (IOException e) {
                System.out.println("Нет файла настроек");
            }
        }
    }

    private void parseSettings(ArrayList<String> lines) {
        String host = lines.get(0).substring(lines.get(0).indexOf('=') + 1);
        String port = lines.get(1).substring(lines.get(1).indexOf('=') + 1);
        boolean auto = Boolean.parseBoolean(lines.get(2).substring(lines.get(2).indexOf('=') + 1));

        if (!host.isEmpty()) {
            hostTBox.setText(host);
        }
        if (!port.isEmpty()) {
            portTBox.setText(port);
        }
        if (auto) {
            connectOnLaunchChBox.setSelected(true);
        }
    }

    private void setListeners(Stage primaryStage) {
        portTBox.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,5}?")) {
                portTBox.setText(oldValue);
            }
        });

        saveBtn.setOnAction(event -> saveSettings());

        cancelBtn.setOnAction(event -> {
            try {
                this.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            primaryStage.close();
        });
    }

    private void setBindings() {
        hostHBox.disableProperty().bind(connectOnLaunchChBox.selectedProperty().not());
    }

    private void setVisualDesign() {
        mainGrid.setVgap(10);
        mainGrid.setHgap(10);
        mainGrid.setPadding(new Insets(10));
        portTBox.setPrefWidth(50);

        saveBtn.setPrefWidth(80);
        cancelBtn.setPrefWidth(80);
        GridPane.setHalignment(btnHBox, HPos.RIGHT);
    }

    private void fillGrid() {
        hostHBox.getChildren().addAll(hostLbl, hostTBox, portLbl, portTBox);
        btnHBox.getChildren().addAll(saveBtn, cancelBtn);
        mainGrid.add(connectOnLaunchChBox, 0, 0);
        mainGrid.add(hostHBox, 0, 1);
        mainGrid.add(btnHBox, 0, 2);
    }

    private void showStage(Stage primaryStage) {
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle(Localizator.settingsFormTitle.get());
        primaryStage.setResizable(false);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
}