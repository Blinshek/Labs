package com.company;

import EditingCells.*;
import Enums.Country;
import Enums.MovieGenre;
import Enums.MpaaRating;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.time.LocalDate;

public class TableRenderer {
    private static TableColumn<Movie, Integer> idCol = new TableColumn<>("ID");
    private static TableColumn<Movie, String> titleCol = new TableColumn<>(Localizator.titleCol.get());
    private static TableColumn<Movie, Coordinates> coordsCol = new TableColumn<>(Localizator.coordinatesCol.get());
    private static TableColumn<Movie, Float> coordXCol = new TableColumn<>("X");
    private static TableColumn<Movie, Float> coordYCol = new TableColumn<>("Y");
    private static TableColumn<Movie, LocalDate> creationDateCol = new TableColumn<>(Localizator.creationDateCol.get());
    private static TableColumn<Movie, Integer> oscarsCol = new TableColumn<>(Localizator.oscarsCol.get());
    private static TableColumn<Movie, Float> boxOfficeCol = new TableColumn<>(Localizator.boxOfficeCol.get());
    private static TableColumn<Movie, MovieGenre> genreColumn = new TableColumn<>(Localizator.genreCol.get());
    private static TableColumn<Movie, MpaaRating> ratingCol = new TableColumn<>(Localizator.ratingCol.get());
    private static TableColumn<Movie, Coordinates> screenWriterCol = new TableColumn<>(Localizator.screenwriterCol.get());
    private static TableColumn<Movie, String> nameCol = new TableColumn<>(Localizator.personNameCol.get());
    private static TableColumn<Movie, LocalDate> birthdayCol = new TableColumn<>(Localizator.birthdayCol.get());
    private static TableColumn<Movie, Country> nationalityColumn = new TableColumn<>(Localizator.nativeCountryCol.get());
    private static TableColumn<Movie, Coordinates> locationCol = new TableColumn<>(Localizator.locationCol.get());
    private static TableColumn<Movie, String> locNameCol = new TableColumn<>(Localizator.locNameCol.get());
    private static TableColumn<Movie, Long> locXCol = new TableColumn<>("X");
    private static TableColumn<Movie, Long> locYCol = new TableColumn<>("Y");
    private static TableColumn<Movie, Long> locZCol = new TableColumn<>("Z");
    private static TableColumn<Movie, String> ownerLoginCol = new TableColumn<>(Localizator.ownerCol.get());

    public static TableView<Movie> renderTable() {
        TableView<Movie> table = new TableView<>();

        setCellValueFactories();
        setCellFactories();
        setEditRules();
        setText();
        table.setMaxSize(1100, 210);

        //Добавление колонок в таблицу----------------------------------------------------------
        coordsCol.getColumns().addAll(coordXCol, coordYCol);
        locationCol.getColumns().addAll(locNameCol, locXCol, locYCol, locZCol);
        screenWriterCol.getColumns().addAll(nameCol, birthdayCol, nationalityColumn, locationCol);
        table.getColumns().addAll(idCol, titleCol, coordsCol, creationDateCol, oscarsCol, boxOfficeCol, genreColumn, ratingCol, screenWriterCol, ownerLoginCol);
        return table;
    }

    private static void setCellValueFactories() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        coordXCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getCoordinates().getX()));
        coordYCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getCoordinates().getY()));
        creationDateCol.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        oscarsCol.setCellValueFactory(new PropertyValueFactory<>("oscarsCount"));
        boxOfficeCol.setCellValueFactory(new PropertyValueFactory<>("usaBoxOffice"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("mpaaRating"));
        nameCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getScreenWriter().getName()));
        birthdayCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getScreenWriter().getBirthday()));
        nationalityColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getScreenWriter().getNationality()));
        locNameCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getScreenWriter().getLocation().getName()));
        locXCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getScreenWriter().getLocation().getX()));
        locYCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getScreenWriter().getLocation().getY()));
        locZCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getScreenWriter().getLocation().getZ()));
        ownerLoginCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getOwnerLogin()));
    }

    private static void setCellFactories() {
        Callback<TableColumn<Movie, LocalDate>, TableCell<Movie, LocalDate>> dateCellFactory
                = (TableColumn<Movie, LocalDate> param) -> new DateEditingCell();
        Callback<TableColumn<Movie, String>, TableCell<Movie, String>> strCellFactory
                = (TableColumn<Movie, String> param) -> new StringEditingCell();
        Callback<TableColumn<Movie, Integer>, TableCell<Movie, Integer>> intCellFactory
                = (TableColumn<Movie, Integer> param) -> new IntEditingCell();
        Callback<TableColumn<Movie, Float>, TableCell<Movie, Float>> floatCellFactory
                = (TableColumn<Movie, Float> param) -> new FloatEditingCell();
        Callback<TableColumn<Movie, Long>, TableCell<Movie, Long>> longCellFactory
                = (TableColumn<Movie, Long> param) -> new LongEditingCell();
        Callback<TableColumn<Movie, MpaaRating>, TableCell<Movie, MpaaRating>> RatingCBoxCellFactory
                = (TableColumn<Movie, MpaaRating> param) -> new RatingCBoxEditingCell();
        Callback<TableColumn<Movie, MovieGenre>, TableCell<Movie, MovieGenre>> GenreCBoxCellFactory
                = (TableColumn<Movie, MovieGenre> param) -> new GenreCBoxEditingCell();
        Callback<TableColumn<Movie, Country>, TableCell<Movie, Country>> CountryCBoxCellFactory
                = (TableColumn<Movie, Country> param) -> new CountryCBoxEditingCell();

        titleCol.setCellFactory(strCellFactory);
        coordXCol.setCellFactory(floatCellFactory);
        coordYCol.setCellFactory(floatCellFactory);
        creationDateCol.setCellFactory(dateCellFactory);
        oscarsCol.setCellFactory(intCellFactory);
        boxOfficeCol.setCellFactory(floatCellFactory);
        genreColumn.setCellFactory(GenreCBoxCellFactory);
        ratingCol.setCellFactory(RatingCBoxCellFactory);
        nameCol.setCellFactory(strCellFactory);
        birthdayCol.setCellFactory(dateCellFactory);
        nationalityColumn.setCellFactory(CountryCBoxCellFactory);
        locNameCol.setCellFactory(strCellFactory);
        locXCol.setCellFactory(longCellFactory);
        locYCol.setCellFactory(longCellFactory);
        locZCol.setCellFactory(longCellFactory);
    }

    private static void setEditRules() {
        titleCol.setOnEditCommit((TableColumn.CellEditEvent<Movie, String> event) -> {
            Main.mainFormController.isTableEdited.set(true);
            event.getRowValue().setTitle(event.getNewValue());
        });

        coordXCol.setOnEditCommit((TableColumn.CellEditEvent<Movie, Float> event) -> {
            event.getRowValue().getCoordinates().setX(event.getNewValue());
            Main.mainFormController.isTableEdited.set(true);

            Main.mainFormController.scatterChart.addNodes(Main.mainFormController.moviesList);
        });

        coordYCol.setOnEditCommit((TableColumn.CellEditEvent<Movie, Float> event) -> {
            event.getRowValue().getCoordinates().setY(event.getNewValue());
            Main.mainFormController.isTableEdited.set(true);
            Main.mainFormController.scatterChart.addNodes(Main.mainFormController.moviesList);
        });

        creationDateCol.setOnEditCommit((TableColumn.CellEditEvent<Movie, LocalDate> event) -> {
            event.getRowValue().setCreationDate(event.getNewValue());
            Main.mainFormController.isTableEdited.set(true);
        });

        oscarsCol.setOnEditCommit((TableColumn.CellEditEvent<Movie, Integer> event) -> {
            event.getRowValue().setOscarsCount(event.getNewValue());
            Main.mainFormController.isTableEdited.set(true);
        });

        boxOfficeCol.setOnEditCommit((TableColumn.CellEditEvent<Movie, Float> event) -> {
            event.getRowValue().setUsaBoxOffice(event.getNewValue());
            Main.mainFormController.isTableEdited.set(true);
        });

        genreColumn.setOnEditCommit((TableColumn.CellEditEvent<Movie, MovieGenre> event) -> {
            event.getRowValue().setGenre(event.getNewValue());
            Main.mainFormController.isTableEdited.set(true);
        });

        ratingCol.setOnEditCommit((TableColumn.CellEditEvent<Movie, MpaaRating> event) -> {
            event.getRowValue().setMpaaRating(event.getNewValue());
            Main.mainFormController.isTableEdited.set(true);
        });

        nameCol.setOnEditCommit((TableColumn.CellEditEvent<Movie, String> event) -> {
            event.getRowValue().getScreenWriter().setName(event.getNewValue());
            Main.mainFormController.isTableEdited.set(true);
        });

        birthdayCol.setOnEditCommit((TableColumn.CellEditEvent<Movie, LocalDate> event) -> {
            event.getRowValue().getScreenWriter().setBirthday(event.getNewValue());
            Main.mainFormController.isTableEdited.set(true);
        });

        nationalityColumn.setOnEditCommit((TableColumn.CellEditEvent<Movie, Country> event) -> {
            event.getRowValue().getScreenWriter().setNationality(event.getNewValue());
            Main.mainFormController.isTableEdited.set(true);
        });

        locNameCol.setOnEditCommit((TableColumn.CellEditEvent<Movie, String> event) -> {
            event.getRowValue().getScreenWriter().getLocation().setName(event.getNewValue());
            Main.mainFormController.isTableEdited.set(true);
        });

        locXCol.setOnEditCommit((TableColumn.CellEditEvent<Movie, Long> event) -> {
            event.getRowValue().getScreenWriter().getLocation().setX(event.getNewValue());
            Main.mainFormController.isTableEdited.set(true);
        });

        locYCol.setOnEditCommit((TableColumn.CellEditEvent<Movie, Long> event) -> {
            event.getRowValue().getScreenWriter().getLocation().setY(event.getNewValue());
            Main.mainFormController.isTableEdited.set(true);
        });

        locZCol.setOnEditCommit((TableColumn.CellEditEvent<Movie, Long> event) -> {
            event.getRowValue().getScreenWriter().getLocation().setZ(event.getNewValue());
            Main.mainFormController.isTableEdited.set(true);
        });
    }

    private static void setText() {
        Localizator.curLanguageIdd.addListener((observable, oldValue, newValue) -> {
            titleCol.setText(Localizator.titleCol.get());
            coordsCol.setText(Localizator.coordinatesCol.get());
            creationDateCol.setText(Localizator.creationDateCol.get());
            oscarsCol.setText(Localizator.oscarsCol.get());
            boxOfficeCol.setText(Localizator.boxOfficeCol.get());
            genreColumn.setText(Localizator.genreCol.get());
            ratingCol.setText(Localizator.ratingCol.get());
            screenWriterCol.setText(Localizator.screenwriterCol.get());
            nameCol.setText(Localizator.personNameCol.get());
            birthdayCol.setText(Localizator.birthdayCol.get());
            nationalityColumn.setText(Localizator.nativeCountryCol.get());
            locationCol.setText(Localizator.locationCol.get());
            locNameCol.setText(Localizator.locNameCol.get());
            ownerLoginCol.setText(Localizator.ownerCol.get());
        });
    }
}