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

		/**
		 * Called if the user presses escape
		 * */
		public void cancelled();
    }
	Rect box;
    Text textDisplay;
	
	int width, height;
	String text = "";

	// Text that is drawn on top of the text box
	public Text title;

    Listener l;

    /**
     * Construct a {@link TextBox} with given width and height.
	 * @param title The text displayed on top of the text box
     * @param width The text box's width
     * @param height The text box's height
     * @param l A listener object for the constructed {@link TextBox}
     **/
	public TextBox(String title, int width, int height, Listener l) {
		super(width, height);
        this.l = l;
        
		box = new Rect(width-1, height-1);
		box.backgroundChar = ' ';
		textDisplay = new Text(text + '|', width-2);

		this.title = new Text(title, width);

		this.objects.put(box, new Position(0, 0, 0));
		this.objects.put(textDisplay, new Position(1, 1, 1));
		this.objects.put(this.title, new Position(2, 0, 1));
        update();
	}

    /**
     * Update the appearance of the text box to take into
     * account the currently selected inputted text
     **/
    void update() {
		textDisplay.content = text+"|";
    }
    /**
     * Input a character to this textbox while handling delete key presses.
     **/
    public void type(char input) {
		// Handle enter
		if (input == 13) {
			submit();
			return;
		}
		// Handle delete
		if (input == 127) {
			// Don't delete on an empty string
			if (text.length() > 0) text = text.substring(0, text.length()-1);
		}
		else text += input;
		update();
    }

    /**
     * Calls the event {@link Listener} for this text box
     **/
    public void submit() {
		l.submitted(text);
		clear();
    }

	/**
	 * Clear the contents of the textbox.
	 * */
	public void clear() {
		text = "";
		update();
	}

	/**
	 * Called if the user presses escape
	 * */
	public void cancel() {
		l.cancelled();
		clear();
	}
}
