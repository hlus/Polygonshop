package sample;

import java.awt.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Class which describe option values
 *
 * @author hlus
 * @version 2.0
 */
public class OptionValues implements Serializable {

    public boolean showDescription;
    public boolean showTree;
    public double pointRadius;
    public double lineWidth;
    public double treeLineWidth;
    public Color polygonBackground;
    public Color vertexColor;
    public Color leafColor;
    public Color nodeColor;
    public Color simpleLineColor;
    public Color diagonalColor;

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

    public javafx.scene.paint.Color getPolygonBackground() {
        return ColorUtil.awtToFx(polygonBackground);
    }

    public javafx.scene.paint.Color getVertexColor() {
        return ColorUtil.awtToFx(vertexColor);
    }

    public javafx.scene.paint.Color getLeafColor() {
        return ColorUtil.awtToFx(leafColor);
    }

    public javafx.scene.paint.Color getNodeColor() {
        return ColorUtil.awtToFx(nodeColor);
    }

    public javafx.scene.paint.Color getSimpleLineColor() {
        return ColorUtil.awtToFx(simpleLineColor);
    }

    public javafx.scene.paint.Color getDiagonalColor() {
        return ColorUtil.awtToFx(diagonalColor);
    }

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

    public OptionValues(OptionValues optionValues) {
        this(optionValues.getOptions());
    }

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
