package sample;

import com.jfoenix.controls.JFXTabPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.Optional;

import static javafx.scene.input.KeyCode.*;


public class Controller {


    // Menu Items
    @FXML
    MenuItem MISave, MISaveAs;
    @FXML
    Menu MIEdit;

    @FXML
    JFXTabPane TPPolygonFiles;
    @FXML
    Button BAddPoints, BBuildPolygon, BRemovePolygon;

    private HashMap<Integer, UIPolygon> tabs;
    private Integer selectedTab;

    @FXML
    public void initialize() {
        selectedTab = null;
        tabs = new HashMap();
        renderUI();
        // set event on change tabs ...
        TPPolygonFiles.getSelectionModel().selectedItemProperty().addListener((obs, prev, selected) -> {
            selectedTab = selected != null ? selected.hashCode() : null;
            renderUI();
            //System.out.println(obs);
            //System.out.println("Prev: " + prev);
            //System.out.println("Selected: " + selected.hashCode());
        });
    }

    private void renderUIElements() {

    }

    /**
     * Handle action related to input (in this case specifically only responds to
     * keyboard event CTRL-A).
     *
     * @param event Input event.
     */
    @FXML
    private void handleKeyInput(final InputEvent event) {
        if (event instanceof KeyEvent) {
            final KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.isControlDown()) {
                switch (keyEvent.getCode()) {
                    case N:
                        onCreateNewFile();
                        break;
                    case O:
                        onOpenFile();
                        break;
                    case A:
                        onShowAbout();
                        break;
                    case E:
                        onExit();
                    default:
                        break;
                }
            }
        }
    }

    private void renderUI() {
        boolean tabsExists = tabs.size() != 0;
        MISave.setDisable(!tabsExists);
        MISaveAs.setDisable(!tabsExists);
        MIEdit.setDisable(!tabsExists);

        boolean polygonSelected = selectedTab != null;
        BAddPoints.setDisable(!polygonSelected);
        BBuildPolygon.setDisable(!polygonSelected);
        BRemovePolygon.setDisable(!polygonSelected);
    }

    @FXML
    private void onCreateNewFile() {
        Dialog createDialog = DialogHelper.getCreateNewDialog(this.getClass().getResource("main.css").toExternalForm());
        createDialog.initOwner(TPPolygonFiles.getScene().getWindow());
        Optional<String[]> result = createDialog.showAndWait();
        result.ifPresent(res -> {
            String fileName = res[0];
            double width = Double.parseDouble(res[1]);
            double height = Double.parseDouble(res[2]);

            UIPolygon polygonTab = new UIPolygon(
                    fileName,
                    width,
                    height
            );

            Tab newTab = polygonTab.getTab();
            newTab.setOnCloseRequest(e -> {
                System.out.println("I close the: " + newTab.hashCode());
                tabs.remove(newTab.hashCode());
                renderUI();
            });

            tabs.put(newTab.hashCode(), polygonTab);
            // add new Tab at the front
            TPPolygonFiles.getTabs().add(0, newTab);
            // select this tab
            TPPolygonFiles.getSelectionModel().select(0);
        });
    }

    @FXML
    private void onOpenFile() {
    }

    @FXML
    private void onShowAbout() {
    }

    @FXML
    private void onExit() {
        ((Stage) TPPolygonFiles.getScene().getWindow()).close();
    }
}
