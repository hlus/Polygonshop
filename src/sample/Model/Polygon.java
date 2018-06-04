package sample.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which describe math part of polygon
 *
 * @author hlus
 * @version 2.0
 */
public class Polygon implements Serializable {

    /**
     * All point in polygon
     */
    private List<Point2D> points;

    /**
     * simple getter for access points property
     *
     * @return list of points
     * @see Polygon#points
     */
    public List<Point2D> getPoints() {
        return points;
    }

    /**
     * Constructor with copy of point
     *
     * @param points points for polygon
     * @see Polygon
     */
    public Polygon(List<Point2D> points) {
        this.points = new ArrayList<>(points);
    }
}
