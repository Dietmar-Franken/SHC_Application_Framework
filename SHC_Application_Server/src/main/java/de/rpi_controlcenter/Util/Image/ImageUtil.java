package de.rpi_controlcenter.Util.Image;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Hilfsfunktionen für die Bildbearbeitung
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class ImageUtil {

    /**
     * erzeugt ein skaliertes Bild
     *
     * @param originalImage Originales Bild
     * @param newWidth neue Breite
     * @param newHeight neue höhe
     * @return skaliertes Bild
     */
    public static BufferedImage createResizedCopy(BufferedImage originalImage, int newWidth, int newHeight) {

        BufferedImage scaled = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaled.createGraphics();
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return scaled;
    }
}
