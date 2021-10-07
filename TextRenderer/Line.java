package TextRenderer;

import java.util.ArrayList;

public class Line implements RenderObject {
  int x1, y1, x2, y2;
  int layer;
  boolean vertical_first;

  public Line(int x1, int y1, int x2, int y2, int layer){
    this(x1, y1, x2, y2, layer, true);
  }

  public Line(int x1, int y1, int x2, int y2, int layer, boolean vertical_first) {
    if (x1 > x2){
      this.x1 = x2;
      this.y1 = y2;
      this.x2 = x1;
      this.y2 = y1;
    } else{
      this.x1 = x1;
      this.y1 = y1;
      this.x2 = x2;
      this.y2 = y2;
    }

    this.layer = layer;
    this.vertical_first = vertical_first;
  }

  public Pixel[] draw() {
    // Needs to decide whether to go up first or horizontal first
    // I don't think diagonal is possible
    
    ArrayList<Pixel> arr = new ArrayList<Pixel>();   
   
    if (vertical_first){
      if (y2 > y1){
        // draw the vertical part first
        for (int i=0; i<y2-y1; i++){
          arr.add(new Pixel('|', x1, y1+i, layer));
        }
      } else{
        // draw the vertical part first
        for (int i=0; i<y1-y2; i++){
          arr.add(new Pixel('|', x1, y1-i, layer));
        }
      }
      //then horizontal (same no matter which direction line is going)
      for (int i=0; i<=x2-x1; i++){
        arr.add(new Pixel('-', x1+i, y2, layer));
      }
      arr.add(new Pixel('+', x1, y2, layer));
    }else{
      // draw horizontal part first
      for (int i=0; i<=x2-x1; i++){
        arr.add(new Pixel('-', x1+i, y1, layer));
      }
      // then vertical part
      if (y2 > y1){
        for (int i=1; i<=y2-y1; i++){
          arr.add(new Pixel('|', x2, y1+i, layer));
        }
      } else{
        for (int i=0; i<y1-y2; i++){
          arr.add(new Pixel('|', x2, y1-i, layer));
        }
      }
      arr.add(new Pixel('+', x2, y1, layer));
    }
  
    return arr.toArray(new Pixel[arr.size()]);
    
  }
}
