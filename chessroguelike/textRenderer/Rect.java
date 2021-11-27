package chessroguelike.textRenderer;

import java.util.ArrayList;

public class Rect implements RenderObject {
  public int width, height, layer;

  public Color bg = Color.BLACK, fg = Color.WHITE;

  public Rect(int width, int height, int layer) {
    this.width = width;
    this.height = height;
    this.layer = layer;
  }

  public ArrayList<Pixel> draw() {
    ArrayList<Pixel> arr = new ArrayList<Pixel>();
    for (int i = 1; i < height; i++) {
      for (int j = 1; j < width; j++) {
        arr.add(new Pixel(new Glyph('.', fg, bg), j, i, layer-1));
      }
    }

    for (int i = 0; i <= width; i++) {
      arr.add(new Pixel(new Glyph('—', fg, bg), i, 0, layer));
      arr.add(new Pixel(new Glyph('—', fg, bg), i, height, layer));
    }
    for (int i = 0; i <= height; i++) {
      arr.add(new Pixel(new Glyph('│', fg, bg), 0, i, layer));
      arr.add(new Pixel(new Glyph('│', fg, bg), width, i, layer));
    }

    Glyph plus = new Glyph('+', fg, bg);
    arr.add(new Pixel(plus, 0, 0, layer));
    arr.add(new Pixel(plus, 0, height, layer));
    arr.add(new Pixel(plus, width, height, layer));
    arr.add(new Pixel(plus, width, 0, layer));

    return arr;
  }

}
