package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.Menu;

import chessroguelike.textRenderer.*;

class TransitionScene extends Scene{  
  Text t;
  Menu menu;

  TransitionScene(int width, int height, Listener listener){
    super(width, height, listener);

    menu = new Menu(new String[] {"Next level", "Main Menu", "Instructions", "Save Game", "Exit Game"}, 30, 11, new Menu.Listener(){
      public void onSelect(int selection){
        switch(selection){
          case 0:
            listener.move(new GameScene(width, height, listener));
            break;
          case 1:
            listener.move(new InstructionsScene(width, height, listener));
            break;
          // NOTE: Not sure if this is the scene for saving game or for loading game. Please make sure the linked scene is for saving the current game
          case 2:
            listener.move(new SavedGameScene(width, height, listener));
            break;
          case 3:
            listener.move(new MenuScene(width - 25, height, listener));
            break;
          case 4:
            listener.exit();
            break;
          
        }
      }
    });

    /*
    PLEASE MODIFY THIS TEXT AFTER THE PLAYER KNOWS WHAT MOVES IT IS PLAYING AS
    */
    t = new Text("Good Job completing a level! To save your progress, press 'Save Game'\n" + "You will be playing as: " + 
    , 25);

    objects.put(menu, new Position(2, 2));
		objects.put(t, new Position(40, 2));    
  }

  public void input(char c) {
		if (c == 'k') menu.up();
		else if (c == 'j') menu.down();
		// 13 is return
		else if (c == 'y' || c == 13) menu.select();
		refreshScreen();
	}  
}