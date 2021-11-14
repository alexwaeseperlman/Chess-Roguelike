package chessroguelike.textRenderer;

import java.util.ArrayList;

public class Rect implements RenderObject {
  public int x, y, width, height, layer;

  public Color bg = Color.BLACK, fg = Color.WHITE;

  public Rect(int x, int y, int width, int height, int layer) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.layer = layer;
  }

  public ArrayList<Pixel> draw() {
    ArrayList<Pixel> arr = new ArrayList<Pixel>();
    for (int i = 0; i <= width; i++) {
      arr.add(new Pixel(new Glyph('—', fg, bg), x+i, y, layer));
      arr.add(new Pixel(new Glyph('—', fg, bg), x+i, y+height, layer));
    }

    for (int i = 0; i <= height; i++) {
      arr.add(new Pixel(new Glyph('│', fg, bg), x, y+i, layer));
      arr.add(new Pixel(new Glyph('│', fg, bg), x+width, y+i, layer));
    }

    for (int i = 1; i < height; i++) {
      for (int j = 1; j < width; j++) {
        arr.add(new Pixel(new Glyph(' ', fg, bg), x+j, y+i, layer-1));
      }
    }
    Glyph plus = new Glyph('+', fg, bg);
    arr.add(new Pixel(plus, x, y, layer));
    arr.add(new Pixel(plus, x, y+height, layer));
    arr.add(new Pixel(plus, x+width, y+height, layer));
    arr.add(new Pixel(plus, x+width, y, layer));

    return arr;
  }

}
