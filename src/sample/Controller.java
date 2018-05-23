package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Optional;

public class Controller {


    // Menu Items
    @FXML
    MenuItem MISave, MISaveAs;
    @FXML
    Menu MIEdit;

    // Main content
    @FXML
    JFXTabPane TPPolygonFiles;

    // Controls
    @FXML
    JFXButton BBuildPolygon, BClearPolygon, BTriangulate, BTriangulateInfo;

    private HashMap<Integer, UIPolygon> tabs;
    private Integer selectedTab;

    @FXML
    public void initialize() {
        selectedTab = null;
        tabs = new HashMap();
        renderAllUI();

        // set event on change tabs ...
        TPPolygonFiles.getSelectionModel().selectedItemProperty().addListener((obs, prev, selected) -> {
            selectedTab = selected != null ? selected.hashCode() : null;
            renderAllUI();
        });
    }

    private void renderAllUIElements() {

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

    private void renderAllUI() {
        renderMenu();
        renderRightSideMenu();
    }

    private void renderMenu() {
        boolean tabsExists = tabs.size() != 0;
        MISave.setDisable(!tabsExists);
        MISaveAs.setDisable(!tabsExists);
        MIEdit.setDisable(!tabsExists);
    }

    private void renderRightSideMenu() {
        // Default disable all right menu
        BBuildPolygon.setText("Build polygon");
        BBuildPolygon.setDisable(true);
        BClearPolygon.setDisable(true);
        BTriangulate.setDisable(true);
        BTriangulateInfo.setDisable(true);
        if (selectedTab != null) {
            UIPolygon polygonUI = tabs.get(selectedTab);
            if (!polygonUI.isBuilt()) {
                BBuildPolygon.setDisable(false);
                BBuildPolygon.setText(polygonUI.isBuilding() ? "Finish building" : "Build polygon");
            } else {
                BClearPolygon.setDisable(false);
            }
        }
    }

    private void addNewTab(Tab tab, UIPolygon polygonUI) {
        tab.setOnCloseRequest(e -> onCloseTab(tab.hashCode()));

        tabs.put(tab.hashCode(), polygonUI);
        // add new Tab at the front
        TPPolygonFiles.getTabs().add(0, tab);
        // select this tab
        TPPolygonFiles.getSelectionModel().select(0);
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
            addNewTab(polygonTab.getTab(), polygonTab);
        });
    }

    private void onCloseTab(int tabHashCode) {
        selectedTab = null;
        tabs.remove(tabHashCode);
        renderAllUI();
    }

    private File createAndShowFileChooser(String type, String initialFileName) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(type);
        chooser.setInitialDirectory(new File("."));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Polygon files (*.pol)", "*.pol"));
        chooser.setInitialFileName(initialFileName);
        Window ownerWindow = TPPolygonFiles.getScene().getWindow();
        return type.contains("Save") ?
                chooser.showSaveDialog(ownerWindow) :
                chooser.showOpenDialog(ownerWindow);
    }

    @FXML
    private void onOpenFile() {
        File file = createAndShowFileChooser("Open Polygon", "");
        if (file != null) {
            try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(file))) {
                UIPolygon newPolygon = new UIPolygon((UIPolygon) objIn.readObject());
                addNewTab(newPolygon.getTab(), newPolygon);
            } catch (IOException i) {
                i.printStackTrace();
            } catch (ClassNotFoundException c) {
                System.out.println("Employee class not found");
                c.printStackTrace();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void onSave(ActionEvent actionEvent) {
        UIPolygon polygonUI = tabs.get(selectedTab);
        File file = createAndShowFileChooser("Save Polygon", polygonUI.getFileName());
        if (file != null) {
            try {
                polygonUI.onSave(file);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void onSaveAs(ActionEvent actionEvent) {
    }

    @FXML
    private void onShowAbout() {
    }

    @FXML
    private void onExit() {
        ((Stage) TPPolygonFiles.getScene().getWindow()).close();
    }

    public void onBuildPolygon(ActionEvent actionEvent) {
        UIPolygon polygonUI = tabs.get(selectedTab);
        polygonUI.onBuildOrEndBuilt();
        renderRightSideMenu();
    }

    public void onClearPolygon(ActionEvent actionEvent) {
        UIPolygon polygonUI = tabs.get(selectedTab);
        polygonUI.onClearPolygon();
        renderRightSideMenu();
    }

    public void onTriangulatePolygon(ActionEvent actionEvent) {
    }

    public void onTriangulateInfo(ActionEvent actionEvent) {
    }


}
