package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawAssistant {

    public static void drawDefaultPoint(Canvas canvas, Point2D point) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        double r = 2.0;
        gc.strokeOval(point.getX(), point.getY(), r, r);
    }

    public static void fillCanvas(Canvas canvas, Color color) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public static void drawDefaultLine(Canvas canvas, Point2D a, Point2D b){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.strokeLine(a.getX(), a.getY(), b.getX(), b.getY());
    }

}
