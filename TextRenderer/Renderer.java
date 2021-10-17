package TextRenderer;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Renderer implements RenderObject {
	public HashSet<RenderObject> objects = new HashSet<RenderObject>();

	// Defines the viewport in global coordinates
	// For example if the camera is placed at position 0,0
	// pixels with negative coordinates shouldn't be visible
	int width, height, left, top, layer;
	ArrayList<ArrayList<Glyph>> screen, fb;

	public Renderer(int width, int height) {
		this(width, height, 0, 0);
	}

	public Renderer(int width, int height, int x, int y) {
		this.width = width;
		this.height = height;
		this.left = x;
		this.top = y;
		this.layer = 0;

		screen = blankScreen();
		fb = blankScreen();
	}

	public Renderer(int width, int height, int x, int y, int layer) {
		this(width, height, x, y);
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
		for (RenderObject obj : objects) {
			for (Pixel p : obj.draw()) {
				p.x -= left;
				p.y -= top;
				if (p.x < width && p.x >= 0 && p.y < height && p.y >= 0) {
						pixels.add(p);
				}
			}
		}
		// Sort pixels by their layer. This means that pixels with higher layer values will be painted to the buffer later
		Collections.sort(pixels, new Comparator<Pixel>() {
			@Override
			public int compare(Pixel a, Pixel b) {
				if (a.layer < b.layer) return -1;
				else if (b.layer < a.layer) return 1;
				return 0;
			}
		});

		for (Pixel p : pixels) {
			if (!p.c.transparent) fb.get(p.y).set(p.x, p.c);
		}
	}

	@Override
	public Pixel[] draw() {
		// Ensure that pixels are only drawn to the alternate buffer
		ArrayList<Pixel> out = new ArrayList<Pixel>();
		refresh();
		for (int i = 0; i < screen.size(); i++) {
			for (int j = 0; j < screen.get(i).size(); j++) {
				//if (!fb.get(i).get(j).equals(screen.get(i).get(j))) 
					out.add(new Pixel(fb.get(i).get(j), j+left, i+top, layer));
			}
		}

		screen = fb;
		fb = blankScreen();

		return out.toArray(new Pixel[out.size()]);
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

