package chessroguelike.textRenderer;

/**
 * This class is essentially just a wrapper to contain
 * a glyph along with a position. This is how
 * glyphs are passed around between objects
 **/
public class Pixel {
    int x, y, layer;
    Glyph c;

    /**
     * Generate a glyph at the given position
     **/
    public Pixel(char c, int x, int y, int layer) {
        this(new Glyph(c),x,y,layer);
    }

    /**
     * Generate a pixel with the given glyph at the given position
     **/
    public Pixel(Glyph c, int x, int y, int layer) {
        this.c = c;
        this.x = x;
        this.y = y;
        this.layer = layer;
    }
}
