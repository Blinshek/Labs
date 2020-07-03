package Forms;

import Commands.LoginCmd;
import ThreadTasks.ReaderTask;
import ThreadTasks.SendCmdTask;
import com.company.Client;
import com.company.Localizator;
import com.company.Main;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

import static com.company.Starter.*;

public class LoginForm extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(Localizator.loginFormTitle.get());

        GridPane grid = new GridPane();
        Scene primaryScene = new Scene(grid);
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField logTbox = new TextField();
        logTbox.setPrefWidth(150);
        PasswordField pasTbox = new PasswordField();
        pasTbox.setPrefWidth(150);

        Button okBtn = new Button("OK");
        okBtn.setPrefWidth(logTbox.getPrefWidth());
        okBtn.setDisable(true);

        //listeners--------------------------------------------
        logTbox.textProperty().addListener(event -> {
            if (!pasTbox.getText().isEmpty() && !logTbox.getText().isEmpty())
                okBtn.setDisable(false);
            else
                okBtn.setDisable(true);
        });

        pasTbox.textProperty().addListener(event -> {
            if (!pasTbox.getText().isEmpty() && !logTbox.getText().isEmpty())
                okBtn.setDisable(false);
            else
                okBtn.setDisable(true);
        });

        okBtn.setOnAction(event -> {
            if (!logTbox.getText().isEmpty() && !pasTbox.getText().isEmpty()) {
                Client newClient = new Client(logTbox.getText(), pasTbox.getText());
                LoginCmd loginCmd = new LoginCmd(curClient, newClient);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                SendCmdTask.addCmd(loginCmd);

                ArrayList<String> out = ReaderTask.getOutput();
                if (out.get(0).equals("yes")) {
                    curClient.logIn(newClient);
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
            }
        });
        //-----------------------------------------------------

        grid.add(new Label(Localizator.loginLbl.get()), 0, 0);
        grid.add(new Label(Localizator.passLbl.get()), 0, 1);
        grid.add(logTbox, 1, 0);
        grid.add(pasTbox, 1, 1);
        grid.add(okBtn, 1, 2);

        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setScene(primaryScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
