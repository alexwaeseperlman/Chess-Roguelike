package chessroguelike.textRenderer;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *	Manages the rendering of pixels to the screen
 * */
public class Renderer implements RenderObject {
	public HashMap<RenderObject, Position> objects = new HashMap<RenderObject, Position>();

	// Defines the viewport in global coordinates
	// For example if the camera is placed at position 0,0
	// pixels with negative coordinates shouldn't be visible
	int width, height, layer;
	ArrayList<ArrayList<Glyph>> screen, fb;

	public Renderer(int width, int height) {
		this.width = width;
		this.height = height;
		this.layer = 0;

		screen = blankScreen();
		fb = blankScreen();
	}

	public Renderer(int width, int height, int layer) {
		this(width, height);
		this.layer = layer;
	}

	ArrayList<ArrayList<Glyph>> blankScreen() {
		ArrayList<ArrayList<Glyph>> out = new ArrayList<ArrayList<Glyph>>();
		
		for (int i = 0; i < height; i++) {
			out.add(new ArrayList<Glyph>());
			for (int j = 0; j < width; j++) {
				out.get(i).add(new Glyph(' '));
			}
		}

		return out;
	}

	public void refresh() {
		ArrayList<Pixel> pixels = new ArrayList<Pixel>();
		for (RenderObject obj : objects.keySet()) {
			for (Pixel p : obj.draw()) {
				p.x += objects.get(obj).x;
				p.y += objects.get(obj).y;
				if (p.x < width && p.x >= 0 && p.y < height && p.y >= 0) {
						pixels.add(p);
				}
			}
		}
		// Sort pixels by their layer. This means that pixels with higher layer values will be painted to the buffer later
		Collections.sort(pixels, new Comparator<Pixel>() {
			@Override
			public int compare(Pixel a, Pixel b) {
                return a.layer - b.layer;
			}
		});

		for (Pixel p : pixels) {
			if (!p.c.transparent) fb.get(p.y).set(p.x, p.c);
		}
	}

	@Override
	public ArrayList<Pixel> draw() {
		ArrayList<Pixel> out = new ArrayList<Pixel>();
		refresh();
		for (int i = 0; i < screen.size(); i++) {
			for (int j = 0; j < screen.get(i).size(); j++) {
                out.add(new Pixel(fb.get(i).get(j), j, i, layer));
			}
		}

		screen = fb;
		fb = blankScreen();

		return out;
	}

	public void hardRefresh() {
		fb = blankScreen();
		refresh();
		if (!inBuffer) toggleBuffer();
		// Clear the screen
		System.out.printf("\033[2J");
		refreshScreen();
	}
	
	public void refreshScreen() {
		refresh();
		if (!inBuffer) toggleBuffer();
		// Find each pixel in the fb that is different and update it
		for (int i = 0; i < height; i++) {
			// Store whether or not the cursor was moved to the last character's position
			// If it was then it doesn't need to be moved again
			boolean moved = false;
			
			for (int j = 0; j < width; j++) {
				Glyph g = fb.get(i).get(j);
				if (!g.equals(screen.get(i).get(j))) {
					if (!moved) {
						moveCursor(j+1, i+1);
						moved = true;
					}
					System.out.print(g.draw());
				}
				else {
					moved = false;
				}
			}
		}
		screen = fb;
		fb = blankScreen();
	}

	// Functions after this point use a lot of codes from
	// here https://www.xfree86.org/current/ctlseqs.html
	public static void moveCursor(int x, int y) {
		System.out.printf("\u001B[%d;%dH", y, x);
	}

	static boolean inBuffer = false;
	static void toggleBuffer() {
		// Switch to buffer
		if (!inBuffer) {
			// Save the cursor position
			System.out.printf("\u001B7");

			// Switch to the alternate screen buffer
			System.out.printf("\u001B[?47h");

			// Hide the cursor
			System.out.printf("\u001B[?25l");
		}
		// Switch out of buffer
		else {
			// Switch back to the standard screen buffer
			System.out.printf("\u001B[?47l");

			// Show the cursor
			System.out.printf("\u001B[?25h");

			// Restore the saved cursor position
			System.out.printf("\u001B8");
		}
		inBuffer = !inBuffer;
	}

	public static void close() {
		if (inBuffer) toggleBuffer();
	}
}
