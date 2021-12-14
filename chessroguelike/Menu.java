package chessroguelike;
import chessroguelike.textRenderer.*;

/**
 * This is a render object that generates an interactive
 * menu, controlled by the keyboard
 **/
public class Menu extends Renderer {
    /**
     * The onSelect method gets called every time the user selects an item.
     * Note that the listener is only called if the {@link:#select()} method is called
     **/
    public static interface Listener {
        /**
         * @param selection The index of the selected button in the options[] array
         **/
        public void onSelect(int selection);
    }
    // Rect objects that are drawn behind buttons
	Rect[] buttons;
    // Text objects to draw inside the buttons
    Text[] texts;
	
	int buttonWidth, buttonHeight;
    int x, y;

    int selection = 0;

    Color selected = new Color(128, 128, 128);
    Listener l;

    /**
     * Construct a menu with a button corresponding
     * to each entry in `options[]`
     * @param options A list of button titles
     * @param width The menu's width
     * @param height The menu's height
     * @param l A listener object for the constructed menu
     **/
	public Menu(String options[], int width, int height, Listener l) {
		super(width, height);
        this.l = l;
        
        // Construct an array to store all the render objects that the menu uses
        buttons = new Rect[options.length];
        texts = new Text[options.length];

        // The button width is the menu width-1 because they have borders which take up one unitc:w
		buttonWidth = width-1;
        // They also have a default height of 1
		buttonHeight = 1;

        // Construct each button
        for (int i = 0; i < options.length; i++) {
            // A button consists of a rect with a text object on top of it
            buttons[i] = new Rect(buttonWidth, buttonHeight, 0);
            texts[i] = new Text(options[i], buttonWidth, 1);

            // Text is placed in a position offset to the right 
            // by 3 units from the edge of the rectangle.
            // This was decided on through trial and error because it looks nice
            this.objects.put(buttons[i], new Position(0, i*height/options.length));
            this.objects.put(texts[i], new Position(3, i*height/options.length+1));
        }
        update();
	}

    /**
     * Update the appearance of the menu to take into
     * account the currently selected button
     **/
    void update() {
        for (int i = 0; i < buttons.length; i++) {
            // The currently selected button should be colored
            // to show that it is selected
            if (i == selection) {
                buttons[i].bg = selected;
                texts[i].bg = selected;
            }
            // All other buttons default to the color black
            // TODO: It might be necessary in the future to make this
            // color into a property so that menus can be configured
            // when necessary. 
            // We haven't needed that functionality yet.
            else {
                buttons[i].bg = Color.BLACK;
                texts[i].bg = Color.BLACK;
            }
        }
    }
    /**
     * Move the currently selected button update
     **/
    public void up() {
        selection = (buttons.length + selection - 1) % buttons.length;
        update();
    }

    /**
     * Move the currently selected button down
     **/
    public void down() {
        selection = (buttons.length + selection + 1) % buttons.length;
        update();
    }

    /**
     * Essentially 'clicks' the currently selected button
     **/
    public void select() {
        l.onSelect(selection);
    }
}
