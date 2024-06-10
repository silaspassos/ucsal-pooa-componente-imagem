package br.ucsal.pooa.componenteimagem;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WatermarkProcessor {
    /**
     * Aplica uma marca d'água a imagem.
     * 
     * @param image     A imagem a ter a marca d'água aplicada.
     * @param watermark A imagem contendo a marca d'água.
     * @param opacity   A opacidade da marca d'água. Deve ser um valor float entre [0.0, 1.0] inclusivo.
     * @param x         Posição X da marca d'água.
     * @param y         Posição Y da marca d'água.
     * @return A imagem com a marca d'água aplicada.
     */
    public BufferedImage applyWatermark(BufferedImage image, BufferedImage watermark, float opacity, int x, int y) {
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
