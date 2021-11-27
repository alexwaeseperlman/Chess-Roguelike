package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.game.map.*;
import chessroguelike.Menu;
import chessroguelike.textRenderer.*;
import java.util.ArrayList;

class GameScene extends Scene {
	Room room;
	Piece player;

	Text t;
    GameScene(int width, int height, Listener listener) {
        super(width, height, listener);

		t = new Text("Use 'l' and 'h' to cycle between moves. Press m to make your selected move. Press escape to leave move select mode", 25);

		player = new Piece() {
			@Override
			public boolean alive() {
				return true;
			}

			@Override
			public ArrayList<Pixel> drawPiece() {
				ArrayList<Pixel> out = new ArrayList<Pixel>();
				out.add(new Pixel('P', 0, 0, 5));
				return out;
			}
		};
		room = new Room(20, 10);
		room.updatePiece(player, new Position(3, 3));
		player.visualizingMove = true;
		player.moves = Move.knight;

		objects.put(room, new Position(1, 1));
		objects.put(t, new Position(25, 1));
    }

	public void input(char c) {
		if (c == 'l') {
			player.cycleMove(1);
			player.visualizingMove = true;
		}
		if (c == 'h') {
			player.cycleMove(-1);
			player.visualizingMove = true;
		}
		if (c == '\u001B') player.visualizingMove = false;

		if (c == 'm' || c == 13) player.moves[player.selectedMove].apply(player, room);
		refreshScreen();
	}
}
