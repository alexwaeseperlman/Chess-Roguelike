package TextRenderer;

public class Glyph {
  // foreground, background
  Color fg, bg;
  char shape;
  boolean transparent = false;
  public Glyph(char shape) {
    this(shape, Color.WHITE, Color.BLACK);
  }
  public Glyph() {
      this(' ', Color.BLACK, Color.BLACK);
      transparent = true;
  }
  public Glyph(char shape, Color fg, Color bg) {
    this.fg = fg;
    this.bg = bg;
    this.shape = shape;
  }
}