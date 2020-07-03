package Forms;

import Commands.*;
import Enums.*;
import Exceptions.CommandParseException;
import ThreadTasks.ReaderTask;
import ThreadTasks.SendCmdTask;
import com.company.*;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

import static com.company.Starter.*;

public class MainForm extends Application {
    private Stage primaryStage = new Stage();

    private HBox logRegHBox = new HBox(10);
    private VBox btnVBox = new VBox(10);
    private GridPane tableGrid = new GridPane();
    private GridPane mainGrid = new GridPane();
    private GridPane userGrid = new GridPane();
    private GridPane cmdGrid = new GridPane();
    private GridPane menuBarGrid = new GridPane();

    private Scene primaryScene = new Scene(menuBarGrid);

    private Label allBDlbl = new Label(Localizator.allDbLbl.get());
    private Label cmdLbl = new Label(Localizator.cmdInputLbl.get());

    private Label answersLbl = new Label(Localizator.serverAnswersLbl.get());
    private Label idLbl = new Label("ID:");
    private Label inputLbl = new Label();
    private Label curCmdLbl = new Label(Localizator.cmdOverviewLbl.get());

    private ComboBox<String> cmdCBox = new ComboBox<>();
    private ComboBox<Integer> cmdIdCBox = new ComboBox<>();
    private ComboBox<String> langCBox = new ComboBox<>();

    private TextField inputTBox = new TextField();
    private TextField curCmdTBox = new TextField();

    private String[] curCmd = new String[4];

    private TextArea answerTA = new TextArea();

    private Button addMovieBtn = new Button(Localizator.addMovieBtn.get());
    private Button sendCmdBtn = new Button(Localizator.sendCmdBtn.get());
    private Button logBtn = new Button(Localizator.loginBtn.get());
    private Button regBtn = new Button(Localizator.registrationBtn.get());
    private Button updBtn = new Button(Localizator.saveChangesBtn.get());
    private Button clearBtn = new Button(Localizator.clearBtn.get());
    private Button delBtn = new Button(Localizator.deleteBtn.get());

    private MenuButton userMenuBtn = new MenuButton();

    private MenuItem logoutMenu = new MenuItem(Localizator.exitMenuItem.get());

    private MenuBar menuBar = MenuBarRenderer.render();

    private TableView<Movie> table = TableRenderer.renderTable();

    public MyChart scatterChart = new MyChart();
    private Movie lastInpMovie;
    public Movie editedMovie;

    public MovieInpForm movieInpFormController;
    public ObservableList<Movie> moviesList = FXCollections.observableArrayList();
    public ObservableList<Movie> oldMoviesList = FXCollections.observableArrayList();

    private Group userGroup = new Group();
    private Group filterGroup = new Group();

    private String boxOfficeParseEx = Localizator.BoxOfficeCmdParseException.get();
    private String fileNameParseEx = Localizator.fileNameCmdParseException.get();
    private String movieInpParseEx = Localizator.movieCmdParseException.get();
    private String idParseEx = Localizator.idCmdParseException.get();
    private String argumentsParseEx = Localizator.argumentsCmdParseException.get();

    public SimpleBooleanProperty isTableEdited = new SimpleBooleanProperty(false);


    @Override
    public void start(Stage primaryStage) {
        setListeners();
        this.primaryStage = primaryStage;
        userMenuBtn.getItems().add(logoutMenu);
        setBindings();
        fillLangCBox();
        fillCmdCBox();
        setVisualDesign();
        setText();
        fillGrid();
        showStage();
    }

    //Связи с общественностью-------------------------------------------------
    public void setLastInputMovie(Movie movie, boolean isFromChart) {
        if (isFromChart && !movie.equals(editedMovie)) {
            moviesList.set(moviesList.indexOf(editedMovie), movie);
            isTableEdited.set(true);
        }
        if (!isFromChart) {
            lastInpMovie = movie;
            curCmd[2] = "Movie<" + movie.getTitle() + '>';
            updateCurCmdTBox();
        }
    }

    public void setMoviesList(ObservableList<Movie> newMovies) {
        moviesList.clear();
        moviesList.addAll(newMovies);
        oldMoviesList.clear();
        oldMoviesList.addAll(newMovies);
        table.setItems(moviesList);
        GUIUtils.autoResizeColumns(table);
    }

    public void setAnswer(ArrayList<String> answers) {
        for (String str : answers) {
            answerTA.setText(answerTA.getText() + str + "\n");
        }
    }
    //------------------------------------------------------------------------

    //Лисенеры
    private void setListeners() {
        logoutMenu.setOnAction(event -> {
            SendCmdTask.addCmd(new LogoutCmd(curClient));
            curClient.logOut();
            fillCmdCBox();
        });

        logBtn.setOnAction(event -> {
            LoginForm logFrm = new LoginForm();
            try {
                logFrm.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        regBtn.setOnAction(event -> {
            RegistrationForm regFrm = new RegistrationForm();
            try {
                regFrm.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        updBtn.setOnAction(event -> {
            ArrayList<Movie> updated = new ArrayList<>();
            moviesList.forEach(movie -> {
                if (movie.getOwnerLogin().equals(curClient.getLogin())) {
                    updated.add(movie);
                }
            });

            if (!updated.isEmpty()) {
                isTableEdited.set(false);
                SendCmdTask.addCmd(new UpdateCmd(updated));

                ArrayList<String> out = ReaderTask.getOutput();
                if (out.get(0).equals("yes")) {
                    out.remove(0);
                    setAnswer(out);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText(out.get(1));
                    alert.setHeaderText(null);
                    alert.show();
                }
            }
        });

        clearBtn.setOnAction(event -> {
            SendCmdTask.addCmd(new ClearCmd());
        });

        delBtn.setOnAction(event -> {
            int id = table.getFocusModel().getFocusedItem().getId();
            SendCmdTask.addCmd(new RemoveKeyCmd(id));
        });

        sendCmdBtn.setOnAction(event -> {
            try {
                parseCmd(cmdCBox.getValue());
            } catch (CommandParseException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(e.getMessage());
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        });

        addMovieBtn.setOnAction(event -> {
            movieInpFormController = new MovieInpForm(lastInpMovie);
            try {

                movieInpFormController.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        primaryStage.setOnCloseRequest(event -> {
            System.exit(-1);
        });

        ChangeListener<String> floatListener = (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,8}([.]\\d{0,2})?")) {
                inputTBox.setText(oldValue);
                curCmd[3] = ("value<" + newValue + '>');
                updateCurCmdTBox();
            }
        };

        ChangeListener<String> intListener = (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,10}?")) {
                inputTBox.setText(oldValue);
                curCmd[3] = ("value<" + newValue + '>');
                updateCurCmdTBox();
            }
        };

        ChangeListener<Boolean> logListener = (observable, oldValue, newValue) -> {
            btnVBox.setDisable(!newValue);
            userMenuBtn.setVisible(newValue);
            logRegHBox.setVisible(!newValue);
            if (newValue) {
                table.setEditable(true);
                userMenuBtn.setText(curClient.getLogin());
                fillCmdCBox();
            }
        };

        InvalidationListener moviesListener = (observable) -> {
            scatterChart.addNodes(moviesList);
            fillIdCBox();
        };

        curClient.logged.addListener(logListener);

        moviesList.addListener(moviesListener);

        langCBox.setOnAction(event -> {
            Localizator.setCurLanguageId(Language.getByStr(langCBox.getValue()).getId());
            //setText();
        });

        cmdCBox.setOnAction(event -> {
            String value = cmdCBox.getValue();

            addMovieBtn.setVisible(false);
            inputTBox.setVisible(false);
            cmdIdCBox.setVisible(false);
            idLbl.setVisible(false);
            inputLbl.setVisible(false);
            inputTBox.clear();
            if (value != null) {
                curCmd[0] = value;
                updateCurCmdTBox();
                inputTBox.textProperty().removeListener(intListener);
                inputTBox.textProperty().removeListener(floatListener);
                switch (value) {
                    case CountByUsaBoxOfficeCmd.cmdName:
                    case CountGreaterThanUsaBoxOfficeCmd.cmdName: {
                        inputLbl.setText(Localizator.boxOfficeLbl.get());
                        inputLbl.setVisible(true);
                        inputTBox.setVisible(true);
                        inputTBox.textProperty().addListener(floatListener);
                        break;
                    }
                    case RemoveKeyCmd.cmdName:
                    case RemoveLowerKeyCmd.cmdName:
                    case RemoveGreaterKeyCmd.cmdName: {
                        inputLbl.setText("ID:");
                        inputLbl.setVisible(true);
                        inputTBox.setVisible(true);
                        inputTBox.textProperty().addListener(intListener);
                        break;
                    }
                    case InsertCmd.cmdName: {
                        addMovieBtn.setVisible(true);
                        break;
                    }
                    case ReplaceIfGreaterCmd.cmdName: {
                        idLbl.setVisible(true);
                        cmdIdCBox.setVisible(true);
                        addMovieBtn.setVisible(true);
                        fillIdCBox();
                        break;
                    }
                    case ExecuteScriptCmd.cmdName: {
                        inputLbl.setText(Localizator.fileNameLbl.get());
                        inputLbl.setVisible(true);
                        inputTBox.setVisible(true);
                    }
                }
            }
        });

        cmdIdCBox.setOnAction(event -> {
            int value = cmdIdCBox.getValue();
            if (value > 0) {
                curCmd[1] = ("ID<" + value + '>');
                updateCurCmdTBox();
            }
        });
    }

    private void setBindings() {
        delBtn.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());

        updBtn.disableProperty().bind(isTableEdited.not());
    }

    private void updateCurCmdTBox() {
        curCmdTBox.clear();
        Arrays.stream(curCmd).forEach(s -> {
            if (s != null && !s.isEmpty())
                curCmdTBox.setText(curCmdTBox.getText() + "   " + s);
        });
    }

    //Выравнивания, размеры и прочее
    private void setVisualDesign() {
        mainGrid.setPadding(new Insets(15));
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);
        curCmdTBox.setEditable(false);
        answerTA.setEditable(false);

        GridPane.setHalignment(userGroup, HPos.RIGHT);
        GridPane.setValignment(filterGroup, VPos.BOTTOM);

        userGrid.setHgap(10);
        cmdGrid.setHgap(10);
        cmdGrid.setVgap(10);
        cmdGrid.setPrefWidth(500);

        tableGrid.setHgap(10);
        tableGrid.setVgap(10);

        addMovieBtn.setVisible(false);
        inputTBox.setVisible(false);
        cmdIdCBox.setVisible(false);
        idLbl.setVisible(false);
        inputLbl.setVisible(false);

        userMenuBtn.setVisible(false);
        btnVBox.setDisable(true);

        updBtn.setPrefWidth(120);
        delBtn.setPrefWidth(120);
        clearBtn.setPrefWidth(120);
        sendCmdBtn.setPrefWidth(150);
        cmdCBox.setPrefWidth(240);
        logBtn.setPrefWidth(60);
        regBtn.setPrefWidth(100);
        userMenuBtn.setPrefWidth(170);
        answerTA.setMaxWidth(500);
        answerTA.setMaxHeight(100);
    }

    //Заполнение всего и вся--------------------------------------------------
    private void fillLangCBox() {
        ObservableList<String> languages = FXCollections.observableArrayList();
        Arrays.stream(Language.values()).forEach(language -> languages.add(language.getDescription()));
        langCBox.setItems(languages);
        langCBox.setValue(languages.get(0));
    }

    private void fillCmdCBox() {
        ObservableList<String> cmdList = FXCollections.observableArrayList();
        if (curClient.isLogged()) {
            Arrays.stream(Command.values()).filter(cmd -> {
                try {
                    AbsCommand cmdd = (AbsCommand) (cmd.getCmdClass().newInstance());
                    return cmdd.modifier != CmdMod.PUBLIC_ONLY;
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                return false;
            }).forEach(cmd -> cmdList.add(cmd.getCmdName()));
        } else {
            Arrays.stream(Command.values()).filter(cmd -> {
                try {
                    AbsCommand cmdd = (AbsCommand) (cmd.getCmdClass().newInstance());
                    return cmdd.modifier != CmdMod.PRIVATE;
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                return false;
            }).forEach(cmd -> cmdList.add(cmd.getCmdName()));
        }
        cmdCBox.setItems(cmdList);
    }

    public void fillIdCBox() {
        ObservableList<Integer> idList = FXCollections.observableArrayList();
        moviesList.forEach(movie -> {
            if (movie.getOwnerLogin().equals(curClient.getLogin()))
                idList.add(movie.getId());
        });
        idList.sort(Integer::compareTo);
        cmdIdCBox.setItems(idList);
    }

    private void fillGrid() {
        GridPane filterGrid = FilterRenderer.RenderFilter(table, moviesList);
        filterGroup.getChildren().add(filterGrid);
        userGroup.getChildren().add(userGrid);

        logRegHBox.getChildren().addAll(logBtn, regBtn);
        btnVBox.getChildren().addAll(updBtn, delBtn, clearBtn);

        menuBarGrid.add(menuBar, 0, 0);
        menuBarGrid.add(mainGrid, 0, 1);

        tableGrid.add(allBDlbl, 0, 0);
        tableGrid.add(table, 0, 1);
        tableGrid.add(btnVBox, 1, 1);

        userGrid.add(langCBox, 0, 0);
        userGrid.add(logRegHBox, 1, 0);
        userGrid.add(userMenuBtn, 1, 0);

        cmdGrid.add(cmdLbl, 0, 0);
        cmdGrid.add(idLbl, 1, 0);
        cmdGrid.add(inputLbl, 2, 0);
        cmdGrid.add(cmdCBox, 0, 1);
        cmdGrid.add(cmdIdCBox, 1, 1);
        cmdGrid.add(addMovieBtn, 2, 1);
        cmdGrid.add(inputTBox, 2, 1);
        cmdGrid.add(curCmdLbl, 0, 2, 3, 1);
        cmdGrid.add(curCmdTBox, 0, 3, 3, 1);
        cmdGrid.add(sendCmdBtn, 0, 4);

        mainGrid.add(userGroup, 1, 0);
        mainGrid.add(scatterChart, 0, 1, 1, 5);
        mainGrid.add(cmdGrid, 1, 2);
        mainGrid.add(answersLbl, 1, 3);
        mainGrid.add(answerTA, 1, 4);
        mainGrid.add(filterGroup, 1, 5);
        mainGrid.add(tableGrid, 0, 6, 2, 1);
    }

    //------------------------------------------------------------------------
    private void setText() {
        Localizator.curLanguageIdd.addListener((observable, oldValue, newValue) -> {
            addMovieBtn.setText(Localizator.addMovieBtn.get());
            sendCmdBtn.setText(Localizator.sendCmdBtn.get());
            logBtn.setText(Localizator.loginBtn.get());
            regBtn.setText(Localizator.registrationBtn.get());
            updBtn.setText(Localizator.saveChangesBtn.get());
            clearBtn.setText(Localizator.clearBtn.get());
            delBtn.setText(Localizator.deleteBtn.get());
            allBDlbl.setText(Localizator.allDbLbl.get());
            cmdLbl.setText(Localizator.cmdInputLbl.get());
            answersLbl.setText(Localizator.serverAnswersLbl.get());
            curCmdLbl.setText(Localizator.cmdOverviewLbl.get());
            logoutMenu.setText(Localizator.exitMenuItem.get());
            primaryStage.setTitle(Localizator.mainFormTitle.get());
            boxOfficeParseEx = Localizator.BoxOfficeCmdParseException.get();
            fileNameParseEx = Localizator.fileNameCmdParseException.get();
            movieInpParseEx = Localizator.movieCmdParseException.get();
            idParseEx = Localizator.idCmdParseException.get();
            argumentsParseEx = Localizator.argumentsCmdParseException.get();
        });
    }

    //Показ формы
    private void showStage() {
        primaryStage.setTitle(Localizator.mainFormTitle.get());
        primaryStage.setScene(primaryScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    //Парсер команд
    private void parseCmd(String cmdDescription) throws CommandParseException {
        switch (cmdDescription) {
            case CountByUsaBoxOfficeCmd.cmdName: {
                if (!inputTBox.getText().isEmpty()) {
                    SendCmdTask.addCmd(new CountByUsaBoxOfficeCmd(Float.parseFloat(inputTBox.getText())));
                    break;
                } else
                    throw new CommandParseException(boxOfficeParseEx);
            }
            case CountGreaterThanUsaBoxOfficeCmd.cmdName: {
                if (!inputTBox.getText().isEmpty()) {
                    SendCmdTask.addCmd(new CountGreaterThanUsaBoxOfficeCmd(Float.parseFloat(inputTBox.getText())));
                    break;
                } else
                    throw new CommandParseException(boxOfficeParseEx);
            }
            case ExecuteScriptCmd.cmdName: {
                if (!inputTBox.getText().isEmpty()) {
                    SendCmdTask.addCmd(new ExecuteScriptCmd(inputTBox.getText()));
                    break;
                } else
                    throw new CommandParseException(fileNameParseEx);
            }
            case InsertCmd.cmdName: {
                if (lastInpMovie != null) {
                    SendCmdTask.addCmd(new InsertCmd(lastInpMovie));
                    lastInpMovie = null;
                    break;
                } else
                    throw new CommandParseException(movieInpParseEx);
            }
            case RemoveGreaterKeyCmd.cmdName: {
                if (!inputTBox.getText().isEmpty()) {
                    SendCmdTask.addCmd(new RemoveGreaterKeyCmd(Integer.parseInt(inputTBox.getText())));
                    break;
                } else
                    throw new CommandParseException(idParseEx);
            }
            case RemoveLowerKeyCmd.cmdName: {
                if (!inputTBox.getText().isEmpty()) {
                    SendCmdTask.addCmd(new RemoveLowerKeyCmd(Integer.parseInt(inputTBox.getText())));
                    break;
                } else
                    throw new CommandParseException(idParseEx);
            }
            case ReplaceIfGreaterCmd.cmdName: {
                if (cmdIdCBox.getValue() > 0 && lastInpMovie != null) {
                    SendCmdTask.addCmd(new ReplaceIfGreaterCmd(Integer.parseInt(inputTBox.getText()), lastInpMovie));
                    lastInpMovie = null;
                    break;
                } else {
                    throw new CommandParseException(argumentsParseEx);
                }
            }
        }

        ArrayList<String> out = ReaderTask.getOutput();
        if (out.get(0).equals("yes")) {
            out.remove(0);
            setAnswer(out);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText(out.get(1));
            alert.setHeaderText(null);
            alert.show();
        }
    }
}