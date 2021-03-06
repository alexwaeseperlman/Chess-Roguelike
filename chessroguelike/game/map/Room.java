package chessroguelike.game.map;

import chessroguelike.textRenderer.*;

import java.io.*;
import java.util.HashMap;
import java.util.Random;

/**
 * A class to render a store game state
 **/
public class Room implements Serializable {
    int height, width;
	// Stores the location of every piece int the room
    public HashMap<Piece, Position> pieces;

	public transient Renderer renderer;

    public Room(int width, int height) {
        this.width = width;
        this.height = height;
		setup();
    }

	/**
	 * Set up the renderer for this room
	 * */
	void setup() {
		// Initialize the renderer to be a bit larger than the room so we render its borders
		renderer = new Renderer(width+2, height+2);
        // declare hashmap used to store pieces and their positions
		pieces = new HashMap<Piece, Position>();
        renderer.objects.put(new Rect(width, height), new Position(0, 0));
	}

	/**
	 * Update the position of a piece. This handles updating the render object and the piece position map
     * @return Null if no piece is taken, the taken Piece otherwise
	 * */
    public Piece updatePiece(Piece p, Position pos) {
        // gets target (null if there is no piece there)
        Piece target = atPosition(pos);
        // if the target position is not in the room, return null
        if (!inRoom(pos)) return null;
        // update the piece's position in the room and the renderer
        Position drawPos = new Position(pos.x, pos.y, 2);
        pieces.put(p, pos);
        renderer.objects.put(p, drawPos);

        // refresh the screen
        renderer.refresh();
        // if a piece was taken, kill that piece and return it
        if (target != null) {
            killPiece(target);
            return target;
        }
        // if no piece was taken, return null
        return null;
    }

    /**
    * Removes given piece from the room
    * @param p : piece to be killed
    */
    void killPiece(Piece p) {
        // TODO: Handle player death
        pieces.remove(p);
        renderer.objects.remove(p);
    }

	/**
	 *	Test if a piece exists in a room, and if its position is correct
     * @param piece : piece to be checked
	 * */
    boolean inRoom(Piece piece) {
        return pieces.containsKey(piece) && inRoom(pieces.get(piece));
    }

    /**
     * Test if a position is filled by another piece
     * @param pos : position object to be tested
     * @return null if position is empty, a Piece that occupies the position if not empty
     * */
    Piece atPosition(Position pos) {
        for (Piece x : pieces.keySet()) {
            if (pieces.get(x).equals(pos)) return x;
        }
        return null;
    }

    /**
     * Test if a position is filled by another piece
     * @param pos : position to be tested
     * */
    boolean filledPosition(Position pos) {
        return atPosition(pos) != null;
    }

	/**
	 * Test if a position is in the room
     * @param pos : Position to be tested
	 * */
	boolean inRoom(Position pos) {
		return inRoom(pos.x, pos.y);
	}
	/**
	 * Test if a position is in the room based its x and y coordinates
     * @param x : x coordinate of position
     * @param y : y coordinate of position
	 * */
    boolean inRoom(int x, int y) {
        return x>0 && y> 0 && x < width && y < height;
    }

    /**
     * Randomly generate a room based on parameters
     * @param w : width of the room
     * @param h : height of the room
     * @param difficulty: the number of pieces that are in the room
     * @param player The {@link Piece} object that represents 
                     the player
     * @param playerPos : Position of the player
     * @return newly generated {@link Room} object with enemy pieces
     **/
    public static Room generate(int w, int h, int difficulty, Piece player, Position playerPos) {
        // creates new room and places the player
        Room room = new Room(w, h);
        room.updatePiece(player, playerPos);

        // creates random generator
        Random rand = new Random();
        // create difficulty number of enemies
        for (int i = 0; i < difficulty; i++) {
            Position p;
            int guesses = 0;
            // do-while loop to ensure that the enemy piece is generated
            // at a valid position
            do {
                p = new Position(rand.nextInt(w), rand.nextInt(h));
                guesses++;
            } while ((room.filledPosition(p) || !room.inRoom(p)) && guesses < 100 /* give up */);
            if (!room.filledPosition(p)) room.updatePiece(Piece.pawn(), p);
        }

        // return the generated room
        return room;
    }

	 
	/**
	 * Custom method to deserialize room objects. This gets called when {@link SavedGame} objects are being loaded
	 * @param inp : The input stream for the object being loaded
	 * */
    private void readObject(ObjectInputStream inp) throws ClassNotFoundException, IOException {       
		// Read width and height
		width = inp.readInt();
        height = inp.readInt();
		// Read all the pieces
        HashMap<Piece, Position> loadedPieces = (HashMap<Piece, Position>)inp.readObject();
		setup();
		// Put each piece inside the renderer
		for (Piece p : loadedPieces.keySet()) {
			pieces.put(p, loadedPieces.get(p));
			renderer.objects.put(p, loadedPieces.get(p));
		}
    }
 
	/**
	 * Custom method for serializing room objects. This is called when {@link SavedGame} objects are being saved
	 * @param out : The output stream for the object being saved
	 * */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(width);
        out.writeInt(height);
        out.writeObject(pieces);
    }
}
