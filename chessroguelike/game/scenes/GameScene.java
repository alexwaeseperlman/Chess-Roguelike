package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.game.PlayerStats;
import chessroguelike.game.map.*;
import chessroguelike.Menu;
import chessroguelike.textRenderer.*;
import chessroguelike.game.engine.*;
import java.util.ArrayList;

class GameScene extends Scene {
	Room room;
	Piece player;
    Engine eng;

    int enemies;
    PlayerStats stats;

    public String piece_name;

	Text t;
    GameScene(int width, int height, Listener listener){
        this(width, height, listener, Move.randomPiece(), new PlayerStats());
    }

    GameScene(int width, int height, Listener listener, String piece_name, PlayerStats stats) {
        super(width, height, listener);

        this.piece_name = piece_name;
        this.stats = stats;

		t = new Text("Use 'l' and 'h' to cycle between moves. \nPress m to make your selected move. \nPress escape to leave move select mode." + "\n\nCurrently playing as: " + piece_name, 25);

		player = new Piece() {
			@Override
			public ArrayList<Pixel> drawPiece() {
				ArrayList<Pixel> out = new ArrayList<Pixel>();
				out.add(new Pixel('P', 0, 0, 5));
				return out;
			}
		};

        // generates random number of enemies between 3 an 5 (inclusive)
        enemies = 3 + (int) (Math.random() * 3);

		room = Room.generate(20, 10, enemies, player, new Position(3, 3));
        
        eng = new Engine(room);
		player.visualizingMove = true;
		player.moves = Move.pieces.get(piece_name);

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

        if (c == 'w'){
            win();
        }

		if (c == 'm' || c == 13) {
            boolean allowed = player.moves[player.selectedMove].allowed(player, room);
            if (allowed) {
                Piece target = player.moves[player.selectedMove].apply(player, room);
                // Only move ai if player didn't take a piece
                if (target == null) {
                    eng.start();
                    eng.makeMoves(player);
                }
            }
        }
        player.attacking = player.moves[player.selectedMove].wouldAttack(player, room);
        player.visualizingMove = player.moves[player.selectedMove].allowed(player, room);

        if (!room.pieces.containsKey(player)) {
            // Go to you lose screen
            lose();
        }
        else if (room.pieces.size() == 1 && room.pieces.containsKey(player)) {
            // Go to you win screen
            win();
        }
        // Don't call refresh screen after a scene transition
		else refreshScreen();
	}
    void lose() {
        listener.move(new DeathScene(width, height, listener, stats));
    }
    void win() {
        stats.enemies_killed += enemies;
        stats.levels_completed ++;
        stats.addPiece(piece_name);
        listener.move(new TransitionScene(width, height, listener, stats));
    }
}
