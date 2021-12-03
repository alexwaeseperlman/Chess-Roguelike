import chessroguelike.textRenderer.*;
import chessroguelike.inputs.*;
import chessroguelike.Menu;
import chessroguelike.game.*;
import chessroguelike.game.scenes.MenuScene;
import java.io.*;

class Main {
	public static Scene activeScene;
	public static void main(String[] args) {
        try {
            FileOutputStream f =new FileOutputStream("err.txt");
    
            System.setErr(new PrintStream(f));
        } catch (FileNotFoundException f) {
            // This should never happen
        }
		Input inputs = new Input(System.in);

		activeScene = new MenuScene(50, 15, new Scene.Listener() {
			@Override
			public void exit() {
				inputs.close();
				Renderer.close();
				System.exit(0);
			}
			@Override
			public void move(Scene s) {
				activeScene = s;
				activeScene.hardRefresh();
			}
		});

		inputs.addListener(new Input.Listener() {
			@Override
			public void keyPressed(char c) {
				activeScene.input(c);
			}
		});
		inputs.open();

		activeScene.hardRefresh();
	}
	public static void wait(int ms) {
		try {
			Thread.sleep(ms);
		}
		catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
}
