package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class just help with draw
 * use canvas, Point2D, Color
 *
 * @author hlus
 * @version 2.1
 */
public class DrawAssistant {

    public static OptionValues options = new OptionValues(
            true,
            true,
            4.0,
            2.0,
            1.0,
            Color.GRAY,
            Color.BLACK,
            Color.BLACK,
            Color.BLACK,
            Color.BLACK,
            Color.BLACK
    );

    /**
     * Draw default point on canvas
     *
     * @param canvas simple canvas for draw
     * @param point  Point2D describe where is draw point
     */
    public static void drawDefaultPoint(Canvas canvas, Point2D point) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(options.vertexColor);
        //gc.setStroke(Color.BLACK);
        gc.fillOval(point.getX() - options.pointRadius,
                point.getY() - options.pointRadius,
                options.pointRadius * 2,
                options.pointRadius * 2
        );
        drawText(canvas, point.getDesc(), point, 12.0, 12.0);
    }

    /**
     * Draw node of triangulation tree
     *
     * @param canvas simple canvas for draw
     * @param point  Point2D describe where is draw node
     */
    public static void drawNodeOfTree(Canvas canvas, Point2D point) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(options.nodeColor);
        double d = 7;
        gc.fillOval(point.getX() - d / 2, point.getY() - d / 2, d, d);
    }

    /**
     * Draw leaf of triangulation tree
     *
     * @param canvas simple canvas for draw
     * @param point  Point2D describe where is draw leaf
     */
    public static void drawLeafOfTree(Canvas canvas, Point2D point) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(options.leafColor);
        double h = 7;
        gc.fillRect(point.getX() - h / 2, point.getY() - h / 2, h, h);
    }

    /**
     * Draw default line on canvas
     *
     * @param canvas simple canvas for draw
     * @param a      Point2D 'from'
     * @param b      Point2D 'to'
     */
    public static void drawDefaultLine(Canvas canvas, Point2D a, Point2D b) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(options.lineWidth);
        gc.setStroke(options.simpleLineColor);
        gc.strokeLine(a.getX(), a.getY(), b.getX(), b.getY());
    }

    /**
     * Draw a dashed line
     *
     * @param canvas simple canvas for draw
     * @param p1     Point2D 'from'
     * @param p2     Point2D 'to'
     */
    public static void drawDashedLine(Canvas canvas, Point2D p1, Point2D p2) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(options.diagonalColor);
        gc.setLineWidth(options.treeLineWidth);
        gc.setLineDashes(3);
        gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        gc.setLineDashes(null);
    }

    /**
     * Draw diagonal of polygon
     * after triangulation
     *
     * @param canvas   simple canvas for draw
     * @param diagonal Segment object which describe diagonal
     * @param leaf     boolean value 'node is leaf or not?'
     */
    public static void drawDefaultDiagonal(Canvas canvas, Segment diagonal, boolean leaf) {
        Point2D mid = diagonal.getMidpoint();
        if (!leaf) {
            drawNodeOfTree(canvas, mid);
            drawDashedLine(canvas, diagonal.getA(), diagonal.getB());
        } else
            drawLeafOfTree(canvas, mid);
        double xShift = (diagonal.getDesc().length() * 5.0) / 2.0;
        if (options.showDescription)
            drawText(canvas, diagonal.getDesc(), diagonal.getMidpoint(), xShift, 15);
    }

    /**
     * Draw line for triangulation tree
     *
     * @param canvas simple canvas for draw
     * @param a      Point2D 'from'
     * @param b      Point2D 'to'
     */
    public static void drawTreeLine(Canvas canvas, Point2D a, Point2D b) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(1.0);
        gc.setStroke(Color.BLACK);
        gc.strokeLine(a.getX(), a.getY(), b.getX(), b.getY());
    }

    /**
     * Draw default text on canvas
     *
     * @param canvas simple canvas for draw
     * @param text   String which draw
     * @param point  Point2D where
     * @param xShift shift axis x
     * @param yShift shift axis y
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
     */
    public static void fillCanvas(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(options.polygonBackground);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
