package chessroguelike.textRenderer;
public class Pixel {
    int x, y, layer;
    Glyph c;

    Pixel(char c, int x, int y, int layer) {
        this(new Glyph(c),x,y,layer);
    }

    Pixel(Glyph c, int x, int y, int layer) {
        this.c = c;
        this.x = x;
        this.y = y;
        this.layer = layer;
    }
}
