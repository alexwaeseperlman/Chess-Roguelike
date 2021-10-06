package TextRenderer;

import java.util.ArrayList;

class Line implements RenderObject {
  int x1, y1, x2, y2;
  int layer;
  boolean diagonal, vertical_first;

  public Line(int x1, int y1, int x2, int y2, int layer){
    Line(x1, y1, x2, y2, layer, false, true);
  }

  public Line(int x1, int y1, int x2, int y2, int layer, boolean diagonal, boolean vertical_first) {
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
    this.diagonal = diagonal;
    this.vertical_first = vertical_first;
  }

  public Pixel[] draw() {
    // Needs to decide whether to go up first or horizontal first
    // I don't think diagonal is possible
    
    ArrayList<Pixel> arr = new ArrayList<Pixel>();


    // handles vertical first then horizontal

    // could add feature to adjust coordinates so the line always goes from left to right (to reduce the number of if statements)

    // MOVE THIS INTO THE INITIATION
    // changes coordinates so that line always goes from left to right
    if (x1 > x2){
      int new_x1, new_y1;
      new_x1 = x2;
      new_y1 = y2;
      x2 = x1;
      y2 = y1;
      x1 = new_x1;
      y1 = new_y1;
    }
    


    if (this.diagonal) {
      double slope = (double) (y2-y1) / (x2-x1);

      int y_pos = 1;
      double y_increment = 0;

      if (slope < 0){
        // line is going from top left corner to bottom right corner
        for (int x=0; x < x1-x2; x++){
          arr.add(new Pixel('\\', x1 + x, y1 - y_pos, layer));
          
          y_increment += slope;
          y_pos += 1;
          while (y_increment <= y_pos){
            arr.add(new Pixel('|', x1 + x, y1 - y_pos, layer));
            y_pos += 1;
          }
        }
      } else{
        for (int x=0; x < x2-x1; x++){
          arr.add(new Pixel('/', x1 + x, y1 + y_pos, layer));
          
          y_increment += slope;
          y_pos += 1;
          while (y_increment <= y_pos){
            arr.add(new Pixel('|', x1 + x, y1 + y_pos, layer));
            y_pos += 1;
          }
        }
      }

      
    } else{
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
        for (int i=0; i<x2-x1; i++){
          arr.add(new Pixel('-', x2+i, y2, layer));
        }
      }else{
        // draw horizontal part first
        for (int i=0; i<x2-x1; i++){
          arr.add(new Pixel('-', x1+i, y1, layer));
        }
        // then vertical part
        if (y2 > y1){
          // draw the vertical part first
          for (int i=0; i<y2-y1; i++){
            arr.add(new Pixel('|', x2, y1+i, layer));
          }
        } else{
          // draw the vertical part first
          for (int i=0; i<y1-y2; i++){
            arr.add(new Pixel('|', x2, y1-i, layer));
          }
        }
      }
    }
    
    return arr.toArray(new Pixel[arr.size()]);
    
  }
}
