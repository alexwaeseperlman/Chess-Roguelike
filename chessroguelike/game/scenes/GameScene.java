package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.game.map.*;
import chessroguelike.Menu;
import chessroguelike.textRenderer.*;
import java.util.ArrayList;

class GameScene extends Scene {
	Room room;
	Piece player;
    GameScene(int width, int height, Listener listener) {
        super(width, height, listener);
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
		room = new Room(width-3, height-3);
		room.updatePiece(player, new Position(3, 3));
		player.visualizingMove = true;
		player.allowedMoves = Move.knight;

		objects.put(room, new Position(1, 1));
    }

	public void input(char c) {
		if (c == 'l') player.cycleMove(1);
		if (c == 'h') player.cycleMove(-1);
		if (c == 'm') player.allowedMoves[player.selectedMove].apply(player, room);
		refreshScreen();
		if (c == 'q') System.exit(0);

	}
}
