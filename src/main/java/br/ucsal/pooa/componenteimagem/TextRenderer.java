package br.ucsal.pooa.componenteimagem;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

public class TextRenderer {
    private void renderTextIntoImage(Graphics2D g2d, String text, int x, int y) {
        g2d.drawString(text, x, y);
    }

    private void renderTextIntoImage(Graphics2D g2d, String text, int x, int y, String font, int size, int type) {
        g2d.setFont(new Font(font, type, size));
        renderTextIntoImage(g2d, text, x, y);
    }

    private void renderTextIntoImage(Graphics2D g2d, String text, int x, int y, String font, int size) {
        renderTextIntoImage(g2d, text, x, y, font, size, Font.PLAIN);
    }

    private void renderTextIntoImage(Graphics2D g2d, ImageText text) {
        int fontStyle = Font.PLAIN;

        if (text.isBold()) {
            fontStyle |= Font.BOLD;
            fontStyle &= ~Font.PLAIN;
        }
        if (text.isItalics()) {
            fontStyle |= Font.ITALIC;
            fontStyle &= ~Font.PLAIN;
        }

        String content = text.getText();
        int x = text.getX();
        int y = text.getY();
        String fontName = text.getFont();
        int fontSize = text.getSize();

        renderTextIntoImage(g2d, content, x, y, fontName, fontSize, fontStyle);
    }

    /**
     * Adiciona texto à imagem.
     * 
     * @param image A imagem à qual o texto será adicionado.
     * @param text  O texto a ser adicionado.
     * @param x     A posição horizontal do texto na imagem.
     * @param y     A posição vertical do texto na imagem.
     * @param font  Nome da fonte a ser usada.
     * @param size  Tamanho do texto.
     * @return A imagem com o texto adicionado.
     */
    public BufferedImage addText(BufferedImage image, String text, int x, int y, String font, int size) {
        Graphics2D g2d = image.createGraphics();
        renderTextIntoImage(g2d, text, x, y, font, size);
        g2d.dispose();
        return image;
    }

    /**
     * Adiciona texto à imagem.
     * 
     * @param image A imagem à qual o texto será adicionado.
     * @param text  O texto a ser adicionado.
     * @param x     A posição horizontal do texto na imagem.
     * @param y     A posição vertical do texto na imagem.
     * @return A imagem com o texto adicionado.
     */
    public BufferedImage addText(BufferedImage image, String text, int x, int y) {
        return addText(image, text, x, y, null, 0);
    }

    /**
     * Adiciona texto à imagem.
     * 
     * @param image A imagem à qual o texto será adicionado.
     * @param text  O texto a ser adicionado.
     * @return A imagem com o texto adicionado.
     */
    public BufferedImage addText(BufferedImage image, ImageText text) {
        return addText(image, text.getText(), text.getX(), text.getY(), text.getFont(), text.getSize());
    }

    /**
     * Adiciona textos à imagem.
     * 
     * @param image A imagem à qual os textos serão adicionados.
     * @param list  Os textos a serem adicionados.
     * @return A imagem com os textos adicionados.
     */
    public BufferedImage addTexts(BufferedImage image, List<ImageText> list) {
        Graphics2D g2d = image.createGraphics();
        Iterator<ImageText> it = list.iterator();
        while (it.hasNext()) {
            renderTextIntoImage(g2d, it.next());
        }
        g2d.dispose();
        return image;
    }
}