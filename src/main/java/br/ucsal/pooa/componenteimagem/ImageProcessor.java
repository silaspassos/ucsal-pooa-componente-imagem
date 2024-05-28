package br.ucsal.pooa.componenteimagem;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageProcessor {
    public BufferedImage copy(BufferedImage image) {
        return resize(image, image.getWidth(), image.getHeight());
    }

    public BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return resizedImage;
    }

    public BufferedImage crop(BufferedImage image, Rectangle rect) {
        BufferedImage croppedImage = new BufferedImage((int) rect.getWidth(), (int) rect.getHeight(), image.getType());
        Graphics2D g2d = croppedImage.createGraphics();
        int x = (int) rect.getX();
        int y = (int) rect.getY();
        int w = (int) rect.getWidth();
        int h = (int) rect.getHeight();
        g2d.drawImage(image, 0, 0, w, h, x, y, x + w, y + h, null);
        g2d.dispose();
        return croppedImage;
    }

    public BufferedImage flipX(BufferedImage image) {
        AffineTransform transform = AffineTransform.getScaleInstance(-1, 1);
        transform.translate(-image.getWidth(null), 0);
        AffineTransformOp transformOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return transformOp.filter(image, null);
    }

    public BufferedImage flipY(BufferedImage image) {
        AffineTransform transform = AffineTransform.getScaleInstance(1, -1);
        transform.translate(0, -image.getHeight(null));
        AffineTransformOp transformOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return transformOp.filter(image, null);
    }

    public BufferedImage applyGrayscaleFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Iterar sobre todos os pixels da imagem
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Obter o valor RGB do pixel
                int rgb = image.getRGB(x, y);

                // Extrair os componentes de cor (vermelho, verde e azul)
                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Calcular a média dos componentes de cor para obter a intensidade de cinza
                int gray = (red + green + blue) / 3;

                // Criar o novo pixel com intensidade de cinza
                int grayPixel = (alpha << 24) | (gray << 16) | (gray << 8) | gray;

                // Definir o novo pixel na imagem em tons de cinza
                grayscaleImage.setRGB(x, y, grayPixel);
            }
        }

        return grayscaleImage;
    }

}