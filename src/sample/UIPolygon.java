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

    public UIPolygon(String fileName, double width, double height) {
        this.fileName = fileName;
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

    public UIPolygon(File file) {
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            UIPolygon pol = (UIPolygon) in.readObject();

            this.polygon = pol.getPolygon();
            this.width = pol.getWidth();
            this.height = pol.getHeight();
            this.fileName = pol.getFileName();
            isTriangulate = false;

            canvas = new Canvas(width, height);
            canvas.setOnMouseClicked(e -> {
                if (isBuilding()) {
                    Point2D clickPoint = new Point2D(e.getX(), e.getY());
                    DrawAssistant.drawDefaultPoint(canvas, clickPoint);
                    buildPoints.add(clickPoint);
                }
            });
            in.close();
            fileIn.close();

            DrawAssistant.fillCanvas(canvas, Color.GRAY);
            redrawPolygon();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }
    }

    public void onSave(File file) {
        try {
            fileName = file.getName();
            FileOutputStream fileOut = new FileOutputStream(file.getPath());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            tab.setText(fileName);
            System.out.printf("Serialized data is saved in " + file.getPath());
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
        List<Point2D> points = polygon.getPoints();
        for (int i = 0; i < points.size() - 1; i++) {
            DrawAssistant.drawText(canvas, "V" + i, points.get(i));
            DrawAssistant.drawDefaultPoint(canvas, points.get(i));
            DrawAssistant.drawDefaultLine(canvas, points.get(i), points.get(i + 1));
        }
        DrawAssistant.drawText(canvas, "V" + (points.size() - 1), points.get(points.size() - 1));
        DrawAssistant.drawDefaultPoint(canvas, points.get(points.size() - 1));
        DrawAssistant.drawDefaultLine(canvas, points.get(0), points.get(points.size() - 1));
    }

    public void onClearPolygon() {
        polygon = null;
        isTriangulate = false;
        DrawAssistant.fillCanvas(canvas, Color.GREY);
    }
}
