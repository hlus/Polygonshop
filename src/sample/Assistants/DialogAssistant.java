package sample.Assistants;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import sample.Controller;
import sample.Model.TriangulatedPolygon;
import sample.Model.UIPolygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class just generate dialogs
 * and have only static methods
 *
 * @author hlus
 * @version 2.3
 */
public class DialogAssistant {

    /**
     * Method generate template of alert dialog
     *
     * @param style   String variable which describe style (.css)
     * @param type    alert type dialog
     * @param title   title of the dialog
     * @param header  header of the dialog
     * @param content content of the dialog
     * @return alert template
     */
    private static Alert getAlertDialog(String style, Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.getDialogPane().getStylesheets().add(style);
        return alert;
    }

    /**
     * This static method which generate Error Alert
     *
     * @param style String variable which describe style (.css)
     * @param msg   String which set to content text
     * @return Alert (with Error title)
     * @see Controller#style
     */
    public static Alert getErrorDialog(String style, String msg) {
        return getAlertDialog(
                style,
                Alert.AlertType.ERROR,
                "Error",
                null,
                msg
        );
    }

    public static Alert getWarningDialog(String style, String msg) {
        return getAlertDialog(
                style,
                Alert.AlertType.WARNING,
                "Warning",
                null,
                msg
        );
    }

    /**
     * This static method which generate confirmation Alert
     *
     * @param style String variable which describe style (.css)
     * @return Alert (with 'Close polygon' title)
     * @see Controller#style
     */
    public static Alert getConfirmSaveDialog(String style) {
        return getAlertDialog(
                style,
                Alert.AlertType.CONFIRMATION,
                "Close polygon",
                "Look carefully, you closed the polygon!",
                "Do you want save changes?"
        );
    }

    /**
     * Generate and return triangulation info dialog
     *
     * @param style String variable which describe style (.css)
     * @param tPol  Triangulated polygon, need for get information
     * @return dialog with extend information about triangulation
     */
    public static Alert getTriangulationInfoDialog(String style, TriangulatedPolygon tPol) {
        String content = "Vertex count: " + tPol.getN() + "\n" +
                "Triangles count: " + tPol.getTrianglesCount() + "\n" +
                "Diagonals count: " + tPol.getDiaglonalesCount() + "\n" +
                "Triangulation cost: " + tPol.getCostSum() + "\n" +
                "Root (solution):" + tPol.getRootNode().getSeg().getDesc();
        return getAlertDialog(style, Alert.AlertType.INFORMATION, "Triangulation info", null, content);
    }

    /**
     * Generic method which create dialog
     * with 2 column of the fields
     *
     * @param style  String variable which describe style (.css)
     * @param title  Dialog title
     * @param fields list of the pairs Key - text of label, Value - some Control
     * @param <T>    return data type
     * @return dialog with 2 column of the fields
     */
    private static <T> Dialog<T> getFieldsDialog(String style, String title, List<Pair<String, Control>> fields) {
        Dialog<T> dialog = new Dialog<>();
        dialog.setTitle(title);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        for (int i = 0; i < fields.size(); i++) {
            grid.add(new Label(fields.get(i).getKey()), 0, i);
            grid.add(fields.get(i).getValue(), 1, i);
        }

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(fields.get(0).getValue()::requestFocus);

        dialog.getDialogPane().getStylesheets().add(style);
        dialog.getDialogPane().getStyleClass().add("main");

        return dialog;
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
        JFXTextField fileName = new JFXTextField("Untitled");
        JFXTextField canvasWidth = new JFXTextField("800");
        JFXTextField canvasHeight = new JFXTextField("600");
        List<Pair<String, Control>> fields = new ArrayList<>(Arrays.asList(
                new Pair<>("Name", fileName),
                new Pair<>("Width:", canvasWidth),
                new Pair<>("Height:", canvasHeight)
        ));

        Dialog<String[]> dialog = getFieldsDialog(style, "Create new ", fields);
        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> dialogButton == createButtonType ?
                new String[]{fileName.getText(), canvasWidth.getText(), canvasHeight.getText()} :
                null);

        return dialog;
    }

    /**
     * Create options dialog
     *
     * @param style  String variable which describe style (.css)
     * @param values current option values
     * @return options dialog which was generated
     */
    public static Dialog<OptionValues> getOptionsDialog(String style, OptionValues values) {

        JFXCheckBox showDescription = new JFXCheckBox();
        showDescription.setSelected(values.showDescription);
        JFXCheckBox showTree = new JFXCheckBox();
        showTree.setSelected(values.showTree);
        JFXTextField pointRadius = new JFXTextField(String.format("%.1f", values.pointRadius));
        JFXTextField lineWidth = new JFXTextField(String.format("%.1f", values.lineWidth));
        JFXTextField treeLineWidth = new JFXTextField(String.format("%.1f", values.treeLineWidth));
        JFXColorPicker background = new JFXColorPicker(values.getPolygonBackground());
        JFXColorPicker vertexColor = new JFXColorPicker(values.getVertexColor());
        JFXColorPicker leafColor = new JFXColorPicker(values.getLeafColor());
        JFXColorPicker nodeColor = new JFXColorPicker(values.getNodeColor());
        JFXColorPicker simpleLineColor = new JFXColorPicker(values.getSimpleLineColor());
        JFXColorPicker diagonalColor = new JFXColorPicker(values.getDiagonalColor());
        List<Pair<String, Control>> fields = new ArrayList<>(Arrays.asList(
                new Pair<>("Show description", showDescription),
                new Pair<>("Show tree", showTree),
                new Pair<>("Point radius:", pointRadius),
                new Pair<>("Line width:", lineWidth),
                new Pair<>("Tree line width:", treeLineWidth),
                new Pair<>("Polygon background: ", background),
                new Pair<>("Vertex color: ", vertexColor),
                new Pair<>("Leaf color: ", leafColor),
                new Pair<>("Node color: ", nodeColor),
                new Pair<>("Line color: ", simpleLineColor),
                new Pair<>("Diagonal color: ", diagonalColor)
        ));

        Dialog<OptionValues> dialog = getFieldsDialog(style, "Options", fields);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            OptionValues res = null;
            if (dialogButton != ButtonType.OK) {
                res = new OptionValues(Arrays.asList(
                        showDescription.isSelected(),
                        showTree.isSelected(),
                        Double.parseDouble(pointRadius.getText()),
                        Double.parseDouble(lineWidth.getText()),
                        Double.parseDouble(treeLineWidth.getText()),
                        ColorUtil.fxToAwt(background.getValue()),
                        ColorUtil.fxToAwt(vertexColor.getValue()),
                        ColorUtil.fxToAwt(leafColor.getValue()),
                        ColorUtil.fxToAwt(nodeColor.getValue()),
                        ColorUtil.fxToAwt(simpleLineColor.getValue()),
                        ColorUtil.fxToAwt(diagonalColor.getValue())
                ));
            }
            return res;
        });

        return dialog;
    }
}