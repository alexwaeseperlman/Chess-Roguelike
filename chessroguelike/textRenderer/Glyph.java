package chessroguelike.textRenderer;

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
  public String draw() {
	  return draw(true);
  }
  public String draw(boolean color) {
	if (color) return Color.color(shape, fg, bg);
	return "" + shape;
  }

  public boolean equals(Glyph other) {
	  return other.shape == shape && other.fg.equals(fg) && other.bg.equals(bg);
  }
}
