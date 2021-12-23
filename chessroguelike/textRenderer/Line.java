package chessroguelike.textRenderer;

import java.util.ArrayList;

/**
* RenderObject that draws a line
*/
public class Line implements RenderObject {
    // declares variables used to display the current object
    public int x, y;
    public Color fg, bg;
    
    /**
    * Simplified constructor function
    * @param x : change in the x direction from the start of the line
    * @param y : change in the y direction from the start of the line
    */
    public Line(int x, int y){
        this(x, y, Color.WHITE, Color.BLACK);
    }
    
    /**
    * Full constructor function
    * @param x : change in the x direction from the start of the line
    * @param y : change in the y direction from the start of the line
    * @param fg : text color (foreground)
    * @param bg : hight color (backgroud)
    */    
    public Line(int x, int y, Color fg, Color bg) {

        this.x = x;
        this.y = y;
        this.fg = fg;
        this.bg = bg;
    }
    
    /**
    * Draws the required line
    * @return an ArrayList of pixels
    */
    @Override
    public ArrayList<Pixel> draw() {
        ArrayList<Pixel> arr = new ArrayList<Pixel>();
        
        // take the mininum of 0 (starting point) and x (destination) as the start of x, vice versa for endX, and same for Y as well
        int startX = Math.min(0, x), endX = Math.max(0, x),
        startY = Math.min(0, y), endY = Math.max(0, y);
        
        // set values for vertX (the x position to draw the vertical line on)
        // and horizY (the y value to draw the horizontal lien on)
        int vertX = 0, horizY = y;
        
        // Draw the long side first, so if width is greater than height we draw the vertical line at endX
        if (endX-startX > endY-startY) {
            vertX = x;
            horizY = 0;
        }
        
        // draw the horizontal part
        for (int i = startX+1; i < endX; i++) {
            arr.add(new Pixel(new Glyph('─', fg, bg), i, horizY));
        }
        
        // draw the vertical part
        for (int i = startY+1; i < endY; i++) {
            arr.add(new Pixel(new Glyph('│', fg, bg), vertX, i));
        }

        // mark the turn point (horizontal -> vertical) with '+'
        arr.add(new Pixel(new Glyph('+', fg, bg), vertX, horizY));
        // mark the end points with 'x'
        arr.add(new Pixel(new Glyph('x', fg, bg), 0, 0));
        arr.add(new Pixel(new Glyph('x', fg, bg), x, y));

        // return the pixel arraylist
        arr.add(new Pixel(new Glyph('+', fg, bg), vertX, horizY));
        arr.add(new Pixel(new Glyph('x', fg, bg), 0, 0));
        arr.add(new Pixel(new Glyph('x', fg, bg), x, y));

        return arr;
    }
}
