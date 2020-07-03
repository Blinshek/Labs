package com.company;

import Enums.MovieGenre;
import Enums.MpaaRating;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.Arrays;

public class FilterRenderer {
    private static TableView<Movie> table;
    private static ObservableList<Movie> tableItemsList;
    private static Label GroupLbl = new Label(Localizator.filterLbl.get());
    private static Label valueLbl = new Label(Localizator.valueLbl.get());
    private static Label xCoordLbl = new Label("X");
    private static Label yCoordLbl = new Label("Y");

    private static Button clearFilterBtn = new Button(Localizator.clearBtn.get());

    private static DatePicker datePick = new DatePicker();

    private static TextField valueTBox = new TextField();
    private static TextField xCoordValueTBox = new TextField();
    private static TextField yCoordValueTBox = new TextField();

    private static ComboBox<String> fieldCBox = new ComboBox<>();
    private static ComboBox<String> valueCBox = new ComboBox<>();

    private static CheckBox filterEnabledCheBox = new CheckBox(Localizator.byColumnChBox.get());

    private static ObservableList<String> genreList = FXCollections.observableArrayList();
    private static ObservableList<String> ratingList = FXCollections.observableArrayList();

    private static GridPane filterGrid = new GridPane();
    private static HBox coordsHBox = new HBox(10);


    public static GridPane RenderFilter(TableView<Movie> table, ObservableList<Movie> tableList) {
        FilterRenderer.table = table;
        FilterRenderer.tableItemsList = tableList;
        fillFieldCBox();
        setVisualDesign();
        setBindings();
        setListeners();
        setText();
        setFilter();
        fillGrid();
        return filterGrid;
    }

    private static void setListeners() {
        ChangeListener<String> floatListener = (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,8}([.]\\d{0,2})?")) {
                valueTBox.setText(oldValue);
            }
        };

        ChangeListener<String> intListener = (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,10}?")) {
                valueTBox.setText(oldValue);
            }
        };

        fieldCBox.setOnAction(event -> {
            String value = fieldCBox.getValue();
            valueTBox.clear();
            xCoordValueTBox.clear();
            yCoordValueTBox.clear();
            datePick.setValue(null);
            valueCBox.setValue(null);

            if (value != null) {
                if (value.equals("ID") || value.equals(Localizator.oscarsCol.get())) {
                    valueTBox.textProperty().removeListener(floatListener);
                    valueTBox.textProperty().addListener(intListener);
                }
                if (value.equals(Localizator.titleCol.get()) || value.equals(Localizator.screenwriterCol.get()) || value.equals(Localizator.ownerCol.get())) {
                    valueTBox.textProperty().removeListener(intListener);
                    valueTBox.textProperty().removeListener(floatListener);
                }
                if (value.equals(Localizator.boxOfficeCol.get())) {
                    valueTBox.textProperty().removeListener(intListener);
                    valueTBox.textProperty().addListener(floatListener);
                }
                if (value.equals(Localizator.genreCol.get())) {
                    valueCBox.setItems(genreList);
                }
                if (value.equals(Localizator.ratingCol.get().replace("\n", " "))) {
                    valueCBox.setItems(ratingList);
                }
                /*
                switch (value) {
                    case "ID":
                    case "Oscars": {
                        valueTBox.textProperty().removeListener(floatListener);
                        valueTBox.textProperty().addListener(intListener);
                        break;
                    }
                    case "Title":
                    case "Screenwriter":
                    case "Owner": {
                        valueTBox.textProperty().removeListener(intListener);
                        valueTBox.textProperty().removeListener(floatListener);
                        break;
                    }
                    case "USA box office": {
                        valueTBox.textProperty().removeListener(intListener);
                        valueTBox.textProperty().addListener(floatListener);
                        break;
                    }
                    case "Genre": {
                        valueCBox.setItems(genreList);
                        break;
                    }
                    case "MPAA rating": {
                        valueCBox.setItems(ratingList);
                        break;
                    }
                }
                 */
            }
        });

        xCoordValueTBox.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,8}([.]\\d{0,2})?")) {
                xCoordValueTBox.setText(oldValue);
            }
        });

        yCoordValueTBox.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,8}([.]\\d{0,2})?")) {
                yCoordValueTBox.setText(oldValue);
            }
        });

        clearFilterBtn.setOnAction(event -> {
            fieldCBox.setValue(null);
            valueCBox.setValue(null);
            xCoordValueTBox.clear();
            yCoordValueTBox.clear();
            valueTBox.clear();
            datePick.setValue(null);
        });
    }

    private static void setBindings() {
        valueTBox.visibleProperty().bind(fieldCBox.valueProperty().isEqualTo("ID")
                .or(fieldCBox.valueProperty().isEqualTo(Localizator.titleCol.get()))
                .or(fieldCBox.valueProperty().isEqualTo(Localizator.oscarsCol.get()))
                .or(fieldCBox.valueProperty().isEqualTo(Localizator.boxOfficeCol.get()))
                .or(fieldCBox.valueProperty().isEqualTo(Localizator.screenwriterCol.get()))
                .or(fieldCBox.valueProperty().isEqualTo(Localizator.ownerCol.get())));
        coordsHBox.visibleProperty().bind(fieldCBox.valueProperty().isEqualTo(Localizator.coordinatesCol.get()));
        datePick.visibleProperty().bind(fieldCBox.valueProperty().isEqualTo(Localizator.creationDateCol.get()));
        valueCBox.visibleProperty().bind(fieldCBox.valueProperty().isEqualTo(Localizator.genreCol.get())
                .or(fieldCBox.valueProperty().isEqualTo(Localizator.ratingCol.get().replace("\n", " "))));

        valueLbl.visibleProperty().bind(fieldCBox.valueProperty().isNotNull());

        clearFilterBtn.disableProperty().bind(valueTBox.textProperty().isEmpty()
                .and(xCoordValueTBox.textProperty().isEmpty())
                .and(yCoordValueTBox.textProperty().isEmpty())
                .and(datePick.valueProperty().isNull())
                .and(valueCBox.valueProperty().isNull())
                .or(filterEnabledCheBox.selectedProperty().not()));
        valueTBox.disableProperty().bind(filterEnabledCheBox.selectedProperty().not());
        valueCBox.disableProperty().bind(filterEnabledCheBox.selectedProperty().not());
        datePick.disableProperty().bind(filterEnabledCheBox.selectedProperty().not());
        coordsHBox.disableProperty().bind(filterEnabledCheBox.selectedProperty().not());
        fieldCBox.disableProperty().bind(filterEnabledCheBox.selectedProperty().not());
    }

    private static void setText() {
        Localizator.curLanguageIdd.addListener((observable, oldValue, newValue) -> {
            GroupLbl.setText(Localizator.filterLbl.get());
            valueLbl.setText(Localizator.valueLbl.get());
            clearFilterBtn.setText(Localizator.clearBtn.get());
            filterEnabledCheBox.setText(Localizator.byColumnChBox.get());
            setBindings();
            fillFieldCBox();
        });
    }

    public static void setFilter() {
        FilteredList<Movie> filteredData = new FilteredList<>(tableItemsList, p -> true);
        valueCBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(movie -> {
                if (newValue == null || newValue.isEmpty())
                    return true;

                String lowerCaseFilter = newValue.toLowerCase();
                if (fieldCBox.getValue().equals(Localizator.genreCol.get())) {
                    if (movie.getGenre() == null)
                        return false;
                    else
                        return movie.getGenre().getDescription().toLowerCase().equals(lowerCaseFilter);
                }
                if (fieldCBox.getValue().equals(Localizator.ratingCol.get())) {
                    if (movie.getMpaaRating() == null)
                        return false;
                    else
                        return movie.getMpaaRating().name().toLowerCase().equals(lowerCaseFilter);
                }
                return false; // Does not match.
            });

            updateTable(filteredData);
        });

        datePick.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(movie -> {
                if (newValue == null)
                    return true;

                if (movie.getCreationDate() == null)
                    return false;
                else
                    return movie.getCreationDate().equals(newValue);
            });

            updateTable(filteredData);
        });

        valueTBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(movie -> {
                if (newValue == null || newValue.isEmpty())
                    return true;

                String lowerCaseFilter = newValue.toLowerCase();
                String value = fieldCBox.getValue();
                if (value.equals("ID")) {
                    return Integer.parseInt(lowerCaseFilter) == movie.getId();
                }
                if (value.equals(Localizator.titleCol.get())) {
                    return movie.getTitle().toLowerCase().contains(lowerCaseFilter);
                }
                if (value.equals(Localizator.oscarsCol.get())) {
                    return Integer.parseInt(lowerCaseFilter) == movie.getOscarsCount();
                }
                if (value.equals(Localizator.boxOfficeCol.get())) {
                    return Float.parseFloat(lowerCaseFilter) == movie.getUsaBoxOffice();
                }
                if (value.equals(Localizator.screenwriterCol.get())) {
                    return movie.getScreenWriter().getName().toLowerCase().contains(lowerCaseFilter);
                }
                if (value.equals(Localizator.ownerCol.get())) {
                    return movie.getOwnerLogin().toLowerCase().contains(lowerCaseFilter);
                }
                    /*
                    switch (fieldCBox.getValue()) {
                        case "ID": {
                            return Integer.parseInt(lowerCaseFilter) == movie.getId();
                        }
                        case "Oscars": {
                            return Integer.parseInt(lowerCaseFilter) == movie.getOscarsCount();
                        }
                        case "USA box office": {
                            return Float.parseFloat(lowerCaseFilter) == movie.getUsaBoxOffice();
                        }
                        case "Screenwriter": {
                            return movie.getScreenWriter().getName().contains(lowerCaseFilter);
                        }
                        case "Owner": {
                            return movie.getOwnerLogin().contains(lowerCaseFilter);
                        }
                    }
                     */
                return false; // Does not match.
            });

            updateTable(filteredData);
        });

        xCoordValueTBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(movie -> {
                if (newValue == null || newValue.isEmpty())
                    if (yCoordValueTBox.getText().isEmpty())
                        return true;
                    else
                        return movie.getCoordinates().getY() == Float.parseFloat(yCoordValueTBox.getText());

                if (yCoordValueTBox.getText().isEmpty())
                    return movie.getCoordinates().getX() == Float.parseFloat(newValue);
                else
                    return (movie.getCoordinates().getY() == Float.parseFloat(yCoordValueTBox.getText()) &&
                            movie.getCoordinates().getX() == Float.parseFloat(newValue));
            });

            updateTable(filteredData);
        });

        yCoordValueTBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(movie -> {
                if (newValue == null || newValue.isEmpty())
                    if (xCoordValueTBox.getText().isEmpty())
                        return true;
                    else
                        return movie.getCoordinates().getX() == Float.parseFloat(xCoordValueTBox.getText());

                if (xCoordValueTBox.getText().isEmpty())
                    return movie.getCoordinates().getY() == Float.parseFloat(newValue);
                else
                    return (movie.getCoordinates().getX() == Float.parseFloat(xCoordValueTBox.getText()) &&
                            movie.getCoordinates().getY() == Float.parseFloat(newValue));
            });

            updateTable(filteredData);
        });
    }

    private static void updateTable(FilteredList<Movie> filteredData) {
        table.setItems(filteredData);
        table.refresh();
    }

    private static void fillFieldCBox() {
        ObservableList<String> filterFieldsList = FXCollections.observableArrayList();
        table.getColumns().forEach(column -> {
            filterFieldsList.add(column.getText().replace("\n", " "));
        });
        fieldCBox.setItems(filterFieldsList);

        Arrays.stream(MovieGenre.values()).forEach(genre -> genreList.add(genre.getDescription()));

        Arrays.stream(MpaaRating.values()).forEach(rating -> ratingList.add(rating.name()));
    }

    private static void fillGrid() {
        coordsHBox.getChildren().addAll(xCoordLbl, xCoordValueTBox, yCoordLbl, yCoordValueTBox);

        filterGrid.add(GroupLbl, 0, 0);
        filterGrid.add(filterEnabledCheBox, 0, 1);
        filterGrid.add(fieldCBox, 1, 1);
        filterGrid.add(valueLbl, 2, 1);
        filterGrid.add(valueCBox, 3, 1);
        filterGrid.add(valueTBox, 3, 1);
        filterGrid.add(coordsHBox, 3, 1);
        filterGrid.add(datePick, 3, 1);
        filterGrid.add(clearFilterBtn, 4, 1);
    }

    private static void setVisualDesign() {
        filterGrid.setHgap(10);
        filterGrid.setVgap(10);
        fieldCBox.setPrefWidth(150);
        valueCBox.setPrefWidth(150);
        valueTBox.setPrefWidth(150);
        coordsHBox.setPrefWidth(150);
        datePick.setPrefWidth(150);
    }
}