package sample.Model;

import java.io.Serializable;

/**
 * Simple class Point,
 * which contain coordinates in 2d dimensions,
 * and which we can serialize !!!
 *
 * @author hlus
 * @version 2.1
 */
public class Point2D implements Serializable {

    /**
     * Properties:
     * x - The abscissa axis
     * y - The ordinate axis
     * desc - description for point
     */
    private double x;
    private double y;
    private String desc;

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
     * Simple getter for access desc property
     *
     * @return desc property
     * @see Point2D#desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Simple setter for desc property
     *
     * @param desc param which will be assign desc property
     * @see Point2D#desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Constructor with copy of point
     *
     * @param x    double coordinate axis x
     * @param y    double coordinate axis y
     * @param desc description for point
     */
    public Point2D(double x, double y, String desc) {
        this.x = x;
        this.y = y;
        this.desc = desc;
    }

    /**
     * Constructor with copy of point
     *
     * @param x double coordinate axis x
     * @param y double coordinate axis y
     * @see Point2D
     */
    public Point2D(double x, double y) {
        this(x, y, "");
    }

    /**
     * Calculate distance between points
     *
     * @param a first point 'from'
     * @param b second point 'to'
     * @return distance between points
     */
    public static double distance(Point2D a, Point2D b) {
        double dx = b.getX() - a.getX();
        double dy = b.getY() - a.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Calculate distance between this point and
     * a - param point
     *
     * @param p second point 'to'
     * @return distance between points
     */
    public double distance(Point2D p) {
        return distance(this, p);
    }

    /**
     * @return desc property of point
     */
    @Override
    public String toString() {
        return desc;
    }
}
