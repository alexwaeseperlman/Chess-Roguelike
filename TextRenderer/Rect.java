package TextRenderer;

import java.util.ArrayList;

public class Rect implements RenderObject {
  public int x, y, width, height, layer;

  public Rect(int x, int y, int width, int height, int layer) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.layer = layer;
  }

  public Pixel[] draw() {
    ArrayList<Pixel> arr = new ArrayList<Pixel>();
    for (int i = 0; i <= width; i++) {
      arr.add(new Pixel('-', x+i, y, layer));
      arr.add(new Pixel('-', x+i, y+height, layer));
    }

    for (int i = 0; i <= height; i++) {
      arr.add(new Pixel('|', x, y+i, layer));
      arr.add(new Pixel('|', x+width, y+i, layer));
    }

    for (int i = 1; i < height; i++) {
      for (int j = 1; j < width; j++) {
        arr.add(new Pixel('.', x+i, y+j, layer));
      }
    }

    arr.add(new Pixel('+', x, y, layer));
    arr.add(new Pixel('+', x, y+height, layer));
    arr.add(new Pixel('+', x+width, y+height, layer));
    arr.add(new Pixel('+', x+width, y, layer));

    return arr.toArray(new Pixel[arr.size()]);
  }

}