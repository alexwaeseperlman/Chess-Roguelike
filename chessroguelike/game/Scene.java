package chessroguelike.game;

import chessroguelike.textRenderer.*;
import java.io.*;

/**
 * The scene class is a {@link Renderer} that allows you
 * to switch to other renderers
 **/
public abstract class Scene extends Renderer {
    // Used to move between scenes or back to the main menu
    public static interface Listener {
        public void exit();
        public void move(Scene s);
    }
	protected Listener listener;

    // Called when user types a character
    public abstract void input(char c);

    /**
     * Construct a scene object with given width and height
     **/
	public Scene(int width, int height, Listener listener) {
		super(width, height);
		this.listener = listener;
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(this);   
    }
}
