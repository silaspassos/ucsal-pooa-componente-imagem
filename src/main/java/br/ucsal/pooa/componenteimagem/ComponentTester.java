package br.ucsal.pooa.componenteimagem;

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
		BufferedImage finalImage = component.resize(imageWithText, imageWithText.getWidth() / 2, imageWithText.getHeight() / 2);

		try {
			component.save(finalImage, "examples/output.png");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
