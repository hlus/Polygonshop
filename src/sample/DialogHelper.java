package sample;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.behavior.JFXColorPickerBehavior;
import com.jfoenix.skins.JFXColorPickerSkin;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

/**
 * This class just generate dialogs
 * and have only static methods
 *
 * @author hlus
 * @version 2.0
 */
public class DialogHelper {

    /**
     * This static method which generate Error Alert
     *
     * @param style String variable which describe style (.css)
     * @param msg   String which set to content text
     * @return Alert (with Error title)
     * @see Controller#style
     */
    public static Alert getErrorDialog(String style, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(msg);
        alert.getDialogPane().getStylesheets().add(style);
        return alert;
    }

    /**
     * This static method which generate confirmation Alert
     *
     * @param style String variable which describe style (.css)
     * @return Alert (with 'Close polygon' title)
     * @see Controller#style
     */
    public static Alert getConfirmSaveDialog(String style) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close polygon");
        alert.setHeaderText("Look carefully, you closed the polygon!");
        alert.setContentText("Do you want save changes?");
        alert.getDialogPane().getStylesheets().add(style);
        return alert;
    }

    public static Alert getTriangulationInfoDialog(String style, TriangulatedPolygon tPol) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Triangulation info");
        //alert.setHeaderText("Look, an Information Dialog");
        String information = "Vertex count: " + tPol.getN() + "\n" +
                "Triangles count: " + tPol.getTrianglesCount() + "\n" +
                "Diagonals count: " + tPol.getDiaglonalesCount() + "\n" +
                "Triangulation cost: " + tPol.getCostSum() + "\n" +
                "Root (solution):" + tPol.getRootNode().getSeg().getDesc();
        alert.setContentText(information);
        alert.getDialogPane().getStylesheets().add(style);
        return alert;
    }

    /**
     * This static method which generate dialog
     * where is ask some options for create new 'polygon file'
     *
     * @param style String variable which describe style (.css)
     * @return Dialog which result string array (fileName, width, height)
     * @see UIPolygon
     * @see UIPolygon#fileName
     * @see UIPolygon#width
     * @see UIPolygon#height
     * @see Controller#style
     */
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

        dialog.getDialogPane().getStylesheets().add(style);
        dialog.getDialogPane().getStyleClass().add("main");

        dialog.setResultConverter(dialogButton -> dialogButton == createButtonType ?
                new String[]{fileName.getText(), canvasWidth.getText(), canvasHeight.getText()} :
                null);

        return dialog;
    }

    public static Dialog<OptionValues> getOptionsDialog(String style, OptionValues values) {
        Dialog<OptionValues> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        JFXCheckBox showDescription = new JFXCheckBox();
        showDescription.setSelected(values.showDescription);
        JFXTextField pointRadius = new JFXTextField(String.format("%.1f", values.pointRadius));
        JFXTextField lineWidth = new JFXTextField(String.format("%.1f", values.lineWidth));
        JFXTextField treeLineWidth = new JFXTextField(String.format("%.1f", values.treeLineWidth));
        JFXColorPicker background = new JFXColorPicker(values.polygonBackground);
        JFXColorPicker vertexColor = new JFXColorPicker(values.vertexColor);
        JFXColorPicker leafColor = new JFXColorPicker(values.leafColor);
        JFXColorPicker nodeColor = new JFXColorPicker(values.nodeColor);
        JFXColorPicker simpleLineColor = new JFXColorPicker(values.simpleLineColor);
        JFXColorPicker diagonalColor = new JFXColorPicker(values.diagonalColor);


        grid.add(new Label("show description"), 0, 0);
        grid.add(showDescription, 1, 0);

        grid.add(new Label("Point radius:"), 0, 1);
        grid.add(pointRadius, 1, 1);

        grid.add(new Label("Line width:"), 0, 2);
        grid.add(lineWidth, 1, 2);

        grid.add(new Label("Tree line width:"), 0, 3);
        grid.add(treeLineWidth, 1, 3);

        grid.add(new Label("Polygon background: "), 0, 4);
        grid.add(background, 1, 4);

        grid.add(new Label("Vertex color: "), 0, 5);
        grid.add(vertexColor, 1, 5);

        grid.add(new Label("Leaf color: "), 0, 6);
        grid.add(leafColor, 1, 6);

        grid.add(new Label("Node color: "), 0, 7);
        grid.add(nodeColor, 1, 7);

        grid.add(new Label("Line color: "), 0, 8);
        grid.add(simpleLineColor, 1, 8);

        grid.add(new Label("Diagonal color: "), 0, 9);
        grid.add(diagonalColor, 1, 9);

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(showDescription::requestFocus);

        dialog.getDialogPane().getStylesheets().add(style);
        dialog.getDialogPane().getStyleClass().add("main");

        dialog.setResultConverter(dialogButton -> dialogButton != ButtonType.OK ?
                null :
                new OptionValues(
                        showDescription.isSelected(),
                        Double.parseDouble(pointRadius.getText()),
                        Double.parseDouble(lineWidth.getText()),
                        Double.parseDouble(treeLineWidth.getText()),
                        background.getValue(),
                        vertexColor.getValue(),
                        leafColor.getValue(),
                        nodeColor.getValue(),
                        simpleLineColor.getValue(),
                        diagonalColor.getValue()
                )
        );

        return dialog;
    }
}
