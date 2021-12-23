package chessroguelike.textRenderer;

/**
 * This class is essentially just a wrapper to contain
 * a glyph along with a position. This is how
 * glyphs are passed around between objects
 **/
public class Pixel {
	Position pos;
    Glyph c;

    /**
     * Generate a glyph at the given position
     **/
    public Pixel(char c, int x, int y) {
        this(new Glyph(c),x,y);
    }

    /**
     * Generate a pixel with the given glyph at the given position
     **/
    public Pixel(Glyph c, int x, int y) {
		this.c = c;
		this.pos = new Position(x, y, 0);
    }

    /**
     * Generate a glyph at the given position and layer
     **/
    public Pixel(char c, int x, int y, int layer) {
        this(new Glyph(c),x,y,layer);
    }

    /**
     * Generate a pixel with the given glyph at the given position
     **/
    public Pixel(Glyph c, int x, int y, int layer) {
        this.c = c;
		this.pos = new Position(x, y, layer);
    }
}
