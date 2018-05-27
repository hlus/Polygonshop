package sample;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CostCell {

    private Segment seg;
    private double cost;
    private List<CostCell> subNodes;

    public Segment getSeg() {
        return seg;
    }

    public List<CostCell> getSubNodes() {
        return subNodes;
    }

    public double getCost() {
        return cost;
    }

    public CostCell(Segment seg) {
        this(seg, 0);
    }

    public CostCell(Segment seg, double cost) {
        this(seg, cost, null);
    }

    public CostCell(Segment seg, double cost, List<CostCell> subNodes) {
        this.seg = seg;
        this.cost = cost;
        this.subNodes = subNodes;
        if (seg != null && subNodes != null)
            this.seg.setDesc("(" + String.join(",", subNodes.stream().map(n -> n.getSeg().getDesc()).collect(Collectors.toList())) + ")");
    }
}
