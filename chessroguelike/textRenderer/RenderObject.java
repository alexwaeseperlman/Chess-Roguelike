package chessroguelike.textRenderer;
import java.util.ArrayList;

/**
 * {@link RenderObject} is an interface that means that an object can be drawn.
 * This is one of the places where we used polymorphism. 
 * There are many objects that implement {@link RenderObject} but
 * the {@link Renderer} class can't tell the difference between them
 * */
public interface RenderObject {
	public ArrayList<Pixel> draw();
}
