package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class DialogHelper {

    public static Alert getErrorDialog(String style, Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(ex.getMessage());
        alert.getDialogPane().getStylesheets().add(style);
        return alert;
    }

    public static Alert getConfirmSaveDialog(String style){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close polygon");
        alert.setHeaderText("Look carefully, you closed the polygon!");
        alert.setContentText("Do you want save changes?");
        alert.getDialogPane().getStylesheets().add(style);
        return alert;
    }

    public static Dialog<String[]> getCreateNewDialog(String style) {
        // Create the custom dialog.
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Create new file");

        // Set the button types.
        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        JFXTextField fileName = new JFXTextField("Untitled");

        JFXTextField canvasWidth = new JFXTextField("800");

        JFXTextField canvasHeight = new JFXTextField("600");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(fileName, 1, 0);

        grid.add(new Label("Width:"), 0, 1);
        grid.add(canvasWidth, 1, 1);

        grid.add(new Label("Height:"), 0, 2);
        grid.add(canvasHeight, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the filename field by default.
        Platform.runLater(fileName::requestFocus);

        dialog.setResultConverter(dialogButton -> dialogButton == createButtonType ?
                new String[]{fileName.getText(), canvasWidth.getText(), canvasHeight.getText()} :
                null);

        dialog.getDialogPane().getStylesheets().add(style);
        dialog.getDialogPane().getStyleClass().add("main");

        return dialog;
    }
}
