package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.Menu;

import java.io.Serializable;

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
        objects.put(new Text("Saved Games", 20), new Position(2, 2));
    }

	public void input(char c) {  // b to go "back" to main menu
        if (c == 'b') {
            backToMainMenu();
        }
       // else if (c == 's') {  // save game info to .ser file
            //saveGame();
        //    backToMainMenu();
        //}
	}

    void backToMainMenu() {
        listener.move(new MenuScene(width, height, listener));
    }
}
