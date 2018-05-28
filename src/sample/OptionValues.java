package sample;

import javafx.scene.paint.Color;

public class OptionValues {

    public boolean showDescription;
    public double pointRadius;
    public double lineWidth;
    public double treeLineWidth;
    public Color polygonBackground;
    public Color vertexColor;
    public Color leafColor;
    public Color nodeColor;
    public Color simpleLineColor;
    public Color diagonalColor;


    public OptionValues(boolean showDescription, double pointRadius, double lineWidth, double treeLineWidth, Color polygonBackground, Color vertexColor, Color leafColor, Color nodeColor, Color simpleLineColor, Color diagonalColor) {
        this.showDescription = showDescription;
        this.pointRadius = pointRadius;
        this.lineWidth = lineWidth;
        this.treeLineWidth = treeLineWidth;
        this.polygonBackground = polygonBackground;
        this.vertexColor = vertexColor;
        this.leafColor = leafColor;
        this.nodeColor = nodeColor;
        this.simpleLineColor = simpleLineColor;
        this.diagonalColor = diagonalColor;
    }
}
