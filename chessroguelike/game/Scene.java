package chessroguelike.game;

import chessroguelike.textRenderer.*;

public abstract class Scene extends Renderer {
    // Used to move between scenes or back to the main menu
    public static interface Listener {
        public void exit();
        public void move(Scene s);
    }
	protected Listener listener;

    // Called when user types a character
    public abstract void input(char c);

	public Scene(int width, int height, Listener listener) {
		super(width, height);
		this.listener = listener;
	}
}
