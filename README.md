# Componente de Imagem

Este componente implementa leitura e escrita de imagens a partir de arquivos, além de algumas funções de processamento de imagem.

## Funções disponíveis

- Redimensionamento (resize)
- Recortagem (crop)
- Espelhamento (flip)
- Filtro de escala de cinza (grayscale)
- Texto

## Métodos públicos

- `BufferedImage open(String path)`
- `void save(BufferedImage image, String path, String type)`
  - `void save(BufferedImage image, String path)`
- `BufferedImage copy(BufferedImage image)`
- `BufferedImage resize(BufferedImage image, int width, int height)`
- `BufferedImage crop(BufferedImage image, Rectangle rect)`
  - `BufferedImage crop(BufferedImage image, int cropX, int cropY, int cropW, int cropH)`
  - `BufferedImage crop(BufferedImage image, double cropX, double cropY, double cropW, double cropH)`
- `BufferedImage flipX(BufferedImage image)`
- `BufferedImage flipY(BufferedImage image)`
- `BufferedImage applyGrayscaleFilter(BufferedImage image)`
- `BufferedImage addText(BufferedImage image, String text, int x, int y, String font, int size)`
  - `BufferedImage addText(BufferedImage image, String text, int x, int y)`
  - `BufferedImage addText(BufferedImage image, ImageText text)`
  - `BufferedImage addText(BufferedImage image, List<ImageText> list)`
