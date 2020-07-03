package EditingCells;

import com.company.Movie;
import com.company.Starter;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;

public class FloatEditingCell extends TableCell<Movie, Float> {
    private TextField textField;

    public FloatEditingCell() {
    }

    @Override
    public void startEdit() {
        if (!isEmpty() && ((Movie) getTableRow().getItem()).getOwnerLogin().equals(Starter.curClient.getLogin())) {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem().toString());
        setGraphic(null);
    }

    @Override
    public void updateItem(Float item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            //setText(item.toString());
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.setOnAction((e) -> commitEdit(Float.parseFloat(textField.getText())));
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                System.out.println("Commiting " + textField.getText());
                commitEdit(Float.parseFloat(textField.getText()));
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}
