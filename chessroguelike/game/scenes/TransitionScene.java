package chessroguelike.game.scenes;

import chessroguelike.game.Scene;
import chessroguelike.game.PlayerStats;
import chessroguelike.Menu;
import chessroguelike.textRenderer.*;

import chessroguelike.game.map.Move;

/**
* Scene used to transition between game scenes after the player
* has won a level.
* Has options to go to next level, main menu, save game, instructions,
* and exit game
*/
class TransitionScene extends Scene{  
    // declares text and menu
    Text t;
    Menu menu;

    /**
    * Constructor function
    * @param width : width of the screen
    * @param height : height of the screen
    * @param listener : Listener used to switch between scenes
    * @param stats : PlayerStats obejct used to store current stats
    */
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

        // putting the menu and text on the screen
        objects.put(menu, new Position(1, 1));
        objects.put(t, new Position(32, 2));    
    }

    // for handling user input to cycle through the options
    @Override
    public void input(char c) {
        // use ''k' and 'j' to cycle through
        if (c == 'k') menu.up();
        else if (c == 'j') menu.down();
        // 13 is return
        else if (c == 'y' || c == 13) menu.select();
        refreshScreen();
    }  
}