package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.paint.Color;

public class UIPolygon {
    private Canvas canvas;
    private String fileName;

    public UIPolygon(String fileName, double width, double height) {
        this.fileName = fileName;
        canvas = new Canvas(width, height);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GHOSTWHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public Tab getTab() {
        Tab tab = new Tab();
        tab.setText(fileName);
        ScrollPane scrollPane = new ScrollPane(canvas);
        tab.setContent(scrollPane);
        return tab;
    }
}
