package sample.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class describe triangulation information
 * and get access for node of triangulation
 *
 * @author hlus
 * @version 2.0
 */
public class TriangulatedPolygon implements Serializable {

    private CostCell rootNode;  // root node of triangulation tree
    private int n;              // vertex count

    /**
     * Simple getter for get access to root
     * of triangulation tree
     *
     * @return root node of triangulation
     */
    public CostCell getRootNode() {
        return rootNode;
    }

    /**
     * Simple getter for get access
     * to count of vertex
     *
     * @return count of polygon vertex
     */
    public int getN() {
        return n;
    }

    /**
     * Simple getter for get access
     * to count of triangles
     * after triangulation
     *
     * @return count of triangles
     */
    public int getTrianglesCount() {
        return n - 2;
    }

    /**
     * Simple getter for get access
     * to count of diagonals
     * after triangulation
     *
     * @return count of diagonals
     */
    public int getDiaglonalesCount() {
        return n - 3;
    }

    /**
     * This method get access for
     * cost value (min sum of diagonals),
     * after triangulation
     *
     * @return cost value after triangulation
     */
    public double getCostSum() {
        return this.rootNode.getCost() - this.rootNode.getSeg().getCost();
    }

    /**
     * Simple constructor where build
     * optimal triangulation tree
     * and calculate minimal cost
     * @param polygon polygon for triangulation
     */
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
