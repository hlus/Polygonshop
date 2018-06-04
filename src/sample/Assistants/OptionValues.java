package sample.Assistants;

import java.awt.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Class which describe option values
 *
 * @author hlus
 * @version 2.1
 */
public class OptionValues implements Serializable {

    public boolean showDescription; // show or not (vertex name, diagonal descriptions)
    public boolean showTree;        // show lines of triangulation tree
    public double pointRadius;      // radius of vertex
    public double lineWidth;        // width simple line
    public double treeLineWidth;    // width triangulation tree line
    public Color polygonBackground; // color of canvas background
    public Color vertexColor;       // vertex color
    public Color leafColor;         // leaf color (triangulation tree)
    public Color nodeColor;         // node color (triangulation tree)
    public Color simpleLineColor;   // color of simple line
    public Color diagonalColor;     // diagonal color (after triangulation)

    /**
     * Getter build list of options
     * @return list of options
     */
    public List<Object> getOptions() {
        return Arrays.asList(
                showDescription,
                showTree,
                pointRadius,
                lineWidth,
                treeLineWidth,
                polygonBackground,
                vertexColor,
                leafColor,
                nodeColor,
                simpleLineColor,
                diagonalColor
        );
    }

    /**
     * Getter get polygon background color (convert in javafx color)
     * @return polygonBackground
     */
    public javafx.scene.paint.Color getPolygonBackground() {
        return ColorUtil.awtToFx(polygonBackground);
    }

    /**
     * Getter get vertex color (convert in javafx color)
     * @return vertexColor
     */
    public javafx.scene.paint.Color getVertexColor() {
        return ColorUtil.awtToFx(vertexColor);
    }

    /**
     * Getter get polygon leaf color (convert in javafx color)
     * @return leafColor
     */
    public javafx.scene.paint.Color getLeafColor() {
        return ColorUtil.awtToFx(leafColor);
    }

    /**
     * Getter get polygon node color (convert in javafx color)
     * @return nodeColor
     */
    public javafx.scene.paint.Color getNodeColor() {
        return ColorUtil.awtToFx(nodeColor);
    }

    /**
     * Getter get polygon line color (convert in javafx color)
     * @return simpleLineColor
     */
    public javafx.scene.paint.Color getSimpleLineColor() {
        return ColorUtil.awtToFx(simpleLineColor);
    }

    /**
     * Getter get polygon diagonal color (convert in javafx color)
     * @return diagonalColor
     */
    public javafx.scene.paint.Color getDiagonalColor() {
        return ColorUtil.awtToFx(diagonalColor);
    }

    /**
     * Constructor for OptionValues class
     */
    public OptionValues() {
        this(Arrays.asList(
                true,
                true,
                4.0,
                2.0,
                1.0,
                Color.GRAY,
                Color.BLACK,
                Color.BLACK,
                Color.BLACK,
                Color.BLACK,
                Color.BLACK
        ));
    }

    /**
     * Constructor for OptionValues class
     * @param optionValues copy object
     */
    public OptionValues(OptionValues optionValues) {
        this(optionValues.getOptions());
    }

    /**
     * Constructor for OptionValues class
     * @param values values for initialize the new object
     */
    public OptionValues(List<Object> values) {
        this.showDescription = (boolean) values.get(0);
        this.showTree = (boolean) values.get(1);
        this.pointRadius = (double) values.get(2);
        this.lineWidth = (double) values.get(3);
        this.treeLineWidth = (double) values.get(4);
        this.polygonBackground = (Color) values.get(5);
        this.vertexColor = (Color) values.get(6);
        this.leafColor = (Color) values.get(7);
        this.nodeColor = (Color) values.get(8);
        this.simpleLineColor = (Color) values.get(9);
        this.diagonalColor = (Color) values.get(10);
    }
}
