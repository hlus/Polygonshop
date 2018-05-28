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
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        double d = 8;
        gc.fillOval(point.getX() - d / 2, point.getY() - d / 2, d, d);
        drawText(canvas, point.getDesc(), point, 12.0, 12.0);
    }

    public static void drawNodeOfTree(Canvas canvas, Point2D point) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        double d = 7;
        gc.fillOval(point.getX() - d / 2, point.getY() - d / 2, d, d);
    }

    public static void drawLeafOfTree(Canvas canvas, Point2D point) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        double h = 7;
        gc.fillRect(point.getX() - h / 2, point.getY() - h / 2, h, h);
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
        gc.setLineWidth(2.0);
        gc.setStroke(Color.BLACK);
        gc.strokeLine(a.getX(), a.getY(), b.getX(), b.getY());
    }

    public static void drawDashedLine(Canvas canvas, Point2D p1, Point2D p2) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1); // default 1.0
        gc.setLineDashes(3);
        gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        gc.setLineDashes(null);
    }


    public static void drawDefaultDiagonal(Canvas canvas, Segment diagonal, boolean leaf) {
        // draw diagonal
        drawDashedLine(canvas, diagonal.getA(), diagonal.getB());
        Point2D mid = diagonal.getMidpoint();
        if (leaf)
            drawLeafOfTree(canvas, mid);
        else
            drawNodeOfTree(canvas, mid);
        double xshift = (diagonal.getDesc().length() * 5.0) / 2.0;
        drawText(canvas, diagonal.getDesc(), diagonal.getMidpoint(), xshift, 15);
    }

    public static void drawTreeLine(Canvas canvas, Point2D a, Point2D b) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(1.0);
        gc.setStroke(Color.BLACK);
        gc.strokeLine(a.getX(), a.getY(), b.getX(), b.getY());
    }

    /**
     * This method draw default text on canvas
     *
     * @param canvas simple canvas for draw
     * @param text   String which draw
     * @param point  Point2D where
     */
    public static void drawText(Canvas canvas, String text, Point2D point, double xShift, double yShift) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.0);
        gc.strokeText(text, point.getX() - xShift, point.getY() - yShift);
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
