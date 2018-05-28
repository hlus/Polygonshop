package sample;

import java.io.Serializable;

public class Segment implements Serializable {

    private Point2D a, b;
    private String desc;

    public Point2D getA() {
        return a;
    }

    public Point2D getB() {
        return b;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Segment(Point2D a, Point2D b, String desc) {
        this.a = a;
        this.b = b;
        this.desc = desc;
    }

    public Segment(Point2D a, Point2D b) {
        this(a, b, "");
    }

    public double getCost() {
        return a.distance(b);
    }

    public Point2D getMidpoint() {
        return new Point2D((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2, desc);
    }

    @Override
    public String toString() {
        return desc;
    }
}
