package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.game.map.*;
import chessroguelike.Menu;
import chessroguelike.textRenderer.*;
import chessroguelike.game.engine.*;
import java.util.ArrayList;

// regular imports
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

class GameScene extends Scene implements Serializable {
	Room room;
	Piece player;
    Engine eng;

	Text t;
    GameScene(int width, int height, Listener listener) {
        super(width, height, listener);

		t = new Text("Use 'l' and 'h' to cycle between moves. Press m to make your selected move. Press escape to leave move select mode, press 's' to save.", 25);

		player = new Piece() {
			@Override
			public ArrayList<Pixel> drawPiece() {
				ArrayList<Pixel> out = new ArrayList<Pixel>();
				out.add(new Pixel('P', 0, 0, 5));
				return out;
			}
		};
		room = Room.generate(20, 10, 3, player, new Position(3, 3));
        eng = new Engine(room);
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

		if (c == 'm' || c == 13) {
            boolean allowed = player.moves[player.selectedMove].allowed(player, room);
            if (allowed) {
                Piece target = player.moves[player.selectedMove].apply(player, room);
                // Only move ai if player didn't take a piece
                if (target == null) eng.makeMoves(player);
            }
        }
        if (c == 's') {
            saveGame();
            toGameSavedMsg();
        }
        if (c == 'q') {
            backToMainMenu();
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

    void saveGame() {
        try {
            FileOutputStream fileOutputStream = 
                new FileOutputStream("SavedGames.ser");
            ObjectOutputStream objectOutputStream = 
                new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            fileOutputStream.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.room = (Room) ois.readObject();
        this.player = (Piece) ois.readObject();
        this.eng = (Engine) ois.readObject();
    }

    void loadGame() throws IOException, ClassNotFoundException, FileNotFoundException {
        FileInputStream fin = new FileInputStream("SavedGames.ser");
        ObjectInputStream oin = new ObjectInputStream(fin);
        this.room = (Room) oin.readObject();
        this.player = (Piece) oin.readObject();
        this.eng = (Engine) oin.readObject();
        oin.close();
    }

    void backToMainMenu() {
        listener.move(new MenuScene(width, height, listener));
    }
    void toGameSavedMsg() {
        listener.move(new GameSavedMsg(width, height, listener));
    }
    void lose() {
        listener.move(new MenuScene(width, height, listener));
    }
    void win() {
        listener.move(new MenuScene(width, height, listener));
    }
}
