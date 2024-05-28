package br.ucsal.pooa.componenteimagem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageFileHandler {
    public BufferedImage open(String path) throws IOException {
        BufferedImage imgFile = ImageIO.read(new File(path));
        int w = imgFile.getWidth();
        int h = imgFile.getHeight();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        image.createGraphics().drawImage(imgFile, 0, 0, w, h, null);
        return image;
    }

    public void save(BufferedImage image, String path, String type) throws IOException {
        if (type == null)
            type = "png";
        int extIndex = path.lastIndexOf(".");
        if (extIndex != -1) {
            String extension = path.substring(extIndex + 1);
            if (extension.equals("png") || extension.equals("jpg"))
                type = extension;
        }
        ImageIO.write(image, type, new File(path));
    }

    public void save(BufferedImage image, String path) throws IOException {
        save(image, path, null);
    }
}