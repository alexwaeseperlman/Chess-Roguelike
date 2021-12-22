package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.Menu;

import chessroguelike.textRenderer.*;
/*
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
*/

class SavedGameScene extends Scene {
    Scene savedGameScene;
    SavedGameScene(int width, int height, Listener listener) {
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
        listener.move(new GameScene(width, height, listener));
    }
    void backToMainMenu() {
        listener.move(new MenuScene(width, height, listener));
    }
}
