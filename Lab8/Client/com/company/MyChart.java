package com.company;

import Forms.MovieInpForm;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MyChart extends ScatterChart<Number, Number> {
    public MyChart() {
        super(new NumberAxis(0, 5, 1),
                new NumberAxis(0, 5, 1));
        setSettings();
        setDesign();
        setText();
    }

    public void setSettings() {
        setMaxSize(500, 500);
        getXAxis().setLabel("x");
        getYAxis().setLabel("y");
        setTitle(Localizator.chartTitle.get());
        setScaleShape(true);

        setOnScroll(ev -> {
            double deltaY = ev.getDeltaY();
            double zoomFactor = (deltaY > 0) ? 0.5 : -0.5;

            NumberAxis xAxisLocal = ((NumberAxis) getXAxis());
            xAxisLocal.setUpperBound(xAxisLocal.getUpperBound() + zoomFactor);
            xAxisLocal.setLowerBound(xAxisLocal.getLowerBound() + zoomFactor);
            ev.consume();
        });
    }

    private void setDesign() {
        final DropShadow shadow = new DropShadow();
        shadow.setOffsetX(1);
        shadow.setColor(Color.GREY);
        setEffect(shadow);
    }

    private void setText(){
        Localizator.curLanguageIdd.addListener((observable, oldValue, newValue) -> {
            setTitle(Localizator.chartTitle.get());
        });
    }

    //Заполнение графа
    public void addNodes(ObservableList<Movie> moviesList) {
        if (!moviesList.isEmpty()) {
            getData().clear();
            ArrayList<String> owner = getOwners(moviesList);
            for (String ownerLogin : owner) {
                XYChart.Series<Number, Number> series = new XYChart.Series<>();
                series.setName(ownerLogin);
                for (Movie entry : moviesList) {
                    if (entry.getOwnerLogin().equals(ownerLogin)) {
                        XYChart.Data<Number, Number> item = new XYChart.Data<>(entry.getCoordinates().getX(), entry.getCoordinates().getY());
                        Node symbol = new StackPane();

                        symbol.setOnMouseClicked(event -> {
                            if (ownerLogin.equals(Starter.curClient.getLogin())) {
                                try {
                                    Main.mainFormController.movieInpFormController = new MovieInpForm(entry, true);
                                    Main.mainFormController.movieInpFormController.start(new Stage());
                                    Main.mainFormController.editedMovie = entry;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        Tooltip tooltip = new Tooltip(entry.getTitle());
                        Tooltip.install(symbol, tooltip);
                        item.setNode(symbol);
                        series.getData().add(item);
                    }

                }
                getData().add(series);
            }
        }
    }

    private ArrayList<String> getOwners(ObservableList<Movie> moviesList) {
        if (!moviesList.isEmpty()) {
            ArrayList<String> owner = new ArrayList<>();
            for (Movie movie : moviesList) {
                String ow = movie.getOwnerLogin();
                if (!owner.contains(ow))
                    owner.add(ow);
            }
            return owner;
        }
        return null;
    }
}