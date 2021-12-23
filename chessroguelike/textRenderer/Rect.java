package chessroguelike.textRenderer;

import java.util.ArrayList;

/**
 * Render object that draws a rectangle
 **/
public class Rect implements RenderObject {
    public int width, height;

    public Color bg = Color.BLACK, fg = Color.WHITE;
	// The character in the rectangle background
	public char backgroundChar = '.';

    /**
     * Construct a rectangle render object with
     * the given width and height
     **/
    public Rect(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public ArrayList<Pixel> draw() {
        ArrayList<Pixel> arr = new ArrayList<Pixel>();
        // Fill the rectangle with 'backgroundChar' glyphs. 
        for (int i = 1; i < height; i++) {
            for (int j = 1; j < width; j++) {
                arr.add(new Pixel(new Glyph(backgroundChar, fg, bg), j, i));
            }
        }

        // Draw borders using dash and pipe characters
        for (int i = 0; i <= width; i++) {
            arr.add(new Pixel(new Glyph('—', fg, bg), i, 0));
            arr.add(new Pixel(new Glyph('—', fg, bg), i, height));
        }
        for (int i = 0; i <= height; i++) {
            arr.add(new Pixel(new Glyph('│', fg, bg), 0, i));
            arr.add(new Pixel(new Glyph('│', fg, bg), width, i));
        }

        // Put '+' characters on the corners to make the border intersections look cleaner
        Glyph plus = new Glyph('+', fg, bg);
        arr.add(new Pixel(plus, 0, 0, 2));
        arr.add(new Pixel(plus, 0, height, 2));
        arr.add(new Pixel(plus, width, height, 2));
        arr.add(new Pixel(plus, width, 0, 2));

        return arr;
    }
}
