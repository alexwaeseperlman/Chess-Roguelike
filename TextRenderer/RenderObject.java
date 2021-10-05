package TextRenderer;

class Glyph {
  // foreground, background
  Color fg, bg;
  char shape;
  boolean transparent = false;
  Glyph(char shape) {
    this(shape, Color.WHITE, Color.BLACK);
  }
  Glyph() {
      this(' ', Color.BLACK, Color.BLACK);
      transparent = true;
  }
  Glyph(char shape, Color fg, Color bg) {
    this.fg = fg;
    this.bg = bg;
    this.shape = shape;
  }
}

class Pixel {
  int x, y, layer;
  Glyph c;

  Pixel(char c, int x, int y, int layer) {
    this(new Glyph(c),x,y,layer);
  }

  Pixel(Glyph c, int x, int y, int layer) {
    this.c = c;
    this.x = x;
    this.y = y;
    this.layer = layer;
  }
}

public interface RenderObject {
  public Pixel[] draw();
}