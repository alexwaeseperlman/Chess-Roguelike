package chessroguelike.textRenderer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is a render object that simply draws one {@link Glyph} at the origin in object space
 * @see Renderer
 **/
public class Point implements RenderObject {
  Glyph c;

  public Point(Glyph c) {
    this.c = c;
  }

  public ArrayList<Pixel> draw() {
    return new ArrayList<>(Arrays.asList(new Pixel[] {new Pixel(c, 0, 0)}));
  }
}
