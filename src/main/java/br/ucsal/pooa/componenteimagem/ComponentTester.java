package br.ucsal.pooa.componenteimagem;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.awt.Rectangle;

public class ComponentTester {
	public static void main(String[] args) {
		ImageFileHandler fileHandler = new ImageFileHandler();
		ImageProcessor imageProcessor = new ImageProcessor();
		TextRenderer textRenderer = new TextRenderer();
		WatermarkProcessor watermarkProcessor = new WatermarkProcessor();

		try {
			BufferedImage originalImage = fileHandler.open("examples/input.jpg");
			BufferedImage croppedImage = imageProcessor.crop(originalImage, new Rectangle(700, 0, 800, 800));
			BufferedImage filteredImage = imageProcessor.applyGrayscaleFilter(croppedImage);
			BufferedImage flippedImage = imageProcessor.flipX(filteredImage);

			ImageText helloText = new ImageText("Hello", 620, 70, 50, true, false);
			ImageText worldText = new ImageText("World", 620, 130, 50, false, true);

			LinkedList<ImageText> textList = new LinkedList<>();
			textList.add(helloText);
			textList.add(worldText);

			BufferedImage imageWithText = textRenderer.addTexts(flippedImage, textList);

			BufferedImage finalImage = imageProcessor.resize(imageWithText, imageWithText.getWidth(), imageWithText.getHeight());

			BufferedImage watermark = fileHandler.open("examples/watermark.png");

			int repeatX = (int) Math.ceil((double) finalImage.getWidth() / watermark.getWidth());
			int repeatY = (int) Math.ceil((double) finalImage.getHeight() / watermark.getHeight());

			BufferedImage tiledWatermark = new BufferedImage(finalImage.getWidth(), finalImage.getHeight(),
					BufferedImage.TYPE_INT_ARGB);

			for (int x = 0; x < repeatX; x++) {
				for (int y = 0; y < repeatY; y++) {
					tiledWatermark.createGraphics().drawImage(watermark, x * watermark.getWidth(), y * watermark.getHeight(), null);
				}
			}

			finalImage = watermarkProcessor.applyWatermark(finalImage, tiledWatermark, 0.5f, 0, 0);

			fileHandler.save(finalImage, "examples/output.png");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
