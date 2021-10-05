package TextRenderer;

class Point implements RenderObject {
  int x, y, layer;
  Glyph c;

  public Point(Glyph c, int layer) {
    this.c = c;
  }

  public Pixel[] draw() {
    return new Pixel[]{new Pixel(c, x, y, layer)};
  }
}