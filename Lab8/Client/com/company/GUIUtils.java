package com.company;

import com.sun.javafx.scene.control.skin.TableViewSkin;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.DoubleAdder;

public class GUIUtils {
    private static Method columnToFitMethod;

    static {
        try {
            columnToFitMethod = TableViewSkin.class.getDeclaredMethod("resizeColumnToFitContent", TableColumn.class, int.class);
            columnToFitMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void autoFitTable(TableView tableView) {
        tableView.getItems().addListener((ListChangeListener<Object>) c -> {
            for (Object column : tableView.getColumns()) {
                try {
                    columnToFitMethod.invoke(tableView.getSkin(), column, -1);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void customResize(TableView<?> view) {

        DoubleAdder width = new DoubleAdder();
        view.getColumns().forEach(col -> {
            width.add(col.getWidth());
        });
        double tableWidth = view.getWidth();

        if (tableWidth > width.doubleValue()) {
            TableColumn<?, ?> col = view.getColumns().get(view.getColumns().size() - 1);
            col.setPrefWidth(col.getWidth() + (tableWidth - width.doubleValue()));
        }

    }

    public static void autoResizeColumns(TableView<?> table) {
        //Set the right policy
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getColumns().forEach(column ->
                recursiveSize(column, table));
    }

    private static void recursiveSize(TableColumn<?, ?> col, TableView<?> table) {
        if (col.getColumns().size() != 0) {
            col.getColumns().forEach(column -> recursiveSize(column, table));
        } else {
            Text t = new Text(col.getText());
            double max = t.getLayoutBounds().getWidth();
            for (int i = 0; i < table.getItems().size(); i++) {
                //cell must not be empty
                if (col.getCellData(i) != null) {
                    t = new Text(col.getCellData(i).toString());
                    double calcwidth = t.getLayoutBounds().getWidth();
                    //remember new max-width
                    if (calcwidth > max) {
                        max = calcwidth;
                    }
                }
            }
            col.setPrefWidth(max + 30.0d);
        }

    }
}