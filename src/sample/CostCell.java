package sample;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class describe simple cell
 * which use in TriangulatedPolygon
 *
 * @author hlus
 * @version 2.0
 * @see TriangulatedPolygon
 */
public class CostCell implements Serializable {

    private Segment seg;            // segment which describe node
    private double cost;            // cost of this node and sub nodes
    private List<CostCell> subNodes;// list of sub nodes for this node

    /**
     * Simple getter for segment property
     *
     * @return segment property
     * @see CostCell#seg
     */
    public Segment getSeg() {
        return seg;
    }

    /**
     * Simple getter for subNodes property
     *
     * @return list of sub nodes
     * @see CostCell#subNodes
     */
    public List<CostCell> getSubNodes() {
        return subNodes;
    }

    /**
     * Simple getter for cost property
     *
     * @return cost of triangulation
     * @see CostCell#cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * Simple constructor for CostCell class
     *
     * @param seg segment which describe node
     * @see CostCell#seg
     */
    public CostCell(Segment seg) {
        this(seg, 0);
    }

    /**
     * Simple constructor for CostCell class
     *
     * @param seg  segment which describe node
     * @param cost cost of node
     */
    public CostCell(Segment seg, double cost) {
        this(seg, cost, null);
    }

    /**
     * Simple constructor for CostCell class
     *
     * @param seg      segment which describe node
     * @param cost     cost of node
     * @param subNodes sub nodes of this node
     * @see CostCell#CostCell(Segment, double)
     */
    public CostCell(Segment seg, double cost, List<CostCell> subNodes) {
        this.seg = seg;
        this.cost = cost;
        this.subNodes = subNodes;
        if (seg != null && subNodes != null)
            this.seg.setDesc("(" + String.join(",", subNodes.stream().map(n -> n.getSeg().getDesc()).collect(Collectors.toList())) + ")");
    }
}
