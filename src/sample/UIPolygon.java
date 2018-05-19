package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class UIPolygon {

    private Canvas canvas;
    private String fileName;
    private Polygon polygon;
    private List<Point2D> buildPoints;

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

    public Tab getTab() {
        Tab tab = new Tab();
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
            DrawAssistant.drawDefaultPoint(canvas, points.get(i));
            DrawAssistant.drawDefaultLine(canvas, points.get(i), points.get(i + 1));
        }
        DrawAssistant.drawDefaultPoint(canvas, points.get(points.size() - 1));
        DrawAssistant.drawDefaultLine(canvas, points.get(0), points.get(points.size() - 1));
    }

    public void onClearPolygon() {
        polygon = null;
        isTriangulate = false;
        DrawAssistant.fillCanvas(canvas, Color.GREY);
    }
}
