package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UIPolygon implements Serializable {

    private transient Canvas canvas;
    private transient Tab tab;
    private String fileName;
    private Polygon polygon;
    private transient List<Point2D> buildPoints;
    private double width;
    private double height;

    public void setFileName(String fileName) {
        this.fileName = fileName.replace(".pol", "");
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public String getFileName() {
        return fileName;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    // Options
    private boolean isTriangulate;  // Polygon triangulate ?

    public boolean isBuilding() {
        return buildPoints != null;
    }

    public boolean isBuilt() {
        return polygon != null;
    }

    public boolean isTriangulate() {
        return isTriangulate;
    }

    public UIPolygon(UIPolygon p) {
        this(p.getFileName(), p.getWidth(), p.getHeight());
        this.polygon = p.getPolygon();
        redrawPolygon();
    }


    public UIPolygon(String fileName, double width, double height) {
        setFileName(fileName);
        isTriangulate = false;
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

        DrawAssistant.fillCanvas(canvas, Color.GRAY);
    }

    public void onSave(File file) {
        try {
            setFileName(file.getName());
            FileOutputStream fileOut = new FileOutputStream(file.getPath());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            tab.setText(fileName);
        } catch (IOException i) {
            i.printStackTrace();
        }

    }

    public Tab getTab() {
        tab = new Tab();
        tab.setText(fileName);
        ScrollPane scrollPane = new ScrollPane(canvas);
        tab.setContent(scrollPane);
        return tab;
    }

    public void onBuildOrEndBuilt() {
        if (isBuilding()) {
            polygon = new Polygon(buildPoints);
            buildPoints = null;
            redrawPolygon();
        } else {
            buildPoints = new ArrayList<>();
        }
    }

    private void redrawPolygon() {
        DrawAssistant.fillCanvas(canvas, Color.GRAY);
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

    public void onClearPolygon() {
        polygon = null;
        isTriangulate = false;
        DrawAssistant.fillCanvas(canvas, Color.GREY);
    }


}
