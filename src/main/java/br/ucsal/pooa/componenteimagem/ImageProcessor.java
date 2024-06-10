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

    /**
     * Rotaciona a imagem fornecida pelo ângulo especificado.
     *
     * @param image A imagem a ser rotacionada.
     * @param angle O ângulo de rotação em graus.
     * @return A nova imagem rotacionada.
     */
    public BufferedImage rotate(BufferedImage image, double angle) {
        // Converte o ângulo de graus para radianos
        double radians = Math.toRadians(angle);

        // Calcula o seno e cosseno do ângulo
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));

        // Obtém as dimensões da imagem original
        int w = image.getWidth();
        int h = image.getHeight();

        // Calcula as novas dimensões da imagem rotacionada
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        // Cria uma nova imagem com as dimensões calculadas e suporte a transparência
        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();

        // Configura a transformação de rotação
        AffineTransform at = new AffineTransform();

        // Translada para o centro da nova imagem
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        // Define o ponto de rotação como o centro da imagem original
        int x = w / 2;
        int y = h / 2;

        // Aplica a rotação ao ponto central
        at.rotate(radians, x, y);

        // Configura a transformação no objeto Graphics2D
        g2d.setTransform(at);

        // Desenha a imagem original na nova imagem com a transformação aplicada
        g2d.drawImage(image, 0, 0, null);

        // Libera os recursos do objeto Graphics2D
        g2d.dispose();

        // Retorna a imagem rotacionada
        return rotatedImage;
    }
}