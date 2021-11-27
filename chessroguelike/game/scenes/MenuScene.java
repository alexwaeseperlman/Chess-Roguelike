package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.Menu;
import chessroguelike.textRenderer.*;

public class MenuScene extends Scene {
    Menu menu;
    public MenuScene(int width, int height, Listener listener) {
        super(width, height, listener);
		menu = new Menu(new String[]{"Play", "Saved games", "Instructions", "Exit Game"}, 30, 9, new Menu.Listener() {
			public void onSelect(int selection) {
				// Scene 0 is the main menu, so add 1 to each scene
				switch (selection) {
					case 0:
						listener.move(new GameScene(width, height, listener));
						break;
					case 1:
						listener.move(new SavedGameScene(width, height, listener));
						break;
					case 2:
						listener.move(new InstructionsScene(width, height, listener));
						break;
					case 3:
						listener.exit();
						break;
				}

			}
		});

        objects.put(menu, new Position(0, 0));
    }

	public void input(char c) {
		if (c == 'k') menu.up();
		else if (c == 'j') menu.down();
		else if (c == 'y') menu.select();
		refreshScreen();
	}
}
