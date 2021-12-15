package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.game.PlayerStats;
import chessroguelike.Menu;
import chessroguelike.textRenderer.*;

import chessroguelike.game.map.Move;

class TransitionScene extends Scene{  
  Text t;
  Menu menu;

  TransitionScene(int width, int height, Listener listener, PlayerStats stats){
    super(width, height, listener);

    // Get a random piece name using the randomPiece method, to be displayed for the user
    String piece_name = Move.randomPiece();

    // display message for user
    t = new Text("Good Job!\n" + stats.displayStats() + "\n\nIf you choose 'Next Level', you will be playing as: " + piece_name
    , 19);

    // options menu
    menu = new Menu(new String[] {"Next level", "Main Menu", "Save Game", "Instructions", "Exit Game"}, 30, 11, new Menu.Listener(){
      public void onSelect(int selection){
        switch(selection){
            case 0:
                listener.move(new GameScene(width, height, listener, piece_name, stats));
                break;
            case 1:
                listener.move(new InstructionsScene(width, height, listener));
                break;
            case 2:
                // INSERT SCENE FOR SAVING GAME
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

    objects.put(menu, new Position(1, 1));
	objects.put(t, new Position(32, 2));    
    }

    // for handling user input to cycle through the options
    @Override
    public void input(char c) {
        if (c == 'k') menu.up();
        else if (c == 'j') menu.down();
        // 13 is return
        else if (c == 'y' || c == 13) menu.select();
        refreshScreen();
    }  
}