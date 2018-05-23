package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Optional;

/**
 * Controller for sample.fxml view
 *
 * @author hlus
 * @version 2.0
 */
public class Controller {

    /**
     * Binding menu items
     */
    @FXML
    MenuItem MISave;
    @FXML
    Menu MIEdit;

    /**
     * Binding TabPane node
     */
    @FXML
    JFXTabPane TPPolygonFiles;

    /**
     * Binding right-side menu buttons
     */
    @FXML
    JFXButton BBuildPolygon, BClearPolygon, BTriangulate, BTriangulateInfo;

    /**
     * Property which contain style string
     * for dialogs windows
     */
    private String style;

    /**
     * Property which contain all tabs
     * tabs - contain:
     * key -> hashcode of tab UIPolygon (getTab().hashCode())
     * value -> UIPolygon object
     *
     * @see UIPolygon
     * @see UIPolygon#getTab()
     * @see Tab#hashCode()
     */
    private HashMap<Integer, UIPolygon> tabs;
    /**
     * Property which contain hashcode of selected tab
     */
    private Integer selectedTab;

    /**
     * Initialize method call firstly
     * And initialize properties with initial values
     */
    @FXML
    public void initialize() {
        selectedTab = null;
        tabs = new HashMap();
        style = this.getClass().getResource("main.css").toExternalForm();
        renderAllUI();

        // set event on change tabs ...
        TPPolygonFiles.getSelectionModel().selectedItemProperty().addListener((obs, prev, selected) -> {
            selectedTab = selected != null ? selected.hashCode() : null;
            renderAllUI();
        });
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
                    case S:
                        onSave();
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

    // -------------------------------- RENDER UI ----------------------------------

    /**
     * Method which render UI elements
     * namely top menu, and right-side menu
     */
    private void renderAllUI() {
        renderMenu();
        renderRightSideMenu();
    }

    /**
     * Method which render top menu UI elements
     */
    private void renderMenu() {
        boolean tabsExists = tabs.size() != 0;
        MISave.setDisable(!tabsExists);
        MIEdit.setDisable(!tabsExists);
    }

    /**
     * Method which render right-side menu UI elements
     */
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

    // -----------------------------------------------------------------------------

    // ----------------------------- TOP MENU HANDLERS -----------------------------

    /**
     * Binding method handle on click create new file
     */
    @FXML
    private void onCreateNewFile() {
        Dialog createDialog = DialogHelper.getCreateNewDialog(this.style);
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

    /**
     * This method is binding when user click
     * to open some file.
     * method deserialize file and create UIPolygon object,
     * and add him to tabs
     *
     * @see Controller#tabs
     */
    @FXML
    private void onOpenFile() {
        File file = createAndShowFileChooser("Open Polygon", "");
        if (file != null) {
            try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(file))) {
                UIPolygon newPolygon = new UIPolygon((UIPolygon) objIn.readObject());
                addNewTab(newPolygon.getTab(), newPolygon);
            } catch (IOException i) {
                showErrorMessage(i.getMessage());
            } catch (ClassNotFoundException c) {
                showErrorMessage("Wrong file!!!");
            } catch (Exception e) {
                showErrorMessage(e.getMessage());
            }
        }
    }

    /**
     * This method is binding when user click
     * to open some file.
     * method deserialize file and create UIPolygon object,
     * and add him to tabs
     */
    @FXML
    private void onSave() {
        if (selectedTab != null) {
            UIPolygon polygonUI = tabs.get(selectedTab);
            File file = createAndShowFileChooser("Save Polygon", polygonUI.getFileName());
            if (file != null) {
                try {
                    polygonUI.onSave(file);
                } catch (Exception e) {
                    showErrorMessage(e.getMessage());
                }
            }
        } else {
            showErrorMessage("You are not select a file!");
        }
    }

    /**
     * This method is handle when user click on Exit
     */
    @FXML
    private void onExit() {
        // TODO : Add handle if tabs.size() != 0 ask fow save polygons
        ((Stage) TPPolygonFiles.getScene().getWindow()).close();
    }

    /**
     * This method show about information for user
     */
    @FXML
    private void onShowAbout() {
        // TODO : add about page implementation
        throw new NotImplementedException();
    }

    // -----------------------------------------------------------------------------


    // ------------------------- RIGHT-SIDE MENU HANDLERS --------------------------

    /**
     * This method handler on click BuildPolygon,
     * call onBuildOrEndBuilt() method of UIPolygon
     * selected object
     *
     * @see UIPolygon
     * @see UIPolygon#onBuildOrEndBuilt()
     */
    @FXML
    private void onBuildPolygon() {
        UIPolygon polygonUI = tabs.get(selectedTab);
        try {
            polygonUI.onBuildOrEndBuilt();
        } catch (Exception e) {
            showErrorMessage(e.getMessage());
        }
        renderRightSideMenu();
    }

    /**
     * This method handler on click Clear polygon,
     * call onClearPolygon() method of UIPolygon
     * selected object
     */
    @FXML
    private void onClearPolygon() {
        UIPolygon polygonUI = tabs.get(selectedTab);
        polygonUI.onClearPolygon();
        renderRightSideMenu();
    }

    /**
     * Not implemented yet
     */
    @FXML
    private void onTriangulatePolygon() {
        // TODO : implement click on Triangulate
        throw new NotImplementedException();
    }

    /**
     * Not implemented yet
     */
    @FXML
    public void onTriangulateInfo() {
        // TODO : implement click on triangulate info
        throw new NotImplementedException();
    }

    // -----------------------------------------------------------------------------

    // --------------------------- CONTROLLER LOGIC --------------------------------

    /**
     * Method add new tab to the tabs property
     * and focused user on this tab
     *
     * @param tab       tab to add
     * @param polygonUI of this tab
     * @see Controller#tabs
     */
    private void addNewTab(Tab tab, UIPolygon polygonUI) {
        tab.setOnCloseRequest(e -> onCloseTab(tab.hashCode()));

        tabs.put(tab.hashCode(), polygonUI);
        // add new Tab at the front
        TPPolygonFiles.getTabs().add(0, tab);
        // select this tab
        TPPolygonFiles.getSelectionModel().select(0);
    }

    /**
     * This method is handle when some tab is close ...
     *
     * @param tabHashCode hashcode of tab which be closed
     * @see Controller#addNewTab(Tab, UIPolygon)
     */
    private void onCloseTab(int tabHashCode) {
        Alert confirmDialog = DialogHelper.getConfirmSaveDialog(this.style);
        confirmDialog.initOwner(TPPolygonFiles.getScene().getWindow());
        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.get() == ButtonType.OK)
            onSave();

        selectedTab = null;
        tabs.remove(tabHashCode);
        renderAllUI();
    }

    /**
     * This method create and show some file choose
     * which set default options, end recognize type
     * for show save or open file dialog
     *
     * @param type            string for recognize save or open file dialog
     * @param initialFileName string for set initial file name
     * @return File object
     */
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

    /**
     * This method show error message to user
     *
     * @param errMsg
     */
    private void showErrorMessage(String errMsg) {
        Alert errorDialog = DialogHelper.getErrorDialog(this.style, errMsg);
        errorDialog.initOwner(TPPolygonFiles.getScene().getWindow());
        errorDialog.showAndWait();
    }

    // -----------------------------------------------------------------------------
}
