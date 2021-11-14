package chessroguelike;
import chessroguelike.textRenderer.*;

public class Menu extends Renderer {
    public static interface Listener {
        public void onSelect(int selection);
    }
	Rect[] buttons;
    Text[] texts;
	
	int buttonWidth, buttonHeight;
    int x, y;

    int selection = 0;

    Color selected = new Color(128, 128, 128);
    Listener l;

	public Menu(String options[], int width, int height, int x, int y, Listener l) {
		super(width, height, 0, 0, x, y);
        this.l = l;
        buttons = new Rect[options.length];
        texts = new Text[options.length];

		buttonWidth = width * 4 / 5;
		buttonHeight = 1;

        for (int i = 0; i < options.length; i++) {
            buttons[i] = new Rect(0, i*height/options.length, buttonWidth, buttonHeight, 0);
            texts[i] = new Text(options[i], 3, i*height/options.length+1, buttonWidth, 1);

            this.objects.add(buttons[i]);
            this.objects.add(texts[i]);
        }
        update();
	}
    void update() {
        for (int i = 0; i < buttons.length; i++) {
            if (i == selection) {
                buttons[i].bg = selected;
                texts[i].bg = selected;
            }
            else {
                buttons[i].bg = Color.BLACK;
                texts[i].bg = Color.BLACK;
            }
        }
    }
    public void up() {
        selection = (buttons.length + selection - 1) % buttons.length;
        update();
    }

    public void down() {
        selection = (buttons.length + selection + 1) % buttons.length;
        update();
    }

    public void select() {
        l.onSelect(selection);
    }
}
