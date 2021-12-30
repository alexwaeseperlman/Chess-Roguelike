package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.Menu;

class InstructionsScene extends Scene {

	InstructionsScene(int width, int height, Listener listener) {
		super(width, height, listener);
		objects.put(new Text("Welcome to ChessRogueLike! ♔ ♘\n\nHere you will play as chess piece, capturing enemies and ascending up through the ranks - but beware! One unwary step may lead to your downfall.\n\nUse H J K L to cycle through your possible moves, which will be shown on the board - a red highlight means you're attacking an enemy!\n\nYou can save your progress and load games from the Saved Games menu - sort by date modified, name, or levels completed.\n\nGood luck, adventurer! May your vision stay sharp, and your heart true. In case you forgot, the Knight moves in an 'L' shape...\n\n\nPress 'b' to return to the main menu.", 50), new Position(2, 2));
	}

	public void input(char c) {
		if (c == 'b') {
            		backToMainMenu();
        	}
		refreshScreen();
	}
	
	/**
	 * Return to main menu
	 * */
	void backToMainMenu() {
		listener.move(new MenuScene(width, height, listener));
    	}
}
