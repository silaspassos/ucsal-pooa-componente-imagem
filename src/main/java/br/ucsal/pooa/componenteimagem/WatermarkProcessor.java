package br.ucsal.pooa.componenteimagem;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WatermarkProcessor {
    public static BufferedImage applyWatermark(BufferedImage image, BufferedImage watermark, float opacity, int x, int y) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage watermarkedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = watermarkedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.drawImage(watermark, x, y, null);
        g2d.dispose();
        return watermarkedImage;
    }
}