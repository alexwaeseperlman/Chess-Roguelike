package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.game.PlayerStats;
import chessroguelike.Menu;

import chessroguelike.textRenderer.*;

class DeathScene extends Scene{
  Text t;
  Menu menu;

  DeathScene(int width, int height, Listener listener, PlayerStats stats){
    super(width, height, listener);

    /*
    Options Menu
    - Restart
    - Main Menu
    - Instructions
    - Exit Game
    */
    menu = new Menu(new String[] {"Restart", "Main Menu", "Instructions", "Exit Game"}, 30, 9, new Menu.Listener(){
      public void onSelect(int selection){
        switch(selection){
          case 0:
            listener.move(new GameScene(width, height, listener));
            break;
          case 1:
            listener.move(new MenuScene(width, height, listener));
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

    t = new Text("YOU DIED, try harder next time \n" + stats.displayStats() + "\nPress 'k' and 'j' to cycle through options.", 21);

    objects.put(menu, new Position(1, 1));
	objects.put(t, new Position(31, 2));  
    }

    /*
    For moving up and down between the options
    */
    public void input(char c) {
        if (c == 'k') menu.up();
        else if (c == 'j') menu.down();
        // 13 is return
        else if (c == 'y' || c == 13) menu.select();
        refreshScreen();
    }  
}
