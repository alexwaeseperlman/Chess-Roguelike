package chessroguelike;
import chessroguelike.textRenderer.*;

/**
 * A {@link RenderObject} that allows a user to enter text
 **/
public class TextBox extends Renderer {
    /**
     * The {@link Listener} object is used to receive inputs from a text box.
     **/
    public static interface Listener {
        /**
         * @param text The content that the user entered
         **/
        public void submitted(String text);
    }
	Rect box;
    Text textDisplay;
	
	int width, height;
	String text = "";

    Listener l;

    /**
     * Construct a {@link TextBox} with given width and height.
     * @param width The menu's width
     * @param height The menu's height
     * @param l A listener object for the constructed menu
     **/
	public TextBox(int width, int height, Listener l) {
		super(width, height);
        this.l = l;
        
		box = new Rect(width-1, height-1);
		box.backgroundChar = ' ';
		textDisplay = new Text(text + '|', width-2);
		this.objects.put(box, new Position(0, 0, 0));
		this.objects.put(textDisplay, new Position(1, 1, 1));
        update();
	}

    /**
     * Update the appearance of the text box to take into
     * account the currently selected inputted text
     **/
    void update() {
		textDisplay.content = text;
    }
    /**
     * Input a character to this textbox while handling delete key presses.
     **/
    public void type(char input) {
		if (input == 13) {
			submit();
			return;
		}
		if (input == 127) {
			text = text.substring(0, text.length()-1);
		}
		else text += input;
		textDisplay.content = text + '|';
    }

    /**
     * Calls the event {@link Listener} for this text box
     **/
    public void submit() {
		l.submitted(text);
    }
}
