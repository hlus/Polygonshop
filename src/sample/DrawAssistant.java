package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class just help with draw
 * use canvas, Point2D, Color
 *
 * @author hlus
 * @version 2.0
 */
public class DrawAssistant {

    /**
     * This method draw default point on canvas
     *
     * @param canvas simple canvas for draw
     * @param point  Point2D for use coordinates
     */
    public static void drawDefaultPoint(Canvas canvas, Point2D point) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        double r = 2.0;
        gc.strokeOval(point.getX(), point.getY(), r, r);
    }

    /**
     * This method draw default line on canvas
     *
     * @param canvas simple canvas for draw
     * @param a      Point2D 'from'
     * @param b      Point2D 'to'
     */
    public static void drawDefaultLine(Canvas canvas, Point2D a, Point2D b) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.strokeLine(a.getX(), a.getY(), b.getX(), b.getY());
    }

    /**
     * This method draw default text on canvas
     *
     * @param canvas simple canvas for draw
     * @param text   String which draw
     * @param point  Point2D where
     * @param shift  double shift of point
     */
    public static void drawText(Canvas canvas, String text, Point2D point, double shift) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.strokeText(text, point.getX(), point.getY() + shift);
    }

    /**
     * Color canvas all with one color
     *
     * @param canvas simple canvas for draw
     * @param color  Color for fill canvas
     */
    public static void fillCanvas(Canvas canvas, Color color) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
