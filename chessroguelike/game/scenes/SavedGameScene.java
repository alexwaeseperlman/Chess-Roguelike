package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.Menu;

class SavedGameScene extends Scene {

    SavedGameScene(int width, int height, Listener listener) {
        super(width, height, listener);
    }

	public void input(char c) {
		refreshScreen();
	}
}
