package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.game.map.*;
import chessroguelike.Menu;
import chessroguelike.textRenderer.*;

class GameScene extends Scene {
	Room room;
    GameScene(int width, int height, Listener listener) {
        super(width, height, listener);
		room = new Room(width-10, height-10);

		objects.put(room, new Position(1, 1));

    }

	public void input(char c) {
		refreshScreen();
		if (c == 'q') System.exit(0);

	}
}
