package chessroguelike.textRenderer;

import java.util.ArrayList;

public class Line implements RenderObject {
  public int x, y;
  int layer;
  boolean vertical_first;

  public Line(int x2, int y2, int layer){
    this(x2, y2, layer, true);
  }

  public Line(int x, int y, int layer, boolean vertical_first) {
    this.x = x;
    this.y = y;

    this.layer = layer;
    this.vertical_first = vertical_first;
  }

  public ArrayList<Pixel> draw() {
    
    ArrayList<Pixel> arr = new ArrayList<Pixel>(); 

    if (vertical_first){
      draw_vertical(0, 0, y, layer, arr);
      draw_horizontal(0, x, y, layer, arr);
      arr.add(new Pixel('+', 0, y, layer));
    } else{
      draw_horizontal(0, x, 0, layer, arr);
      draw_vertical(x, 0, y, layer, arr);
      arr.add(new Pixel('+', x, 0, layer));
    }

    return arr;
  }

  public void draw_vertical(int x, int _y1, int _y2, int layer, ArrayList<Pixel> _arr){
    if (_y1 > _y2){
      int new_y1 = _y2;
      _y2 = _y1;
      _y1 = new_y1;
    }
    for (int i=0; i<_y2 -_y1; i++){
      _arr.add(new Pixel('|', x, _y1+i, layer));
    }
  }

  public void draw_horizontal(int _x1, int _x2, int y, int layer, ArrayList<Pixel> _arr){
    if (_x1 > _x2){
      int new_x1 = _x2;
      _x2 = _x1;
      _x1 = new_x1;
    }
    for (int i=0; i<=_x2 -_x1; i++){
      _arr.add(new Pixel('-', _x1+i, y, layer));
    }
  }
}
