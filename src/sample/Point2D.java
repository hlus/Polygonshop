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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Point2D(double x, double y, String desc) {
        this.x = x;
        this.y = y;
        this.desc = desc;
    }

    /**
     * Constructor with copy of point
     *
     * @param x double
     * @param y double
     * @see Point2D
     */
    public Point2D(double x, double y) {
        this(x,y,"");
    }

    public static double distance(Point2D a, Point2D b) {
        double dx = b.getX() - a.getX();
        double dy = b.getY() - a.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double distance(Point2D p) {
        return distance(this, p);
    }

    @Override
    public String toString() {
        return desc;
    }
}
