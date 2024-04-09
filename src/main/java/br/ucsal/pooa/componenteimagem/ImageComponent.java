package br.ucsal.pooa.componenteimagem;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageComponent {
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
		int type = Font.PLAIN;

		if (text.isItalics())
			type = Font.ITALIC;
		else if (text.isBold())
			type = Font.BOLD;

		renderTextIntoImage(g2d, text.getText(), text.getX(), text.getY(), text.getFont(), text.getSize(), type);
	}

	/**
	 * Abre uma imagem pelo caminho especificado.
	 * 
	 * @param path O caminho da imagem a ler.
	 * @return A imagem que foi lida.
	 * @throws IOException
	 */
	public BufferedImage open(String path) throws IOException {
		BufferedImage imgFile = ImageIO.read(new File(path));

		int w = (int) imgFile.getWidth();
		int h = (int) imgFile.getHeight();

		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		image.createGraphics().drawImage(imgFile, 0, 0, w, h, null);

		return image;
	}

	/**
	 * Salva a imagem para o caminho especificado.
	 * 
	 * @param image A imagem a salvar.
	 * @param type  O formato em que a imagem deve ser salva.
	 * @param path  O caminho em que a imagem deve ser salva.
	 * @throws IOException
	 */
	public void save(BufferedImage image, String path, String type) throws IOException {
		if (type == null)
			type = "png";

		int extIndex = path.lastIndexOf(".");
		if (extIndex != -1) {
			String extension = path.substring(extIndex + 1);
			if (extension == "png" || extension == "jpg")
				type = extension;
		}

		ImageIO.write(image, type, new File(path));
	}

	/**
	 * Salva a imagem para o caminho especificado.
	 * 
	 * @param image A imagem a salvar.
	 * @param path  O caminho em que a imagem deve ser salva.
	 * @throws IOException
	 */
	public void save(BufferedImage image, String path) throws IOException {
		save(image, path, null);
	}

	/**
	 * Cria uma cópia da imagem especificada.
	 * 
	 * @param image A imagem a ser copiada.
	 * @return A cópia da imagem original.
	 */
	public BufferedImage copy(BufferedImage image) {
		return resize(image, image.getWidth(), image.getHeight());
	}

	/**
	 * Redimensiona a imagem para uma largura e altura específicas.
	 * 
	 * @param image  A imagem a ser redimensionada.
	 * @param width  A largura desejada.
	 * @param height A altura desejada.
	 * @return A imagem redimensionada.
	 */
	public BufferedImage resize(BufferedImage image, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
		Graphics2D g2d = resizedImage.createGraphics();
		g2d.drawImage(image, 0, 0, width, height, null);
		g2d.dispose();
		return resizedImage;
	}

	/**
	 * Recorta a imagem pelos limites especificados.
	 * 
	 * @param image A imagem a ser recortada.
	 * @param rect  Limites do recorte.
	 * @return A imagem recortada.
	 */
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

	/**
	 * Recorta a imagem pelos limites especificados.
	 * 
	 * @param image A imagem a ser recortada.
	 * @param cropX Coluna do recorte.
	 * @param cropY Linha do recorte.
	 * @param cropW Tamanho horizontal do recorte.
	 * @param cropY Tamanho vertical do recorte.
	 * @return A imagem recortada.
	 */
	public BufferedImage crop(BufferedImage image, int cropX, int cropY, int cropW, int cropH) {
		return crop(image, new Rectangle(cropX, cropY, cropW, cropH));
	}

	public BufferedImage crop(BufferedImage image, double cropX, double cropY, double cropW, double cropH) {
		return crop(image, (int) cropX, (int) cropY, (int) cropW, (int) cropH);
	}

	/**
	 * Espelha a imagem horizontalmente.
	 * 
	 * @param image A imagem a ser espelhada.
	 * @return A imagem espelhada.
	 */
	public BufferedImage flipX(BufferedImage image) {
		AffineTransform transform = AffineTransform.getScaleInstance(-1, 1);
		transform.translate(-image.getWidth(null), 0);
		AffineTransformOp transformOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return transformOp.filter(image, null);
	}

	/**
	 * Espelha a imagem verticalmente.
	 * 
	 * @param image A imagem a ser espelhada.
	 * @return A imagem espelhada.
	 */
	public BufferedImage flipY(BufferedImage image) {
		AffineTransform transform = AffineTransform.getScaleInstance(1, -1);
		transform.translate(0, -image.getHeight(null));
		AffineTransformOp transformOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return transformOp.filter(image, null);
	}

	/**
	 * Aplica um filtro de escala de cinza na imagem.
	 * 
	 * @param image A imagem a ser filtrada.
	 * @return A imagem filtrada.
	 */
	public BufferedImage applyGrayscaleFilter(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();

		double gamma = 2.2;
		double invGamma = 1.0 / gamma; // 1.0 / 2.2 precalculado

		// Manipular uma array é mais rápido do que usar getRGB e setRGB
		int[] srcPixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		Object destPixels = Array.newInstance(Integer.TYPE, new int[] { width * height });

		// Itere linha por linha, coluna por coluna
		for (int y = 0; y < height; y++) {
			// Posição atual na imagem
			int imgPos = y * width;

			for (int x = 0; x < width; x++) {
				int pixel = Array.getInt(srcPixels, imgPos);

				// separa os componentes RGB do pixel
				int alpha = (pixel >> 24) & 255;
				int red = (pixel >> 16) & 255;
				int green = (pixel >> 8) & 255;
				int blue = pixel & 255;

				// calcula a luminância de cada canal, com os valores normalizados de 0 - 255
				// para 0.0 - 1.0, pós-correção de gamma
				double lr = 0.2126 * Math.pow(red / 255.0, gamma);
				double lg = 0.7152 * Math.pow(green / 255.0, gamma);
				double lb = 0.0722 * Math.pow(blue / 255.0, gamma);
				int luminance = (int) (Math.pow(lr + lg + lb, invGamma) * 255);

				// converte para ARGB
				// A transparência é preservada
				int outPixel = (alpha << 24) | (luminance << 16) | (luminance << 8) | luminance;

				// guarda o resultado na nova imagem
				Array.setInt(destPixels, imgPos, outPixel);

				// próxima coluna
				imgPos++;
			}
		}

		// crie uma imagem a partir da array resultado e retorne
		BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		int[] outputPixels = ((DataBufferInt) output.getRaster().getDataBuffer()).getData();
		System.arraycopy(destPixels, 0, outputPixels, 0, width * height);

		return output;
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
		return copy(image);
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
	 * @param text  Os textos a serem adicionados.
	 * @return A imagem com os textos adicionados.
	 */
	public BufferedImage addText(BufferedImage image, List<ImageText> list) {
		Graphics2D g2d = image.createGraphics();
		Iterator<ImageText> it = list.iterator();
		while (it.hasNext()) {
			renderTextIntoImage(g2d, it.next());
		}
		g2d.dispose();
		return copy(image);
	}
}
