package br.ucsal.pooa.componenteimagem;

import java.awt.Font;

public class ImageText {
	// Texto
	private String text = "";

	// Posições X e Y
	private int x = 0;
	private int y = 0;

	// Configurações da fonte
	private String font = Font.SANS_SERIF;
	private int size = 14;

	private boolean bold = false;
	private boolean italics = false;

	public ImageText(String text, int x, int y) {
		if (text == null)
			throw new IllegalArgumentException("Invalid text string");

		this.text = text;
		this.x = x;
		this.y = y;
	}

	public ImageText(String text, int x, int y, int size) {
		this(text, x, y);

		if (size < 1)
			throw new IllegalArgumentException("Invalid font size");

		this.size = size;
	}

	public ImageText(String text, int x, int y, String font) {
		this(text, x, y);

		if (font == null)
			throw new IllegalArgumentException("Invalid font");

		this.font = font;
	}

	public ImageText(String text, int x, int y, String font, int size) {
		this(text, x, y, size);

		if (font == null)
			throw new IllegalArgumentException("Invalid font");

		this.font = font;
	}

	public ImageText(String text, int x, int y, boolean bold, boolean italics) {
		this(text, x, y);
		this.bold = bold;
		this.italics = italics;
	}

	public ImageText(String text, int x, int y, String font, boolean bold, boolean italics) {
		this(text, x, y, font);
		this.bold = bold;
		this.italics = italics;
	}

	public ImageText(String text, int x, int y, int size, boolean bold, boolean italics) {
		this(text, x, y, size);
		this.bold = bold;
		this.italics = italics;
	}

	public ImageText(String text, int x, int y, String font, int size, boolean bold, boolean italics) {
		this(text, x, y, font, size);
		this.bold = bold;
		this.italics = italics;
	}

	public String getText() {
		return text;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getFont() {
		return font;
	}

	public int getSize() {
		return size;
	}

	public boolean isBold() {
		return bold;
	}

	public boolean isItalics() {
		return italics;
	}
}
