package TextRenderer;

public class Point implements RenderObject {
  public int x, y, layer;
  Glyph c;

  public Point(Glyph c, int layer) {
    this.c = c;
    this.layer = layer;
  }
  public Point(Glyph c, int x, int y, int layer) {
    this.c = c;
    this.x = x;
    this.y = y;
    this.layer = layer;
  }

  public Pixel[] draw() {
    return new Pixel[]{new Pixel(c, x, y, layer)};
  }
}