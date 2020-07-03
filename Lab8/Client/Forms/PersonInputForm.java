package Forms;

import Enums.Country;
import com.company.Localizator;
import com.company.Location;
import com.company.Main;
import com.company.Person;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Arrays;

public class PersonInputForm extends Application {
    private Stage primaryStage;
    private GridPane mainGrid = new GridPane();
    private GridPane locGrid = new GridPane();
    private GridPane coordGrid = new GridPane();
    private HBox btnHBox = new HBox(10);

    private Scene primaryScene = new Scene(mainGrid);
    private Label nameLbl = new Label(Localizator.nameLbl.get());
    private Label dateLbl = new Label(Localizator.birthdayLbl.get());
    private Label countryLbl = new Label(Localizator.nativeCountryLbl.get());
    private Label locLbl = new Label(Localizator.locationLbl.get());
    private Label locNameLbl = new Label(Localizator.nameLbl.get());
    private Label coordinatesLbl = new Label(Localizator.coordinatesLbl.get());
    private Label locXLbl = new Label("X");
    private Label locYLbl = new Label("Y");
    private Label locZLbl = new Label("Z");

    private TextField nameTBox = new TextField();
    private TextField locNameTBox = new TextField();
    private TextField locXTBox = new TextField();
    private TextField locYTBox = new TextField();
    private TextField locZTBox = new TextField();

    private ComboBox<String> countryCBox = new ComboBox<>();

    private DatePicker datePick = new DatePicker();
    private Button okBtn = new Button("OK");
    private Button clearBtn = new Button(Localizator.clearBtn.get());

    private Line line = new Line(0, 0, 290, 0);
    private Line line1 = new Line(0, 0, 290, 0);

    public PersonInputForm() {
        new PersonInputForm(null);
    }

    public PersonInputForm(Person oldPerson) {
        if (oldPerson != null) {
            nameTBox.setText(oldPerson.getName());
            datePick.setValue(oldPerson.getBirthday());
            countryCBox.setValue(oldPerson.getNationality().getDescription());
            locNameTBox.setText(oldPerson.getLocation().getName());
            locXTBox.setText(Long.toString(oldPerson.getLocation().getX()));
            locYTBox.setText(Long.toString(oldPerson.getLocation().getY()));
            locZTBox.setText(Long.toString(oldPerson.getLocation().getZ()));
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        setListeners();
        fillUpCBoxes();
        setVisualDesign();
        fillUpGrid();
        showStage();
    }

    private void setListeners() {
        setStyleDropper(nameTBox, countryCBox, locXTBox, locYTBox, locZTBox, locNameTBox, datePick);

        primaryStage.setOnCloseRequest(event -> {
            try {
                this.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        clearBtn.setOnAction(event -> {
            clearForm();
        });

        okBtn.setOnAction(event -> {
            if (checkFields()) {
                Country country = Country.getByStr(countryCBox.getValue());
                Location loc = new Location(Long.parseLong(locXTBox.getText()), Long.parseLong(locYTBox.getText()),
                        Long.parseLong(locZTBox.getText()), locNameTBox.getText());
                Person screenwriter = new Person(nameTBox.getText(), datePick.getValue(), country, loc);
                Main.mainFormController.movieInpFormController.setPerson(screenwriter);
                try {
                    primaryStage.close();
                    this.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        locXTBox.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,3}?")) {
                locXTBox.setText(oldValue);
            }
        });

        locYTBox.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,3}?")) {
                locYTBox.setText(oldValue);
            }
        });

        locZTBox.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,3}?")) {
                locZTBox.setText(oldValue);
            }
        });
    }

    private void setStyleDropper(Object... o) {
        for (Object obj : o) {
            if (obj instanceof TextField) {
                TextField temp = (TextField) obj;
                temp.textProperty().addListener((observable, oldValue, newValue) -> temp.setStyle(null));
            }
            if (obj instanceof DatePicker) {
                DatePicker temp = (DatePicker) obj;
                temp.valueProperty().addListener((observable, oldValue, newValue) -> temp.setStyle(null));
            }
            if (obj instanceof ComboBox<?>) {
                ComboBox<?> temp = (ComboBox<?>) obj;
                temp.valueProperty().addListener((observable, oldValue, newValue) -> temp.setStyle(null));
            }
        }
    }

    private boolean checkFields() {
        boolean flag = true;
        if (nameTBox.getText().isEmpty()) {
            nameTBox.setStyle("-fx-border-color: red;");
            flag = false;
        }
        if (countryCBox.getValue() == null) {
            countryCBox.setStyle("-fx-border-color: red;");
            flag = false;
        }
        if (locXTBox.getText().isEmpty()) {
            locXTBox.setStyle("-fx-border-color: red;");
            flag = false;
        }
        if (locYTBox.getText().isEmpty()) {
            locYTBox.setStyle("-fx-border-color: red;");
            flag = false;
        }
        if (locZTBox.getText().isEmpty()) {
            locZTBox.setStyle("-fx-border-color: red;");
            flag = false;
        }
        if (locNameTBox.getText().isEmpty()) {
            locNameTBox.setStyle("-fx-border-color: red;");
            flag = false;
        }
        if (datePick.getValue() == null) {
            datePick.setStyle("-fx-border-color: red;");
            flag = false;
        }
        return flag;
    }

    private void clearForm() {
        nameTBox.clear();
        datePick.setValue(null);
        countryCBox.setValue(null);
        locNameTBox.clear();
        locXTBox.clear();
        locYTBox.clear();
        locZTBox.clear();
    }

    private void setVisualDesign() {
        mainGrid.setPadding(new Insets(15));
        mainGrid.setVgap(10);
        mainGrid.setHgap(10);

        locGrid.setVgap(10);
        locGrid.setHgap(10);
        locGrid.setPadding(new Insets(15));

        coordGrid.setHgap(10);

        line.setStroke(Color.GRAY);
        line1.setStroke(Color.GRAY);

        coordGrid.setPrefWidth(200);
        datePick.setPrefWidth(200);
        countryCBox.setPrefWidth(200);
        okBtn.setPrefWidth(95);
        clearBtn.setPrefWidth(95);
    }

    private void fillUpGrid() {
        btnHBox.getChildren().addAll(clearBtn, okBtn);

        coordGrid.add(locXLbl, 0, 0);
        coordGrid.add(locXTBox, 1, 0);
        coordGrid.add(locYLbl, 2, 0);
        coordGrid.add(locYTBox, 3, 0);
        coordGrid.add(locZLbl, 4, 0);
        coordGrid.add(locZTBox, 5, 0);

        mainGrid.add(nameLbl, 0, 0);
        mainGrid.add(nameTBox, 1, 0);
        mainGrid.add(dateLbl, 0, 1);
        mainGrid.add(datePick, 1, 1);
        mainGrid.add(countryLbl, 0, 2);
        mainGrid.add(countryCBox, 1, 2);
        mainGrid.add(line, 0, 3, 2, 1);
        mainGrid.add(locLbl, 0, 4);
        mainGrid.add(locNameLbl, 0, 5);
        mainGrid.add(locNameTBox, 1, 5);
        mainGrid.add(coordinatesLbl, 0, 6);
        mainGrid.add(coordGrid, 1, 6);
        mainGrid.add(line1, 0, 7, 2, 1);
        mainGrid.add(btnHBox, 1, 8);
    }

    private void fillUpCBoxes() {
        ObservableList<String> countryList = FXCollections.observableArrayList();
        Arrays.stream(Country.values()).forEach(country -> countryList.add(country.getDescription()));
        countryCBox.setItems(countryList);
    }

    private void showStage() {
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle(Localizator.screenwriterFormTitle.get());
        primaryStage.setResizable(false);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
}
