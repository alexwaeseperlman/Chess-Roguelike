package chessroguelike.game.map;

import chessroguelike.textRenderer.*;
import chessroguelike.game.map.pieces.*;
import java.util.HashMap;
import java.util.Random;

/**
 * A class to render a store game state
 **/
public class Room extends Renderer {
    final int height, width;
	// Stores the location of every piece int the room
    public HashMap<Piece, Position> pieces;

    public Room(int width, int height) {
		// Initialize the renderer to be a bit larger than the room so we render its borders
        super(width+2, height+2);

        this.width = width;
        this.height = height;

		pieces = new HashMap<Piece, Position>();

        objects.put(new Rect(width, height), new Position(0, 0));
    }

	/**
	 * Update the position of a piece. This handles updating the render object and the piece position map
     * @return Was a piece taken
	 * */
    public Piece updatePiece(Piece p, Position pos) {
        Piece target = atPosition(pos);
        if (!inRoom(pos)) return null;
		Position drawPos = new Position(pos.x, pos.y, 2);
        pieces.put(p, pos);
        objects.put(p, drawPos);
		refresh();
        if (target != null) {
            killPiece(target);
            return target;
        }
        return null;
    }

    void killPiece(Piece p) {
        // TODO: Handle player death
        pieces.remove(p);
        objects.remove(p);
    }

	/**
	 *	Test if a piece exists in a room, and if its position is correct
	 * */
    boolean inRoom(Piece p) {
        return pieces.containsKey(p) && inRoom(pieces.get(p));
    }

    /**
     * Test if a position is filled by another piece
     * */
    Piece atPosition(Position p) {
        for (Piece x : pieces.keySet()) {
            if (pieces.get(x).equals(p)) return x;
        }
        return null;
    }

    /**
     * Test if a position is filled by another piece
     * */
    boolean filledPosition(Position p) {
        return atPosition(p) != null;
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

    /**
     * Randomly generate a room based on parameters
     * @param difficulty Currently represents the number of
     *                   pieces that are in the room
     * @param player The {@link Piece} object that represents 
                     the player
     **/
    public static Room generate(int w, int h, int difficulty, Piece player, Position playerPos) {
        Room room = new Room(w, h);
        room.updatePiece(player, playerPos);


        Random rand = new Random();
        for (int i = 0; i < difficulty; i++) {
            Position p;
            int guesses = 0;
            do {
                p = new Position(rand.nextInt(w), rand.nextInt(h));
                guesses++;
            } while ((room.filledPosition(p) || !room.inRoom(p)) && guesses < 100 /* give up */);
            if (!room.filledPosition(p)) room.updatePiece(new Pawn(), p);
        }

        return room;
    }
}
