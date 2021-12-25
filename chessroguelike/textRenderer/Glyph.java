package chessroguelike.textRenderer;

import java.io.Serializable;

/**
 * This class stores a character and a color
 * It can be thought of as an indidual pixel for
 * the text {@link:TextRenderer}
 */
public class Glyph implements Serializable {
    // foreground, background
    Color fg, bg;
    // The shape of this glyph is just a character
    char shape;
    // This flag is only useful for the TextRenderer class
    // It says whether or not to draw this pixel
    boolean transparent = false;

    /**
     * Construct a glyph with default colors
     **/
    public Glyph(char shape) {
        this(shape, Color.WHITE, Color.BLACK);
    }
    /**
     * Construct a transparent glyph (colors don't matter)
     **/
    public Glyph() {
        this(' ', Color.BLACK, Color.BLACK);
        transparent = true;
    }
    /**
     * Construct a glyph with given colors and shape
     * @param fg Foreground color
     * @param bg Background color
     **/
    public Glyph(char shape, Color fg, Color bg) {
        this.fg = fg;
        this.bg = bg;
        this.shape = shape;
    }

    /**
     * Draw this glyph to a string.
     * This is the same as just adding ANSI escape
     * sequences around the 'shape' char
     **/
    public String draw() {
	    return draw(true);
    }

    /**
     * Draw this glyph to a string.
     * This is the same as just adding ANSI escape
     * sequences around the 'shape' char
     * @param color Whether or not this string should be returned with color
     **/
    public String draw(boolean color) {
	    if (color) return Color.color(shape, fg, bg);
	    return "" + shape;
    }

    /**
     * @return Whether or not this Glyph is exactly the same as another
     **/
    public boolean equals(Glyph other) {
	    return other.shape == shape && other.fg.equals(fg) && other.bg.equals(bg);
    }
}
