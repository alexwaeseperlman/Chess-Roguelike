package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.Menu;

import chessroguelike.textRenderer.*;
import java.io.*;

class LoadGameScene extends Scene {
    Scene savedGameScene;
    LoadGameScene(int width, int height, Listener listener) {
        super(width, height, listener);
        objects.put(new Text("Game loaded, press 'c' to continue, or 'b' to go back to main menu.", 25), new Position(2, 2));
    }

	public void input(char c) {  // b to go "back" to main menu
        if (c == 'b') {
            backToMainMenu();
        }
        if (c == 'c') {
            goToGameScene();
        }
	}

    void goToGameScene() {
		GameScene game = new GameScene(width, height, listener);
		try {
			FileInputStream fin = new FileInputStream("SavedGame.ser");
			ObjectInputStream oin = new ObjectInputStream(fin);
			SavedGame sg = (SavedGame)oin.readObject();
			game.loadGame(sg);
		}
		catch (Exception e) {
			// Just stick with the randomly generated room
			e.printStackTrace();
		}
        listener.move(game);
    }
    void backToMainMenu() {
        listener.move(new MenuScene(width, height, listener));
    }
}
