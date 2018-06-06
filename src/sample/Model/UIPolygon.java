package sample.Model;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import sample.Assistants.DrawAssistant;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which describe UI part of polygon
 *
 * @author hlus
 * @version 2.5
 */
public class UIPolygon implements Serializable {

    private transient Tab tab;                                      // tab which contains the canvas to display polygon
    private transient Canvas canvas;                                // canvas to display the polygon
    private transient List<Point2D> buildPoints;                    // intermediate points for constructing a polygon
    private double width;                                           // width of canvas (work area width)
    private double height;                                          // height of canvas (work area height)
    private String fileName;                                        // file name var (for save or change)
    private Polygon polygon;                                        // polygon object describes polygon
    private TriangulatedPolygon tPol;                               // triangulation object describe triangulation info

    /**
     * Simple getter for tab property
     *
     * @return Tab
     */
    public Tab getTab() {
        return tab;
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

    public TriangulatedPolygon getTriangulatedPol() {
        return tPol;
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
     * @return boolean which describe 'polygon already is triangulate?'
     */
    public boolean isTriangulate() {
        return tPol != null;
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
        this.tPol = p.tPol;
        redrawPolygon();
    }

    /**
     * Constructor for class UIPolygon
     *
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
                int verIndex = buildPoints.size();
                Point2D clickPoint = new Point2D(e.getX(), e.getY(), "V" + verIndex);
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
     *
     * @param file this argument need for get a name of save file and path to save
     * @throws Exception path can be wrong !!!
     */
    public void savePolygon(File file) throws Exception {
        try (ObjectOutputStream outObj = new ObjectOutputStream(new FileOutputStream(file.getPath()))) {
            setFileName(file.getName());
            tab.setText(fileName);

            outObj.writeObject(this);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Cannot get access for path to save!");
        }
    }

    /**
     * This method call for start build polygon
     */
    public void beginBuildPol() {
        buildPoints = new ArrayList<>();
    }

    /**
     * This method end building polygon
     * and redraw canvas ...
     *
     * @throws Exception throw when building points less
     *                   then 3
     */
    public void endBuildPol() throws Exception {
        if (buildPoints.size() < 3) {
            buildPoints = null;
            redrawPolygon();
            throw new Exception("You added not enough points to build a polygon!");
        }
        polygon = new Polygon(buildPoints);
        buildPoints = null;
        redrawPolygon();
    }

    /**
     * This method create object TriangulatePolygon
     * which describe is triangulated polygon info
     */
    public void triangulatePolygon() {
        tPol = new TriangulatedPolygon(this.polygon);
        redrawPolygon();
    }

    /**
     * Method which redraw polygon at canvas
     * depending on the polygon property
     */
    public void redrawPolygon() {
        DrawAssistant.fillCanvas(canvas);
        if (polygon != null && polygon.getPoints().size() != 0) {
            List<Point2D> points = polygon.getPoints();
            for (int i = 0; i < points.size() - 1; i++) {
                DrawAssistant.drawDefaultLine(canvas, points.get(i), points.get(i + 1));
                DrawAssistant.drawDefaultPoint(canvas, points.get(i));
            }
            DrawAssistant.drawDefaultLine(canvas, points.get(0), points.get(points.size() - 1));
            DrawAssistant.drawDefaultPoint(canvas, points.get(points.size() - 1));
            if (tPol != null) {
                drawCostCell(tPol.getRootNode());
            }
        }
    }

    /**
     * This is recursive method for draw
     * all nodes of triangulation tree
     *
     * @param cell is simple node of cost matrix
     * @see TriangulatedPolygon
     */
    private void drawCostCell(CostCell cell) {
        if (cell == null || cell.getSeg() == null) return;
        DrawAssistant.drawDefaultDiagonal(canvas, cell.getSeg(), cell.getSubNodes() == null);
        if (cell.getSubNodes() != null)
            for (CostCell subCell : cell.getSubNodes()) {
                drawCostCell(subCell);
                if (DrawAssistant.options.showTree)
                    DrawAssistant.drawTreeLine(canvas, cell.getSeg().getMidpoint(), subCell.getSeg().getMidpoint());
            }
    }

    /**
     * Method clear polygon data (polygon property)
     * And redraw canvas
     */
    public void onClearPolygon() {
        polygon = null;
        tPol = null;
        redrawPolygon();
    }
}
