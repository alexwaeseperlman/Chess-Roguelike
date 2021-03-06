package chessroguelike.textRenderer;

import java.util.HashMap;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *	Manages the rendering of pixels to the screen
 **/
public class Renderer implements RenderObject {
	public HashMap<RenderObject, Position> objects = new HashMap<RenderObject, Position>();

	// Defines the viewport in global coordinates
	// For example if the camera is placed at position 0,0
	// pixels with negative coordinates shouldn't be visible
	protected int width, height, layer;

    // The renderer object has two buffers (terminology from opengl):
    // The screen and fb (framebuffer)
    // The screen buffer stores what information is currently on the screen
    // The frame buffer stores pixels as they are rendered, before they go to the screen
    // This is important because it lets us avoid writing characters unnecessarily
    // which means that the game can actually be somewhat playable with an online terminal like repl.it
	// This is one of the places where we used an array of objects
	ArrayList<ArrayList<Glyph>> screen, fb;

    /**
     * Construct a renderer with the given width/height
     **/
	public Renderer(int width, int height) {
		this.width = width;
		this.height = height;

		screen = blankScreen();
		fb = blankScreen();
	}


    /**
     * Call {@link #blankScreen()} with c=' '
     **/
	ArrayList<ArrayList<Glyph>> blankScreen() {
        return blankScreen(' ');
    }
    /**
     * Outputs a blank 2d char array filled with `c`
     */
	ArrayList<ArrayList<Glyph>> blankScreen(char c) {
		ArrayList<ArrayList<Glyph>> out = new ArrayList<ArrayList<Glyph>>();
		
		for (int i = 0; i < height; i++) {
			out.add(new ArrayList<Glyph>());
			for (int j = 0; j < width; j++) {
				out.get(i).add(new Glyph(c));
			}
		}

		return out;
	}

    /**
     * Update the framebuffer for any changes to 
     * {@linkRenderObject} objects in this renderer
     **/
	public void refresh() {
		ArrayList<Pixel> pixels = new ArrayList<Pixel>();
        // Draw each object
		for (RenderObject obj : objects.keySet()) {
			for (Pixel p : obj.draw()) {
                // Shift pixels from object space into renderer space
				p.pos.x += objects.get(obj).x;
				p.pos.y += objects.get(obj).y;
				p.pos.layer += objects.get(obj).layer;
                // If the new pixel is in the screen of this renderer add it to a list to draw it
				if (p.pos.x < width && p.pos.x >= 0 && p.pos.y < height && p.pos.y >= 0) {
						pixels.add(p);
				}
			}
		}
		// Sort pixels by their layer. This means that pixels with higher layer values will be painted to the buffer later
		// This is one of the places where we use sorting
		Collections.sort(pixels, new Comparator<Pixel>() {
			@Override
			public int compare(Pixel a, Pixel b) {
                return a.pos.layer - b.pos.layer;
			}
		});

        // Draw pixels onto the framebuffer in order of their layer
		for (Pixel p : pixels) {
			if (!p.c.transparent) fb.get(p.pos.y).set(p.pos.x, p.c);
		}
	}

    /**
     * Refresh the framebuffer and then return the pixels
     * This allows {@link Renderer} objects to be used as 
     * {@link RenderObject}, which means that more complex
     * things can be drawn.
     **/
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

    /**
     * Clear the screen buffer and then render to it.
     * Calls {@link #refreshScreen()} after clearing
     **/
	public void hardRefresh() {
		// Clear the screen
		System.out.printf("\u001B[2J");

        // Use an uncommon character for the screen buffer so everything gets drawn
		screen = blankScreen('~');
        fb = blankScreen(' ');
		refreshScreen();
	}
	
    /**
     * Update the framebuffer and then print all the new changes to the screen
     **/
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
                // Only print characters if the fb differs from the screen at this position
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
    /**
     * Toggle between alternate and main buffers
     **/
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

