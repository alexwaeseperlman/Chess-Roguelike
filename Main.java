import chessroguelike.textRenderer.*;
import chessroguelike.inputs.*;
import chessroguelike.Menu;
import chessroguelike.game.*;
import chessroguelike.game.scenes.MenuScene;
import java.io.*;


/**
* Driver class for the game
*/
class Main {
    // scene to be displayed on screen
	public static Scene activeScene;
	public static void main(String[] args) {

        try {
            // creates new text file called "err.txt"
            FileOutputStream f =new FileOutputStream("err.txt");

            // prints all system errors & exceptions to text file (instead of disrupting console)
            System.setErr(new PrintStream(f));
        } catch (FileNotFoundException f) {
            // This should never happen
        }
        // creates variable for getting keyboard input
		Input inputs = new Input(System.in);

        // initialize current scene to MenuScene (with new listener)
		activeScene = new MenuScene(50, 15, new Scene.Listener() {
            // overrides exit() function to quit the whole program
			@Override
			public void exit() {
				inputs.close();
				Renderer.close();
				System.exit(0);
			}

            // overrides move() function to change the activeScene and refresh
			@Override
			public void move(Scene s) {
				activeScene = s;
				activeScene.hardRefresh();
			}
		});

        // configure inputs to feed keyboard input into the activeScene
		inputs.addListener(new Input.Listener() {
			@Override
			public void keyPressed(char c) {
				activeScene.input(c);
			}
		});

        // open the input stream
		inputs.open();

        // refreshes the screen
		activeScene.hardRefresh();
	}

    // custom wait function
	public static void wait(int ms) {
		try {
			Thread.sleep(ms);
		}
		catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
}
