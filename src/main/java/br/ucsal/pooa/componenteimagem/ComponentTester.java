package br.ucsal.pooa.componenteimagem;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

public class ComponentTester {
	public static void main(String[] args) {
		ImageComponent component = new ImageComponent();
		BufferedImage originalImage;
		try {
			originalImage = component.open("examples/input.jpg");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}

		BufferedImage croppedImage = component.crop(originalImage, 700, 0, 800, 800);
		BufferedImage filteredImage = component.applyGrayscaleFilter(croppedImage);
		BufferedImage flippedImage = component.flipX(filteredImage);

		ImageText helloText = new ImageText("Hello", 620, 70, 50, true, false);
		ImageText worldText = new ImageText("World", 620, 130, 50, false, true);

		LinkedList<ImageText> textList = new LinkedList<ImageText>();
		textList.add(helloText);
		textList.add(worldText);

		BufferedImage imageWithText = component.addText(flippedImage, textList);

		BufferedImage finalImage = component.resize(imageWithText, imageWithText.getWidth(), imageWithText.getHeight());

		try {
			BufferedImage watermark = component.open("examples/watermark.png");

			int repeatX = (int) Math.ceil((double) finalImage.getWidth() / watermark.getWidth());
			int repeatY = (int) Math.ceil((double) finalImage.getHeight() / watermark.getHeight());

			BufferedImage tiledWatermark = new BufferedImage(finalImage.getWidth(), finalImage.getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = tiledWatermark.createGraphics();

			for (int x = 0; x < repeatX; x++) {
				for (int y = 0; y < repeatY; y++) {
					g2d.drawImage(watermark, x * watermark.getWidth(), y * watermark.getHeight(), null);
				}
			}
			g2d.dispose();

			finalImage = ImageComponent.applyWatermark(finalImage, tiledWatermark, 0.5f, 0, 0);

			component.save(finalImage, "examples/output.png");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}