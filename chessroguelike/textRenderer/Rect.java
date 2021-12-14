package chessroguelike.textRenderer;

import java.util.ArrayList;

/**
 * Render object that draws a rectangle
 **/
public class Rect implements RenderObject {
    public int width, height, layer;

    public Color bg = Color.BLACK, fg = Color.WHITE;

    /**
     * Construct a rectangle render object with
     * the given width and height
     **/
    public Rect(int width, int height, int layer) {
        this.width = width;
        this.height = height;
        this.layer = layer;
    }

    public ArrayList<Pixel> draw() {
        ArrayList<Pixel> arr = new ArrayList<Pixel>();
        // Fill the rectangle with '.' glyphs. 
        // TODO: We might need to be able to change the 
        // fill character for rectangles in the future
        // That hasn't been necessary up until now so
        // there's no need to make any changes
        for (int i = 1; i < height; i++) {
            for (int j = 1; j < width; j++) {
                arr.add(new Pixel(new Glyph('.', fg, bg), j, i, layer-1));
            }
        }

        // Draw borders using dash and pipe characters
        for (int i = 0; i <= width; i++) {
            arr.add(new Pixel(new Glyph('—', fg, bg), i, 0, layer));
            arr.add(new Pixel(new Glyph('—', fg, bg), i, height, layer));
        }
        for (int i = 0; i <= height; i++) {
            arr.add(new Pixel(new Glyph('│', fg, bg), 0, i, layer));
            arr.add(new Pixel(new Glyph('│', fg, bg), width, i, layer));
        }

        // Put '+' characters on the corners to make the border intersections look cleaner
        Glyph plus = new Glyph('+', fg, bg);
        arr.add(new Pixel(plus, 0, 0, layer));
        arr.add(new Pixel(plus, 0, height, layer));
        arr.add(new Pixel(plus, width, height, layer));
        arr.add(new Pixel(plus, width, 0, layer));

        return arr;
    }
}
