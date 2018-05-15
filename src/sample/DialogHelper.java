package sample;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class DialogHelper {

    public static Alert getErrorDialog(String header, Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(ex.getMessage());

        return alert;
    }

    public static Dialog<String[]> getCreateNewDialog() {
        // Create the custom dialog.
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Create new file");
        dialog.setHeaderText("Set the options");

        // Set the icon (must be included in the project).
        // dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

        // Set the button types.
        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField fileName = new TextField();
        fileName.setText("Untitled");

        TextField canvasWidth = new TextField();
        canvasWidth.setText("800");

        TextField canvasHeight = new TextField();
        canvasHeight.setText("600");

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

        return dialog;
    }
}
