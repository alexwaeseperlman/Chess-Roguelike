package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.game.PlayerStats;
import chessroguelike.Menu;

import chessroguelike.textRenderer.*;

/**
* A subclass of Scene, displayed after player death,
* provides current stats and options for restart,
* main menu, instructions, and exit game
*/
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
    menu = new Menu(new String[] {"Restart", "Main Menu", "Instructions", "Exit Game"}, 30, 1, 2, new Menu.Listener(){
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

    // Text object used to display death message
    // With instructions on how to cycle through the menu options
    t = new Text("YOU DIED, try harder next time \n" + stats.displayStats() + "\nPress 'k' and 'j' to cycle through options.", 21);
    
    // put the menu and the text on the screen
    objects.put(menu, new Position(1, 1));
	objects.put(t, new Position(31, 2));  
    }

    /*
    * For moving up and down between the options
    * @param c : key pressed by the user
    */
    public void input(char c) {
        // cycle through the options
        if (c == 'k') menu.up();
        else if (c == 'j') menu.down();
        // select the options if user pressed y or enter (13 is enter)
        else if (c == 'y' || c == 13) menu.select();
        refreshScreen();
        // ignores invalid input
    }  
}
