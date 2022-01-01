package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.game.map.*;
import chessroguelike.*;
import chessroguelike.Menu;
import chessroguelike.textRenderer.*;
import chessroguelike.game.engine.*;
import chessroguelike.game.PlayerStats;
import java.util.ArrayList;

// regular imports
import java.io.*;

/**
* Subclass of Scene used to display the main game (room
* with player and enemies, enemy engine, and text)
* Handles player movement, and switches to {@link DeathScene}
* or {@link TransitionScene} depending on result
* also has save option
*/
public class GameScene extends Scene {
    // declare room, player, enemy engine, and text
	public Room room;
	public Piece player;
    Engine eng;
	Text t;

	// A flag that decides whether input should go to the saveGame popup or the actual game
	public boolean savingGame = false;

	TextBox.Listener savedGameListener = new TextBox.Listener() {
		@Override
		public void submitted(String name) {
			saveGame(name);
		}

		@Override
		public void cancelled() {
			cancelSaveGame();
		}
	};
	TextBox saveGamePopup = new TextBox("Save Game", 40, 3, savedGameListener);

    // declare # of enemies, player stats, and piece name
    int enemies;
    public PlayerStats stats;
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
		t = new Text("Use 'h' 'j' 'k' & 'l' to select moves. Press m or enter to make your selected move. \nPress escape to leave move select mode, 'q' to quit, 's' to save." + "\n\nCurrently playing as: " + piece_name, 25);

        // creates player
		player = new Piece('P');

        // generates random number of enemies between 3 an 5 (inclusive)
        enemies = 3 + (int) (Math.random() * 3);
        // generates the room
		room = Room.generate(20, 10, enemies, player, new Position(3, 3));

		initialize();
    }

   /**
	* Construct a game scene from a saved file
	* @param width : Width of scene
	* @param height : Height of scene
	* @param saved : Saved game object containing information that should be loaded
	* @param listener : {@link Listener} To change scenes and get input
	* */
	GameScene(int width, int height, SavedGame saved, Listener listener) {
		super(width, height, listener);
		saved.apply(this);
		initialize();
	}

	// Add all the necessary objects to the renderer and construct an engine
	public void initialize() {
		objects.clear();
    	// assigns new enemy engine to the room
        eng = new Engine(room);
        // get player moves according to its type (piece_name)
		player.setMoves(Move.pieces.get(piece_name));
        // visualize the player moves
		player.visualizingMove = true;

        // put the room and the text on screen
		objects.put(room.renderer, new Position(1, 1));
		objects.put(t, new Position(25, 1));
		room.renderer.refresh();
		refreshScreen();
	}
    
    /**
    * Handles user input and acts accordingly
    * @param c : character inputted
    */
	public void input(char c) {
		// Handle the saved game popup
		if (savingGame) {
			saveGamePopup.type(c);
			refreshScreen();
			return;
		}
		Position playerPosition = player.getSelectedMove().simulate(player, room);
        // move up (decrease in y direction)
        if (c == 'k'){
            player.increase(new Position(0, 1), playerPosition, room);
        }

        // move down (increase in y direction)
        if (c == 'j'){
            player.increase(new Position(0, -1), playerPosition, room);
        }

        // move left (increase in x direction)
        if (c == 'h'){
            player.increase(new Position(1, 0), playerPosition, room);
        }

        // move right (decrease in x direction)
        if (c == 'l'){
            player.increase(new Position(-1, 0), playerPosition, room);
        }


		if (c == 'm' || c == 13) {
            boolean allowed = player.getSelectedMove().allowed(player, room);
            if (allowed) {
                // apply the move
                Piece target = player.getSelectedMove().apply(player, room);

                // refresh screen, then sleep for 0.5 seconds
                // so user can see what they have done
                refreshScreen();
                //try {
                    //Thread.sleep(500);
                //}
                //catch(InterruptedException ex) {
                    //Thread.currentThread().interrupt();
                //}

                // Only move ai if player didn't take a piece
                if (target == null){
                    eng.makeMoves(player);
                }
            }
        }
        if (c == 's') {
            saveGame();
            //toGameSavedMsg();
        }
        if (c == 'q') {
            backToMainMenu();
        }

        // TODO: Remove after done debugging
        if (c == 'w'){
            win();
        }      

        // if the player can attack, change the line color to red
        player.attacking = player.getSelectedMove().wouldAttack(player, room);

        // if the move is out of bounds, do not display
        player.visualizingMove = player.getSelectedMove().allowed(player, room);

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


	void cancelSaveGame() {
		objects.remove(saveGamePopup);
		refreshScreen();
	}
	void saveGame() {
		savingGame = true;
		saveGamePopup.title.content = "Enter a name for your saved file";
		objects.put(saveGamePopup, new Position(5, 5, 10));
	}
    
    void saveGame(String fileName) {
		// TODO: Use smarter file path joining
		File f = new File(LoadGameScene.savePath + "/" + fileName + ".ser");
		// Don't overwrite existing files
		if (f.exists()) {
			saveGamePopup.title.content = "That file already exists.";
			refreshScreen();
			return;
		}
		// This means that the file was saved
		savingGame = false;
        try (
			FileOutputStream fos = new FileOutputStream(LoadGameScene.savePath + "/" + fileName + ".ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
		) {
			SavedGame saved = save();
			saved.name = fileName;
            oos.writeObject(saved);
        } catch (Exception e) {
			e.printStackTrace();
        }
		// Close the popup
		cancelSaveGame();
	}

    void backToMainMenu() {
        listener.move(new MenuScene(width, height, listener));
    }
    void toGameSavedMsg() {
        listener.move(new LoadGameScene(width, height, listener));
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
        stats.levels_completed++;
        stats.addPiece(piece_name);
        // move the the transition scene
        listener.move(new TransitionScene(width, height, listener, stats));
    }
	/**
	 * Generate a {@link SavedGame} object from a {@link GameScene}
	 * */
	SavedGame save() {
		SavedGame saved = new SavedGame();
		saved.stats = this.stats;
		saved.room = this.room;
		saved.player = this.player;
		saved.piece_name = this.piece_name;
		return saved;
	}

	/**
	 * Apply a saved game to this scene and reinitialize
	 * */
	void loadGame(SavedGame saved) {
		saved.apply(this);
		initialize();
	}
}
