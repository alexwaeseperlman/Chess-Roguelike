package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.Menu;

class InstructionsScene extends Scene {

    InstructionsScene(int width, int height, Listener listener) {
        super(width, height, listener);
    }

	public void input(char c) {
		refreshScreen();
	}
}
