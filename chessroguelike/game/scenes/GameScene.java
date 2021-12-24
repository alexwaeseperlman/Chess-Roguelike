package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.game.map.*;
import chessroguelike.Menu;
import chessroguelike.textRenderer.*;
import chessroguelike.game.engine.*;
import chessroguelike.game.PlayerStats;
import java.util.ArrayList;

// regular imports
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
* Subclass of Scene used to display the main game (room
* with player and enemies, enemy engine, and text)
* Handles player movement, and switches to {@link DeathScene}
* or {@link TransitionScene} depending on result
* also has save option
*/
class GameScene extends Scene implements Serializable {
    // declare room, player, enemy engine, and text
	Room room;
	Piece player;
    Engine eng;
	Text t;

    // declare # of enemies, player stats, and piece name
    int enemies;
    PlayerStats stats;
    public String piece_name;

    /**
    * Constructor function for first level, gets a random piece to start
    * with, and created new PlayerStats object
    * @param width : width of the whole scene (NOT the room!)
    * @param height : height of the whole scene (NOT the room!)
    * @param listener : {@link Listener} object used to get input and switch scenes
    */
    GameScene(int width, int height, Listener listener){
        this(width, height, listener, Move.randomPiece(), new PlayerStats());
    }

    /**
    * Constructor function
    * @param width : width of the whole scene (NOT the room!)
    * @param height : height of the whole scene (NOT the room!)
    * @param listener : {@link Listener} object used to get input and switch scenes
    * @param piece_name : name of the piece the player is
    * @param stats : PlayerStats object storing the player's current stats
    */
   GameScene(int width, int height, Listener listener, String piece_name, PlayerStats stats) {
        super(width, height, listener);

        // set piece_name and stats
        this.piece_name = piece_name;
        this.stats = stats;

        // Instruction text to be displaye on screen
		t = new Text("Use 'u' 'h' 'j' & 'k' to select moves. Press m or enter to make your selected move. \nPress escape to leave move select mode, 'q' to quit, 's' to save." + "\n\nCurrently playing as: " + piece_name, 25);

        // creates player
		player = new Piece() {
            // draws the player on the topleft corner
			@Override
			public ArrayList<Pixel> drawPiece() {
				ArrayList<Pixel> out = new ArrayList<Pixel>();
				out.add(new Pixel('P', 0, 0, 5));
				return out;
			}
		};

        // generates random number of enemies between 3 an 5 (inclusive)
        enemies = 3 + (int) (Math.random() * 3);
        // generates the room
		room = Room.generate(20, 10, enemies, player, new Position(3, 3));
        // assigns new enemy engine to the room
        eng = new Engine(room);
        // visualize the player moves
		player.visualizingMove = true;
        // get player moves according to its type (piece_name)
		player.setMoves(Move.pieces.get(piece_name));

        // put the room and the text on screen
		objects.put(room, new Position(1, 1));
		objects.put(t, new Position(25, 1));
    }
    
    /**
    * Handles user input and acts accordingly
    * @param c : character inputted
    */
	public void input(char c) {
        if (c == 'u'){
            player.decrease(1, 0);
            player.visualizingMove = true;
        }

        if (c == 'j'){
            player.increase(1, 0);
            player.visualizingMove = true;
        }

        if (c == 'h'){
            player.decrease(0, 1);
            player.visualizingMove = true;
        }

        if (c == 'k'){
            player.increase(0, 1);
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

        // TODO: Remove after done debugging
        if (c == 'w'){
            win();
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

    /**
    * Gets called when the player looses and moves to DeathScene
    */
    void lose() {
        listener.move(new DeathScene(width, height, listener, stats));
    }
    /**
    * Gets called when the player beats all the enemies
    * updates player stats and moves to {@link TransitionScene}
    */
    void win() {
        // update the stats of the player
        stats.enemies_killed += enemies;
        stats.levels_completed ++;
        stats.addPiece(piece_name);
        // move the the transition scene
        listener.move(new TransitionScene(width, height, listener, stats));
    }
}
