package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TriangulatedPolygon implements Serializable {

    private CostCell rootNode;
    private int n;

    public CostCell getRootNode() {
        return rootNode;
    }

    public int getN() {
        return n;
    }

    public int getTrianglesCount() {
        return n - 2;
    }

    public int getDiaglonalesCount() {
        return n - 3;
    }

    public double getCostSum() {
        return this.rootNode.getCost();
    }

    public TriangulatedPolygon(Polygon polygon) {
        this.n = polygon.getPoints().size();
        CostCell[][] table = new CostCell[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                table[i][j] = new CostCell(null);

        List<Point2D> points = polygon.getPoints();
        for (int gap = 1; gap < n; gap++) {
            for (int i = 0, j = gap; j < n; i++, j++) {
                if (j < i + 2)
                    table[i][j] = new CostCell(new Segment(points.get(i), points.get(j), "A" + j));
                else {
                    table[i][j] = new CostCell(new Segment(points.get(i), points.get(j)), Double.MAX_VALUE);
                    for (int k = i + 1; k < j; k++) {
                        double val = table[i][k].getCost() + table[k][j].getCost() + points.get(i).distance(points.get(j));
                        if (table[i][j].getCost() > val) {
                            List<CostCell> nodes = new ArrayList<>();
                            nodes.add(table[i][k]);
                            nodes.add(table[k][j]);
                            Segment newSeg = new Segment(points.get(i), points.get(j));
                            table[i][j] = new CostCell(newSeg, val, nodes);
                        }
                    }
                }
            }
        }

        this.rootNode = table[0][n - 1];
    }

}
