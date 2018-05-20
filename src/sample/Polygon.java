package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Polygon implements Serializable{

    private List<Point2D> points;

    public List<Point2D> getPoints() {
        return points;
    }

    public Polygon(List<Point2D> points) {
        this.points = new ArrayList<>(points);
    }
}
