package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * class which describe UI part of polygon
 *
 * @author hlus
 * @version 2.0
 */
public class UIPolygon implements Serializable {

    private transient static Color canvasBackground = Color.GRAY;   // color of background canvas
    private transient Tab tab;                                      // The tab that contains the canvas to display polygon
    private transient Canvas canvas;                                // canvas to display the polygon
    private transient List<Point2D> buildPoints;                    // intermediate points for constructing a polygon
    private double width;                                           // width of canvas (work area width)
    private double height;                                          // height of canvas (work area height)
    private String fileName;                                        // file name var (for save or change)
    private Polygon polygon;                                        // polygon object describes polygon
    private boolean isTriangulate;                                  // state of polygon is triangulate or not

    /**
     * Simple getter for tab property
     *
     * @return Tab
     */
    public Tab getTab() {
        return tab;
    }

    /**
     * Simple getter for canvas property
     *
     * @return Canvas
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Simple getter for width property
     *
     * @return double which describe width of canvas (property)
     */
    public double getWidth() {
        return width;
    }

    /**
     * Simple getter for height property
     *
     * @return double which describe height of canvas (property)
     */
    public double getHeight() {
        return height;
    }

    /**
     * Simple getter for fileName property
     *
     * @return String which describe file name of our polygon object (property)
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Simple getter for polygon property
     *
     * @return Polygon object which describe our polygon
     * @see Polygon
     */
    public Polygon getPolygon() {
        return polygon;
    }

    /**
     * Simple setter for fileName property.
     * Main destination of this setter is remove '.pol' ...
     *
     * @param fileName param which can be not pure (contain '.pol')
     */
    public void setFileName(String fileName) {
        this.fileName = fileName.replace(".pol", "");
    }

    /**
     * This method gives us to understand whether to be
     * in the building of our polygon
     *
     * @return boolean which describe understand whether to be in the construction ?
     */
    public boolean isBuilding() {
        return buildPoints != null;
    }

    /**
     * This method gives us to understand 'does the polygon exist?'
     *
     * @return boolean that answers the question 'does the polygon exist?'
     */
    public boolean isBuilt() {
        return polygon != null;
    }

    /**
     * TODO : In the future, it may not exist (boolean variable)
     *
     * @return boolean which describe 'polygon already is triangulate?'
     */
    public boolean isTriangulate() {
        return isTriangulate;
    }

    /**
     * Constructor for class UIPolygon
     * This constructor with not deep copy
     * because (polygon is pointer)
     *
     * @param p other UIPolygon object (from deserialize)
     */
    public UIPolygon(UIPolygon p) {
        this(p.getFileName(), p.getWidth(), p.getHeight());
        this.polygon = p.getPolygon();
        redrawPolygon();
    }

    /**
     * Constructor for class UIPolygon
     * @param fileName file name of our polygon file
     * @param width    width of canvas
     * @param height   height of canvas
     */
    public UIPolygon(String fileName, double width, double height) {
        setFileName(fileName);
        this.width = width;
        this.height = height;
        canvas = new Canvas(width, height);

        canvas.setOnMouseClicked(e -> {
            if (isBuilding()) {
                Point2D clickPoint = new Point2D(e.getX(), e.getY());
                DrawAssistant.drawDefaultPoint(canvas, clickPoint);
                buildPoints.add(clickPoint);
            }
        });

        tab = new Tab();
        tab.setText(fileName);
        ScrollPane scrollPane = new ScrollPane(canvas);
        tab.setContent(scrollPane);

        onClearPolygon();
    }

    /**
     * This method serialize the polygon file.
     * @param file this argument need for get a name of save file and path to save
     * @throws Exception path can be wrong !!!
     */
    public void onSave(File file) throws Exception {
        try (ObjectOutputStream outObj = new ObjectOutputStream(new FileOutputStream(file.getPath()))) {
            setFileName(file.getName());
            tab.setText(fileName);

            outObj.writeObject(this);

        } catch (Exception e) {
            throw new Exception("Cannot get access for path to save!");
        }
    }

    /**
     * This method call when we wanna begin building
     * of our polygon, or when end building polygon.
     * @throws Exception
     */
    public void onBuildOrEndBuilt() throws Exception {
        if (isBuilding()) {
            if (buildPoints.size() < 3) {
                buildPoints = null;
                redrawPolygon();
                throw new Exception("You added not enough points to build a polygon!");
            }
            polygon = new Polygon(buildPoints);
            buildPoints = null;
            redrawPolygon();
        } else {
            buildPoints = new ArrayList<>();
        }
    }

    /**
     * Method which redraw polygon at canvas
     * depending on the polygon property
     */
    private void redrawPolygon() {
        DrawAssistant.fillCanvas(canvas, canvasBackground);
        if (polygon != null && polygon.getPoints().size() != 0) {
            List<Point2D> points = polygon.getPoints();
            for (int i = 0; i < points.size() - 1; i++) {
                DrawAssistant.drawText(canvas, "V" + i, points.get(i), 10);
                DrawAssistant.drawDefaultPoint(canvas, points.get(i));
                DrawAssistant.drawDefaultLine(canvas, points.get(i), points.get(i + 1));
            }
            DrawAssistant.drawText(canvas, "V" + (points.size() - 1), points.get(points.size() - 1), 10);
            DrawAssistant.drawDefaultPoint(canvas, points.get(points.size() - 1));
            DrawAssistant.drawDefaultLine(canvas, points.get(0), points.get(points.size() - 1));
        }
    }

    /**
     * Method clear polygon data (polygon property)
     * And redraw canvas
     */
    public void onClearPolygon() {
        polygon = null;
        isTriangulate = false;
        redrawPolygon();
    }
}
