package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.Menu;
import chessroguelike.textRenderer.*;

/**
* Transitional scene used to display message, indicating that
* the game has been saved. With options for loading or going back
* to main menu
*/
public class GameSavedMsg extends Scene {
    Menu menu;
    public GameSavedMsg(int width, int height, Listener listener) {
        super(width, height, listener);
		Text t = new Text("Game has been saved - press 'l' to load saved games, or 'b' to go back to main menu.", 25);
        objects.put(t, new Position(2, 2));
    }
    
    void backToMainMenu() {
        listener.move(new MenuScene(width, height, listener));
    }

	public void input(char c) {
		if (c == 'b') {
            backToMainMenu();
        } 
        if (c == 'l') {
            toGameScene();
        }
    }

    void toGameScene() {
        listener.move(new GameScene(width, height, listener));
    }
}
