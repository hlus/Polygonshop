package sample.Assistants;

import java.awt.*;

/**
 * Class for convert javafx color
 * in to awt color (for serializable),
 * and vice versa
 */
public class ColorUtil {

    /**
     * Convert fx color in to awt color (Serializable)
     *
     * @param color fx color arg
     * @return awt color
     */
    public static Color fxToAwt(javafx.scene.paint.Color color) {
        return new Color((float) color.getRed(), (float) color.getGreen(), (float) color.getBlue(), (float) color.getOpacity());
    }

    /**
     * Convert awt color to fx color
     *
     * @param color awt color
     * @return fx color
     */
    public static javafx.scene.paint.Color awtToFx(Color color) {
        return new javafx.scene.paint.Color(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0, color.getAlpha() / 255.0);
    }
}
