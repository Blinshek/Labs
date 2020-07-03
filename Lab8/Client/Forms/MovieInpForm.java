package Forms;

import Enums.*;
import com.company.*;
import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;

public class MovieInpForm extends Application {
    private GridPane mainGrid = new GridPane();
    private GridPane coordsGrid = new GridPane();
    private GridPane personGrid = new GridPane();
    private HBox btnHBox = new HBox(10);

    private Label titleLbl = new Label(Localizator.titleLbl.get());
    private Label dateLbl = new Label(Localizator.creationDateLbl.get());
    private Label coordinatesLbl = new Label(Localizator.coordinatesLbl.get());
    private Label oscarsLbl = new Label(Localizator.oscarsLbl.get());
    private Label boxOfficeLbl = new Label(Localizator.boxOfficeLbl.get());
    private Label screenWriterLbl = new Label(Localizator.screenwriterLbl.get());
    private Label genreLbl = new Label(Localizator.genreLbl.get());
    private Label ratingLbl = new Label(Localizator.mpaaRatingLbl.get());
    private Label coordXLbl = new Label("X");
    private Label coordYLbl = new Label("Y");

    private TextField titleTBox = new TextField();
    private TextField coordXTBox = new TextField();
    private TextField coordYTBox = new TextField();
    private TextField oscarsTBox = new TextField();
    private TextField boxOfficeTBox = new TextField();
    private TextField screenWriterTBox = new TextField();

    private DatePicker datePick = new DatePicker();

    private Button okBtn = new Button("OK");
    private Button personInpBtn = new Button(Localizator.addPersonBtn.get());
    private Button clearBtn = new Button(Localizator.clearBtn.get());

    private ComboBox<String> genreCBox = new ComboBox<>();
    private ComboBox<String> ratingCBox = new ComboBox<>();

    private Scene primaryScene = new Scene(mainGrid);
    private PersonInputForm pForm;

    private Movie oldMovie;

    private boolean isFromChart;

    private Person screenwriter;
    //------------------------------------------------------------------

    public MovieInpForm(Movie oldMovie) {
        this(oldMovie, false);
    }

    public MovieInpForm(Movie oldMovie, boolean isFromChart) {
        if (oldMovie != null) {
            fillUpFields(oldMovie);
            this.oldMovie = oldMovie;
        }
        this.isFromChart = isFromChart;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setListeners(primaryStage);
        fillUpBoxes();
        if (oldMovie == null)
            rndLocalDate();
        setVisualDesign();
        fillUpGrid();
        showStage(primaryStage);
    }

    private void setListeners(Stage primaryStage) {
        setStyleDropper(titleTBox, coordXTBox, coordYTBox, datePick, screenWriterTBox, oscarsTBox, boxOfficeTBox);

        personInpBtn.setOnAction(event -> {
            pForm = new PersonInputForm(screenwriter);
            try {
                pForm.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        clearBtn.setOnAction(event -> {
            clearForm();
        });

        okBtn.setOnAction(event -> {
            if (checkFields()) {
                Movie inpMovie = parseFields();
                Main.mainFormController.setLastInputMovie(inpMovie, isFromChart);
                try {
                    primaryStage.close();
                    this.stop();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        oscarsTBox.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,10}?")) {
                oscarsTBox.setText(oldValue);
            }
        });

        coordXTBox.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,8}([.]\\d{0,2})?")) {
                coordXTBox.setText(oldValue);
            }
        });

        coordYTBox.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,8}([.]\\d{0,2})?")) {
                coordXTBox.setText(oldValue);
            }
        });

        boxOfficeTBox.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,10}([.]\\d{0,2})?")) {
                boxOfficeTBox.setText(oldValue);
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
        }
    }

    private boolean checkFields() {
        boolean flag = true;
        if (titleTBox.getText().isEmpty()) {
            titleTBox.setStyle("-fx-border-color: red;");
            flag = false;
        }
        if (coordXTBox.getText().isEmpty()) {
            coordXTBox.setStyle("-fx-border-color: red;");
            flag = false;
        }
        if (coordYTBox.getText().isEmpty()) {
            coordYTBox.setStyle("-fx-border-color: red;");
            flag = false;
        }
        if (oscarsTBox.getText().isEmpty() || Integer.parseInt(oscarsTBox.getText()) == 0) {
            oscarsTBox.setStyle("-fx-border-color: red;");
            flag = false;
        }
        if (boxOfficeTBox.getText().isEmpty() || Float.parseFloat(boxOfficeTBox.getText()) == 0) {
            boxOfficeTBox.setStyle("-fx-border-color: red;");
            flag = false;
        }
        if (screenwriter == null) {
            screenWriterTBox.setStyle("-fx-border-color: red;");
            flag = false;
        }
        if (datePick.getValue() == null) {
            datePick.setStyle("-fx-border-color: red;");
            flag = false;
        }
        return flag;
    }

    private Movie parseFields() {
        Coordinates coordinates = new Coordinates(Float.parseFloat(coordXTBox.getText()), Float.parseFloat(coordYTBox.getText()));
        MpaaRating tempRating = (ratingCBox.getValue() == null) ? null : MpaaRating.valueOf(ratingCBox.getValue());
        Movie nMovie;
        if (oldMovie != null) {
            nMovie = new Movie(oldMovie.getId(), titleTBox.getText(), oldMovie.getCreationDate(), coordinates, Integer.parseInt(oscarsTBox.getText()),
                    Float.parseFloat(boxOfficeTBox.getText()), MovieGenre.getByStr(genreCBox.getValue()), tempRating, screenwriter);
        } else {
            nMovie = new Movie(-1, titleTBox.getText(), datePick.getValue(), coordinates, Integer.parseInt(oscarsTBox.getText()),
                    Float.parseFloat(boxOfficeTBox.getText()), MovieGenre.getByStr(genreCBox.getValue()), tempRating, screenwriter);
        }
        nMovie.setOwnerLogin(Starter.curClient.getLogin());
        return nMovie;
    }

    private void clearForm() {
        titleTBox.clear();
        coordXTBox.clear();
        coordYTBox.clear();
        oscarsTBox.clear();
        boxOfficeTBox.clear();
        screenWriterTBox.clear();
        screenwriter = null;
        genreCBox.setValue(null);
        ratingCBox.setValue(null);
        personInpBtn.setText(Localizator.addPersonBtn.get());
    }

    private void setVisualDesign() {
        mainGrid.setPadding(new Insets(15));
        mainGrid.setVgap(10);
        mainGrid.setHgap(10);
        coordsGrid.setHgap(10);
        personGrid.setHgap(10);

        genreCBox.setPrefWidth(200);
        personGrid.setMaxWidth(200);
        ratingCBox.setPrefWidth(200);
        coordsGrid.setPrefWidth(200);
        datePick.setPrefWidth(200);
        okBtn.setPrefWidth(95);
        clearBtn.setPrefWidth(95);
        personInpBtn.setMinWidth(70);

        screenWriterTBox.setEditable(false);
    }

    private void fillUpGrid() {
        btnHBox.getChildren().addAll(clearBtn, okBtn);
        personGrid.add(screenWriterTBox, 0, 0);
        personGrid.add(personInpBtn, 1, 0);

        coordsGrid.add(coordXLbl, 0, 0);
        coordsGrid.add(coordXTBox, 1, 0);
        coordsGrid.add(coordYLbl, 2, 0);
        coordsGrid.add(coordYTBox, 3, 0);

        mainGrid.add(titleLbl, 0, 0);
        mainGrid.add(titleTBox, 1, 0);
        mainGrid.add(dateLbl, 0, 1);
        mainGrid.add(datePick, 1, 1);
        mainGrid.add(coordinatesLbl, 0, 2);
        mainGrid.add(coordsGrid, 1, 2);
        mainGrid.add(oscarsLbl, 0, 3);
        mainGrid.add(oscarsTBox, 1, 3);
        mainGrid.add(boxOfficeLbl, 0, 4);
        mainGrid.add(boxOfficeTBox, 1, 4);
        mainGrid.add(genreLbl, 0, 5);
        mainGrid.add(genreCBox, 1, 5);
        mainGrid.add(ratingLbl, 0, 6);
        mainGrid.add(ratingCBox, 1, 6);
        mainGrid.add(screenWriterLbl, 0, 7);
        mainGrid.add(personGrid, 1, 7);
        mainGrid.add(btnHBox, 1, 8);
    }

    private void fillUpBoxes() {
        ObservableList<String> genreList = FXCollections.observableArrayList();
        Arrays.stream(MovieGenre.values()).forEach(genre -> genreList.add(genre.getDescription()));
        genreCBox.setItems(genreList);

        ObservableList<String> MpaaList = FXCollections.observableArrayList();
        Arrays.stream(MpaaRating.values()).forEach(rating -> MpaaList.add(rating.name()));
        ratingCBox.setItems(MpaaList);
    }

    private void fillUpFields(Movie oldMovie) {
        titleTBox.setText(oldMovie.getTitle());
        datePick.setValue(oldMovie.getCreationDate());
        coordXTBox.setText(Float.toString(oldMovie.getCoordinates().getX()));
        coordYTBox.setText(Float.toString(oldMovie.getCoordinates().getY()));
        oscarsTBox.setText(Integer.toString(oldMovie.getOscarsCount()));
        boxOfficeTBox.setText(Float.toString(oldMovie.getUsaBoxOffice()));
        if (oldMovie.getGenre() != null)
            genreCBox.setValue(oldMovie.getGenre().getDescription());
        if (oldMovie.getMpaaRating() != null)
            ratingCBox.setValue(oldMovie.getMpaaRating().toString());
        screenwriter = oldMovie.getScreenWriter();
        screenWriterTBox.setText(screenwriter.getName());
        personInpBtn.setText(Localizator.changeBtn.get());
    }

    private void rndLocalDate() {
        Random rnd = new Random();
        LocalDate ld;
        long days;

        // Get an Epoch value roughly between 1940 and 2010
        // -946771200000L = January 1, 1940
        // Add up to 80 years to it (using modulus on the next long)
        days = Math.abs(rnd.nextLong()) % (50L * 365);

        // Construct a date
        ld = LocalDate.ofEpochDay(days);
        datePick.setValue(ld);
    }

    private void showStage(Stage primaryStage) {
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle(Localizator.movieFormTitle.get());
        primaryStage.setResizable(false);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    public void setPerson(Person screenwriter) {
        this.screenwriter = screenwriter;
        screenWriterTBox.setText(screenwriter.getName());
        personInpBtn.setText(Localizator.changeBtn.get());
    }
}