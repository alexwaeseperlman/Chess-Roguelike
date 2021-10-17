import TextRenderer.*;

public class MainMenu extends Renderer {
	Rect playButton, savedButton, instructionsButton;
	Text playText, savedText, instructionsText;
	
	int buttonWidth, buttonHeight;
	int left;

	public MainMenu(int width, int height) {
		super(width, height);

		buttonWidth = width * 4 / 5;
		buttonHeight = height / 8;
		left  = width / 10;

		playButton = new Rect(left, height / 3, buttonWidth, buttonHeight, 0);
		playText = new Text("Play", left+1, height/3, buttonWidth, 1);
		this.objects.add(playButton);
	}

}
