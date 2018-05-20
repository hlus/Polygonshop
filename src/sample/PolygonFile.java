package sample;

import java.io.Serializable;

public class PolygonFile implements Serializable {
    private String fileName;
    private Polygon polygon;

    public String getFileName() {
        return fileName;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public PolygonFile(String fileName, Polygon polygon) {
        this.fileName = fileName;
        this.polygon = polygon;
    }

}
