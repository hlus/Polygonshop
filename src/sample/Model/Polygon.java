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

    public enum polygonType {
        Collinear,
        Convex,
        Concave;
    }

    /**
     * All point in polygon
     */
    private List<Point2D> points;
    private polygonType polType;

    /**
     * simple getter for access points property
     *
     * @return list of points
     * @see Polygon#points
     */
    public List<Point2D> getPoints() {
        return points;
    }

    public polygonType getPolygonType() {
        return polType;
    }

    /**
     * Constructor with copy of point
     *
     * @param points points for polygon
     * @see Polygon
     */
    public Polygon(List<Point2D> points) {
        this.points = new ArrayList<>(points);
        polType = initializePolygonType();
    }

    private polygonType initializePolygonType() {
        int n = points.size();
        if (n < 3)
            return polygonType.Collinear;

        int flag = 0;
        for (int i = 0; i < n; i++) {
            int j = (i + 1)%n, k = (i + 2)%n;
            double z = (points.get(j).getX() - points.get(i).getX()) * (points.get(k).getY() - points.get(j).getY()) -
                    (points.get(j).getY() - points.get(i).getY()) * (points.get(k).getX() - points.get(j).getX());
            if (z < 0)
                flag |= 1;
            else if (z > 0)
                flag |= 2;
            if (flag == 3)
                return polygonType.Concave;
        }
        return flag != 0 ? polygonType.Convex : polType.Collinear;

    }
}
