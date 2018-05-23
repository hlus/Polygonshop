package sample;

import java.io.Serializable;

/**
 * Simple class Point,
 * which contain coordinates in 2d dimensions,
 * and which we can serialize !!!
 *
 * @author hlus
 * @version 2.0
 */
public class Point2D implements Serializable {

    /**
     * Properties:
     * x - The abscissa axis
     * y - The ordinate axis
     */
    private double x;
    private double y;

    /**
     * Simple getter for property x
     *
     * @return x double
     * @see Point2D#x
     */
    public double getX() {
        return x;
    }

    /**
     * Simple getter for property y
     *
     * @return y double
     * @see Point2D#y
     */
    public double getY() {
        return y;
    }

    /**
     * Constructor with copy of point
     *
     * @param x double
     * @param y double
     * @see Point2D
     */
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
