package chessroguelike.textRenderer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is a render object that simply draws one {@link Glyph} at the origin in object space
 * @see Renderer
 **/
public class Point implements RenderObject {
  public int layer;
  Glyph c;

  public Point(Glyph c, int layer) {
    this.c = c;
    this.layer = layer;
  }

  public ArrayList<Pixel> draw() {
    return new ArrayList<>(Arrays.asList(new Pixel[] {new Pixel(c, 0, 0, layer)}));
  }
}
