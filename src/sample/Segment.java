package sample;

import java.io.Serializable;

/**
 * Class which describe simple
 * segment, have two point and description
 *
 * @author hlus
 * @version 1.0
 */
public class Segment implements Serializable {

    private Point2D a, b;   // points of segment
    private String desc;    // description for points

    /**
     * Getter for a property
     *
     * @return first point in segment
     */
    public Point2D getA() {
        return a;
    }

    /**
     * Getter for b property
     *
     * @return second point in segment
     */
    public Point2D getB() {
        return b;
    }

    /**
     * Getter for desc property
     *
     * @return description of segment
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Setter for property desc
     *
     * @param desc description of segment
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Constructor for Segment class
     *
     * @param a    first point
     * @param b    second point
     * @param desc description for segment
     */
    public Segment(Point2D a, Point2D b, String desc) {
        this.a = a;
        this.b = b;
        this.desc = desc;
    }

    /**
     * Constructor for Segment class
     *
     * @param a first segment point
     * @param b second segment point
     */
    public Segment(Point2D a, Point2D b) {
        this(a, b, "");
    }

    /**
     * Method calculate of cost function
     *
     * @return cost of segment
     */
    public double getCost() {
        return a.distance(b);
    }

    /**
     * Calculate midpoint for segment
     *
     * @return midpoint of segment
     */
    public Point2D getMidpoint() {
        return new Point2D((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2, desc);
    }

    /**
     * @return desc property
     */
    @Override
    public String toString() {
        return desc;
    }
}
