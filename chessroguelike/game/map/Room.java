package chessroguelike.game.map;

import chessroguelike.textRenderer.*;
import java.util.HashMap;

public class Room extends Renderer {
    final int height, width;
	// Stores the location of every piece int the room
    HashMap<Piece, Position> pieces;

    public Room(int width, int height) {
		// Initialize the renderer to be a bit larger than the room so we render its borders
        super(width+2, height+2);

        this.width = width;
        this.height = height;

		pieces = new HashMap<Piece, Position>();

        objects.put(new Rect(width, height, 0), new Position(0, 0));
    }

	/**
	 * Update the position of a piece. This handles updating the render object and the piece position map
	 * */
    public void updatePiece(Piece p, Position pos) {
        pieces.put(p, pos);
        objects.put(p, pos);
		refresh();
    }

	/**
	 *	Test if a piece exists in a room, and if its position is correct
	 * */
    boolean inRoom(Piece p) {
        return pieces.containsKey(p) && inRoom(pieces.get(p)) && p.alive();
    }

	/**
	 * Test if a position is in the room
	 * */
	boolean inRoom(Position p) {
		return inRoom(p.x, p.y);
	}
	/**
	 * Test if a position is in the room based its x and y coordinates
	 * */
    boolean inRoom(int x, int y) {
        return x>0 && y> 0 && x < width && y < height;
    }
}
