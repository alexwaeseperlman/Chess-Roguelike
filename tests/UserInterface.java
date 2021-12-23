package tests;
import chessroguelike.textRenderer.*;
import chessroguelike.inputs.*;
import chessroguelike.*;
import chessroguelike.game.*;
import chessroguelike.game.scenes.*;
import chessroguelike.game.scenes.MenuScene;
import java.io.*;

class UserInterfaceTest extends Scene {
	Menu menu;
	TextBox textbox;
	Text lastInput;
	UserInterfaceTest(int width, int height, Scene.Listener l) {
		super(width, height, l);
		lastInput = new Text("You haven't inputted anything yet", 20);
		textbox = new TextBox("Enter your response (press enter to submit).", 60, 3, new TextBox.Listener() {
			@Override
			public void submitted(String text) {
				lastInput.content = "Your last input was: \n" + text;
				objects.remove(textbox);
			}
		});
		menu = new Menu(new String[]{ "Hello world", "Open a text box" }, 30, 10, new Menu.Listener() {
			@Override
			public void onSelect(int selection) {
				switch (selection) {
					case 0: // "Hello world"
						// Do nothing
						break;
					case 1: // "Open a text box"
						objects.put(textbox, new Position(5, 10));
						break;
						
				}
			}
		});
		objects.put(lastInput, new Position(0, 12));
		objects.put(menu, new Position(0, 0));
	}
	public void input(char c) {
		if (objects.containsKey(textbox)) {
			textbox.type(c);
		}
		else if (c == 'k') menu.up();
		else if (c == 'j') menu.down();
		else if (c == 13) menu.select();
		refreshScreen();
	}
}

class UserInterface {
	public static Scene activeScene;
	public static void main(String[] args) {
        try {
            FileOutputStream f = new FileOutputStream("err.txt");
    
            System.setErr(new PrintStream(f));
        } catch (FileNotFoundException f) {
            // This should never happen
        }
		Input inputs = new Input(System.in);

		activeScene = new UserInterfaceTest(70, 25, new Scene.Listener() {
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
