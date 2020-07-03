package Forms;

import Commands.RegistrationCmd;
import ThreadTasks.ReaderTask;
import ThreadTasks.SendCmdTask;
import com.company.Client;
import com.company.Localizator;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

import static com.company.Starter.*;

public class RegistrationForm extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(Localizator.registrationFormTitle.get());

        GridPane grid = new GridPane();
        Scene primaryScene = new Scene(grid);
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField logTbox = new TextField();
        logTbox.setPrefWidth(150);
        PasswordField pasTbox1 = new PasswordField();
        pasTbox1.setPrefWidth(150);
        PasswordField pasTbox2 = new PasswordField();
        pasTbox2.setPrefWidth(150);

        Button okBtn = new Button("OK");
        okBtn.setDisable(true);
        okBtn.setPrefWidth(logTbox.getPrefWidth());

        grid.add(new Label(Localizator.loginLbl.get()), 0, 0);
        grid.add(new Label(Localizator.passLbl.get()), 0, 1);
        grid.add(new Label(Localizator.repeatPassLbl.get()), 0, 2);
        grid.add(logTbox, 1, 0);
        grid.add(pasTbox1, 1, 1);
        grid.add(pasTbox2, 1, 2);
        grid.add(okBtn, 1, 3);

        logTbox.textProperty().addListener(event -> {
            if (!pasTbox1.getText().isEmpty() && !pasTbox2.getText().isEmpty() && !logTbox.getText().isEmpty())
                okBtn.setDisable(false);
            else
                okBtn.setDisable(true);
        });

        pasTbox1.textProperty().addListener(event -> {
            if (!pasTbox1.getText().isEmpty() && !pasTbox2.getText().isEmpty() && !logTbox.getText().isEmpty())
                okBtn.setDisable(false);
            else
                okBtn.setDisable(true);
        });

        pasTbox2.textProperty().addListener(event -> {
            if (!pasTbox1.getText().isEmpty() && !pasTbox2.getText().isEmpty() && !logTbox.getText().isEmpty())
                okBtn.setDisable(false);
            else
                okBtn.setDisable(true);
        });

        okBtn.setOnAction(event -> {
            if (pasTbox1.getText().equals(pasTbox2.getText())) {
                RegistrationCmd cmd = new RegistrationCmd(curClient, new Client(logTbox.getText(), pasTbox1.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                SendCmdTask.addCmd(cmd);

                ArrayList<String> out = ReaderTask.getOutput();
                if (out.get(0).equals("yes")) {
                    curClient.logIn(new Client(logTbox.getText(), pasTbox1.getText()));
                    primaryStage.close();
                    try {
                        this.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    alert.setAlertType(Alert.AlertType.WARNING);
                }
                alert.setContentText(out.get(1));
                alert.setHeaderText(null);
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, Localizator.passDoesNotMatchAlert.get());
                alert.setHeaderText(null);
                alert.show();
            }
        });

        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setScene(primaryScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}