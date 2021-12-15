package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.Menu;
import chessroguelike.textRenderer.*;

/**
 * This is a scene for the main menu. It has options
 * to switch to any of {@link GameScene}, {@link InstructionsScene}, {@link SavedGameScene}, or exit the game
 **/
public class MenuScene extends Scene {
    Menu menu;
    public MenuScene(int width, int height, Listener listener) {
        super(width, height, listener);

		menu = new Menu(new String[]{"Play", "Saved games", "Instructions", "Exit Game"}, 30, 9, new Menu.Listener() {
			public void onSelect(int selection) {
				switch (selection) {
                    // "Play" button
					case 0:
						listener.move(new GameScene(width, height, listener));
						break;
                    // "Saved games" button
					case 1:
						listener.move(new SavedGameScene(width, height, listener));
						break;
                    // "Instructions" button
					case 2:
						listener.move(new InstructionsScene(width, height, listener));
						break;
                    // "Exit game" button
					case 3:
						listener.exit();
						break;
				}

			}
		});

        objects.put(menu, new Position(2, 2));
    }

	public void input(char c) {
        // Use vim keybinds to move between options
		if (c == 'k') menu.up();
		else if (c == 'j') menu.down();
		// 13 is the return key. Both 'y and 'return' can be used to select options
		else if (c == 'y' || c == 13) menu.select();
		refreshScreen();
	}
}
