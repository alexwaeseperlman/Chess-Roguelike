package chessroguelike.textRenderer;

import java.util.ArrayList;

public class Line implements RenderObject {
    public int x, y;
    public Color fg, bg;
    
    public Line(int x, int y){
        this(x, y, Color.WHITE, Color.BLACK);
    }
    
    public Line(int x, int y, Color fg, Color bg) {
        this.x = x;
        this.y = y;
        this.fg = fg;
        this.bg = bg;
    }
    
    @Override
    public ArrayList<Pixel> draw() {
        ArrayList<Pixel> arr = new ArrayList<Pixel>();
        
        int startX = Math.min(0, x), endX = Math.max(0, x),
        startY = Math.min(0, y), endY = Math.max(0, y);
        
        int vertX = 0, horizY = y;
        
        // Draw the long side first, so if width is greater than height we draw the vertical line at endX
        if (endX-startX > endY-startY) {
            vertX = x;
            horizY = 0;
        }
        
        for (int i = startX+1; i < endX; i++) {
            arr.add(new Pixel(new Glyph('─', fg, bg), i, horizY));
        }
        
        for (int i = startY+1; i < endY; i++) {
            arr.add(new Pixel(new Glyph('│', fg, bg), vertX, i));
        }
        
        arr.add(new Pixel(new Glyph('+', fg, bg), vertX, horizY));
        arr.add(new Pixel(new Glyph('x', fg, bg), 0, 0));
        arr.add(new Pixel(new Glyph('x', fg, bg), x, y));
    
        return arr;
    }
}
