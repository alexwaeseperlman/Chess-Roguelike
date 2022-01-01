package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.Menu;
import chessroguelike.game.map.*;

import chessroguelike.textRenderer.*;
import java.io.*;
import java.util.Collections;
import java.util.ArrayList;

/**
 * The scene that lets a user look at their saved files and sort through them.
 **/
class LoadGameScene extends Scene {
	public static final String savePath = "saves/";
	// Number of games that are displayed on each page
	final int gamesPerPage = 8;
	int currentPage = 0;
	ArrayList<SavedGame> games = new ArrayList<SavedGame>();
	
	// Index of current position in games, used to retrieve game name for file deletion
	int idx;

	Text date = new Text("Date"), name = new Text("Name"), level = new Text("Level");
	Text page = new Text("Page 1/1");
	Menu dates, names, levels;

	LoadGameScene(int width, int height, Listener listener) {
	super(width, height, listener);
		objects.put(new Text("Load a game from below, or press 'b' to go back. Press 'd' to sort by date modified, 'n' to sort by name, or 'c' to sort by levels completed. Press 'x' to delete the game. Use 'l' and 'h' to switch between pages.", 50), new Position(2, 2));
		loadSavedGames();
		rebuildTable();
		updatePageMarker();
	}

	/**
	 * Update the message that displays what page the user is on
	 * */
	void updatePageMarker() {
		objects.remove(page);
		// Add one to currentPage because it's zero indexed
		// Use ceiling integer division to calculate the number of pages
		// ceil(a/b) = floor((a+b-1)/b)
		page.content = String.format("Page %d/%d", currentPage+1, (games.size() + gamesPerPage-1)/gamesPerPage);
		objects.put(page, new Position(width-15, height-5));
	}

	/**
	 * Build a table displaying all the game files. Deletes everything from the previous table
	 * @param x : The x position of the top left corner of the table
	 * @param y : The y position of the top left corner of the table
	 * @param width : The width of the table
	 * @param height : The height of the table
	 * */
	void buildTable(int x, int y, int width, int height) {
		idx = currentPage * gamesPerPage;
		// First and last games that are displayed on this page
		int firstGame = currentPage*gamesPerPage, lastGame = Math.min(games.size(), (currentPage+1)*gamesPerPage);
		int columnWidth = 20;
		// Remove any previous objects
		objects.remove(date);
		objects.remove(name);
		objects.remove(level);
		objects.remove(dates);
		objects.remove(names);
		objects.remove(levels);

		objects.put(date, new Position(x, y, 10));
		objects.put(name, new Position(x+columnWidth, y, 5));
		objects.put(level, new Position(x+columnWidth*2, y, 5));
		// Build the dates list
		String[] dates = new String[lastGame-firstGame];
		for (int i = firstGame; i < lastGame; i++) {
			dates[i-firstGame] = games.get(i).lastModified.toString();
		}
		this.dates = new Menu(dates, columnWidth, 0, 1);
		this.dates.hideBorders();
		// Render the text with no offset
		this.dates.placeText(0);
		objects.put(this.dates, new Position(x, y+2, 0));

		// Build the name list
		String[] names = new String[lastGame-firstGame];
		for (int i = firstGame; i < lastGame; i++) {
			// Cut names off so they don't wrap
			names[i-firstGame] = games.get(i).name;
			if (names[i-firstGame].length() >= columnWidth) {
				names[i-firstGame] = names[i-firstGame].substring(0, columnWidth-5) + "...";
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
		String[] levels = new String[lastGame-firstGame];
		for (int i = firstGame; i < lastGame; i++) {
			levels[i-firstGame] = Integer.toString(games.get(i).stats.levels_completed);
		}
		this.levels = new Menu(levels, columnWidth, 0, 1);
		this.levels.hideBorders();
		// Render the text with no offset
		this.levels.placeText(0);
		objects.put(this.levels, new Position(x+2*columnWidth, y+2, 0));
	}

	/**
	 * Call {@link #buildTable(int, int, int, int)} with default parameters
	 * */
	void rebuildTable() {
        idx = 0;
		buildTable(2, 5, width-15, height);
	}

	public void input(char c) {  // b to go "back" to main menu
		if (c == 'b') {
		    backToMainMenu();
		}
		// Moving up and down
		else if (c == 'k') {
			up();
		}
		else if (c == 'j') {
			down();
		}
		// Types of sorting
		else if (c == 'd') {
			sortGamesByDate();
			rebuildTable();
		}
		else if (c == 'c') {
			sortGamesByLevel();
			rebuildTable();
		}
		else if (c == 'n') {
			sortGamesByName();
			rebuildTable();
		}
		else if (c == 'x') {  // delete the current game
			deleteGame();
			rebuildTable();
		}
		// Page switching
		else if (c == 'l' && currentPage+1 < (games.size()+gamesPerPage-1)/gamesPerPage) {
			currentPage++;
			rebuildTable();
			updatePageMarker();
		}
		else if (c == 'h' && currentPage-1 >= 0) {
			currentPage--;
			rebuildTable();
			updatePageMarker();
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
		// Level is sorted descending
		Collections.sort(games, Collections.reverseOrder(new CompareLevel()));
	}
	void sortGamesByName() {
		Collections.sort(games, new CompareName());
	}
	
	/**
	 * Move the cursor up on all relevant menu objects
	 * */
	void up() {
		dates.up();
		names.up();
		levels.up();
		idx--;
		if (idx < currentPage * gamesPerPage) {
			idx = Math.min(currentPage * gamesPerPage + gamesPerPage - 1, games.size() - 1);
		}
	}
	/**
	 * Move the cursor down on all relevant menu objects
	 * */
	void down() {
		dates.down();
		names.down();
		levels.down();
        	idx++;
		if (idx > Math.max(currentPage * gamesPerPage + gamesPerPage - 1, games.size() - 1)) {
			idx = currentPage * gamesPerPage;
		}
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

	void deleteGame() {
		String filename = games.get(idx).name + ".ser";
		games.remove(idx);
		File deleteThis = new File(savePath + filename);
		deleteThis.delete();
	}

	/**
	 * Return to main menu
	 * */
	void backToMainMenu() {
    		listener.move(new MenuScene(width, height, listener));
	}
}
