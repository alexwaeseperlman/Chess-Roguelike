package TextRenderer;

import java.util.ArrayList;

public class Line implements RenderObject {
  public int x1, y1, x2, y2;
  int _x1, _y1, _x2, _y2;
  int layer;
  boolean vertical_first;

  public Line(int x1, int y1, int x2, int y2, int layer){
    this(x1, y1, x2, y2, layer, true);
  }

  public Line(int x1, int y1, int x2, int y2, int layer, boolean vertical_first) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;

    this.layer = layer;
    this.vertical_first = vertical_first;
  }

  public Pixel[] draw() {
    
    ArrayList<Pixel> arr = new ArrayList<Pixel>(); 

    if (vertical_first){
      draw_vertical(x1, y1, y2, layer, arr);
      draw_horizontal(x1, x2, y2, layer, arr);
      arr.add(new Pixel('+', x1, y2, layer));
    } else{
      draw_horizontal(x1, x2, y1, layer, arr);
      draw_vertical(x2, y1, y2, layer, arr);
      arr.add(new Pixel('+', x2, y1, layer));
    }

    return arr.toArray(new Pixel[arr.size()]);
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
