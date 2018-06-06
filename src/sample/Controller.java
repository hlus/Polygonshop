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
import sample.Assistants.DialogAssistant;
import sample.Assistants.DrawAssistant;
import sample.Assistants.OptionValues;
import sample.Model.Polygon;
import sample.Model.UIPolygon;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.util.HashMap;
import java.util.Optional;

/**
 * Controller for sample.fxml view
 *
 * @author hlus
 * @version 2.2
 */
public class Controller {

    /**
     * Binding menu items
     */
    @FXML
    private MenuItem MISave;
    @FXML
    private Menu MIEdit;

    /**
     * Binding TabPane node
     */
    @FXML
    private JFXTabPane TPPolygonFiles;

    /**
     * Binding right-side menu buttons
     */
    @FXML
    private JFXButton BBuildPolygon, BClearPolygon, BTriangulate, BTriangulateInfo;

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
    private void initialize() {
        selectedTab = null;
        tabs = new HashMap();
        style = this.getClass().getResource("main.css").toExternalForm();
        loadOptions();
        renderAllUI();

        // set event on change tabs ...
        TPPolygonFiles.getSelectionModel().selectedItemProperty().addListener((obs, prev, selected) -> {
            selectedTab = selected != null ? selected.hashCode() : null;
            renderAllUI();
        });
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
            UIPolygon polygonUI = getSelectedPolygon();
            if (!polygonUI.isBuilt()) {
                BBuildPolygon.setDisable(false);
                BBuildPolygon.setText(polygonUI.isBuilding() ? "Finish building" : "Build polygon");
            } else {
                if (polygonUI.isTriangulate())
                    BTriangulateInfo.setDisable(false);
                else
                    BTriangulate.setDisable(false);
                BClearPolygon.setDisable(false);
            }
        }
    }

    // -----------------------------------------------------------------------------

    // ----------------------------- TOP MENU HANDLERS -----------------------------

    /**
     * Handle action related to input (in this case specifically only responds to
     * keyboard event CTRL-KEY).
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
                    case Q:
                        onExit();
                    default:
                        break;
                }
            }
        }
    }

    /**
     * Binding method handle on click create new file
     */
    @FXML
    private void onCreateNewFile() {
        Dialog createDialog = DialogAssistant.getCreateNewDialog(this.style);
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
            UIPolygon polygonUI = getSelectedPolygon();
            File file = createAndShowFileChooser("Save Polygon", polygonUI.getFileName());
            if (file != null) {
                try {
                    polygonUI.savePolygon(file);
                } catch (Exception e) {
                    showErrorMessage(e.getMessage());
                }
            }
        } else {
            showErrorMessage("You are not select a file!");
        }
    }

    /**
     * This method is handle when user click on Options
     *
     * @param actionEvent
     */
    @FXML
    private void onOptions(ActionEvent actionEvent) {
        Dialog<OptionValues> optionsDialog = DialogAssistant.getOptionsDialog(this.style, DrawAssistant.options);
        optionsDialog.initOwner(TPPolygonFiles.getScene().getWindow());

        Optional<OptionValues> result = optionsDialog.showAndWait();
        result.ifPresent(res -> {
            DrawAssistant.options = res;
            tabs.values().forEach(UIPolygon::redrawPolygon);
        });
    }

    /**
     * This method is handle when user click on Exit
     */
    @FXML
    private void onExit() {
        saveOptions();
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
     */
    @FXML
    private void onBuildPolygon() {
        UIPolygon polygonUI = getSelectedPolygon();
        try {
            if (polygonUI.isBuilding()) {
                polygonUI.endBuildPol();
                if (polygonUI.getPolygon().getPolygonType() != Polygon.polygonType.Convex) {
                    Alert alert = DialogAssistant.getWarningDialog(
                            style,
                            polygonUI.getPolygon().getPolygonType() == Polygon.polygonType.Collinear ?
                                    "Polygon are collinear!" : "Polygon are Concave"
                    );
                    alert.initOwner(TPPolygonFiles.getScene().getWindow());
                    alert.showAndWait();
                }

            } else
                polygonUI.beginBuildPol();
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
        UIPolygon polygonUI = getSelectedPolygon();
        polygonUI.onClearPolygon();
        renderRightSideMenu();
    }

    /**
     * Call triangulation request to UIPolygon obj.
     */
    @FXML
    private void onTriangulatePolygon() {
        UIPolygon polygonUI = getSelectedPolygon();
        polygonUI.triangulatePolygon();
        renderRightSideMenu();
    }

    /**
     * Show dialog which describe
     * additional information
     * about triangulation
     */
    @FXML
    private void onTriangulateInfo() {
        Alert alert = DialogAssistant.getTriangulationInfoDialog(this.style, getSelectedPolygon().getTriangulatedPol());
        alert.initOwner(TPPolygonFiles.getScene().getWindow());
        alert.showAndWait();
    }

    // -----------------------------------------------------------------------------

    // --------------------------- CONTROLLER LOGIC --------------------------------

    /**
     * Get access for selected UIPolygon object
     *
     * @return UIPolygon object
     */
    private UIPolygon getSelectedPolygon() {
        return tabs.get(selectedTab);
    }

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
        Alert confirmDialog = DialogAssistant.getConfirmSaveDialog(this.style);
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
     * Loads options from a file if the file exists
     */
    private void loadOptions() {
        try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(new File("config")))) {
            OptionValues options = new OptionValues((OptionValues) objIn.readObject());
            DrawAssistant.options = options;
        } catch (Exception e) {
            DrawAssistant.options = new OptionValues();
        }
    }

    /**
     * Saves options in to the file
     */
    private void saveOptions() {
        try (ObjectOutputStream outObj = new ObjectOutputStream(new FileOutputStream(new File("config")))) {
            outObj.writeObject(DrawAssistant.options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method show error message to user
     *
     * @param errMsg
     */
    private void showErrorMessage(String errMsg) {
        Alert errorDialog = DialogAssistant.getErrorDialog(this.style, errMsg);
        errorDialog.initOwner(TPPolygonFiles.getScene().getWindow());
        errorDialog.showAndWait();
    }

    // -----------------------------------------------------------------------------
}
