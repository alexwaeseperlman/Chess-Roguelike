package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.Menu;
import chessroguelike.game.map.*;

import chessroguelike.textRenderer.*;
import java.io.*;
import java.util.ArrayList;

/**
 * The scene that lets a user look at their saved files and sort through them.
 * */
class LoadGameScene extends Scene {
	public static final String savePath = "saves/";
    Scene savedGameScene;
	ArrayList<SavedGame> games = new ArrayList<SavedGame>();


	Text date = new Text("Date"), name = new Text("Name"), level = new Text("Level");
	Menu dates, names, levels;

    LoadGameScene(int width, int height, Listener listener) {
        super(width, height, listener);
		objects.put(new Text("Load a game from below, or press 'b' to go back. Press 'd' to sort by date modified, 'n' to sort by name, or 'l' to sort by levels completed.", 50), new Position(4, 4));
		loadSavedGames();
		buildTable(2, 2, width-10, height);
    }
	
	void buildTable(int x, int y, int width, int height) {
		int columnWidth = (width)/3;
		// Remove any previous objects
		objects.remove(date);
		objects.remove(name);
		objects.remove(level);
		objects.remove(dates);
		objects.remove(names);
		objects.remove(levels);

		objects.put(date, new Position(x, y, 10));
		objects.put(name, new Position(x+columnWidth, y, 10));
		objects.put(level, new Position(x+columnWidth*2, y, 10));
		// Build the dates list
		String[] dates = new String[games.size()];
		for (int i = 0; i < games.size(); i++) {
			dates[i] = games.get(i).lastModified.toString();
		}
		this.dates = new Menu(dates, columnWidth, 0, 1);
		this.dates.hideBorders();
		// Render the text with no offset
		this.dates.placeText(0);
		objects.put(this.dates, new Position(x, y+2, 0));

		// Build the name list
		String[] names = new String[games.size()];
		for (int i = 0; i < games.size(); i++) {
			// Cut names off so they don't wrap
			names[i] = games.get(i).name;
			if (names[i].length() >= columnWidth) {
				names[i] = names[i].substring(0, columnWidth-5) + "...";
			}
		}
		// Use the names menu for listeners
		this.names = new Menu(names, columnWidth, 0, 1, new Menu.Listener() {
			@Override
			public void onSelect(int i) {
				chooseFile(i);
			}
		});
		this.names.hideBorders();
		// Render the text with no offset
		this.names.placeText(0);
		objects.put(this.names, new Position(x+columnWidth, y+2, 0));

		// Build the level list
		String[] levels = new String[games.size()];
		for (int i = 0; i < games.size(); i++) {
			levels[i] = Integer.toString(games.get(i).stats.levels_completed);
		}
		this.levels = new Menu(levels, columnWidth, 0, 1);
		this.levels.hideBorders();
		// Render the text with no offset
		this.levels.placeText(0);
		objects.put(this.levels, new Position(x+2*columnWidth, y+2, 0));
	}

	public void input(char c) {  // b to go "back" to main menu
		if (c == 'b') {
		    backToMainMenu();
		}
		else if (c == 'k') {
			up();
		}
		else if (c == 'j') {
			down();
		}
		else if (c == 'd') {
			sortGamesByDate();
			buildTable(4, 6, width-10, height);
		}
		else if (c == 'l') {
			sortGamesByLevel();
			buildTable(4, 6, width-10, height);
		}
		else if (c == 'n') {
			sortGamesByName();
			buildTable(4, 6, width-10, height);
		}
		else if (c == 13) {
			this.names.select();
		}
		refreshScreen();
	}

	/**
	* Wrappers for sorting routines
	**/
	void sortGamesByDate() {
		Collections.sort(games, new CompareDate());
	}
	void sortGamesByLevel() {
		Collections.sort(games, new CompareLevel());
	}
	void sortGamesByName() {
		Collections.sort(games, new CompareName());
	}
	
	void up() {
		dates.up();
		names.up();
		levels.up();
	}
	void down() {
		dates.down();
		names.down();
		levels.down();
	}
	
	void chooseFile(int id) {
		GameScene game = new GameScene(width, height, listener);
		game.loadGame(games.get(id));
		listener.move(game);
	}
	/**
	 * Load all files from the saved game directory.
	 * */
	void loadSavedGames() {
		File files = new File(savePath);
		for (File file : files.listFiles()) {
			loadSavedGame(savePath + file.getName());
		}
	}
	/**
	 * Load an individual saved game by name
	 * */
	void loadSavedGame(String name) {
		try (
			FileInputStream fin = new FileInputStream(name);
			ObjectInputStream oin = new ObjectInputStream(fin);
		) {
			SavedGame sg = (SavedGame)oin.readObject();
			games.add(sg);
		}
		catch (Exception e) {
			// Just stick with the randomly generated room
			e.printStackTrace();
		}
    }
	/**
	 * Return to main menu
	 * */
    void backToMainMenu() {
	    listener.move(new MenuScene(width, height, listener));
    }
}
