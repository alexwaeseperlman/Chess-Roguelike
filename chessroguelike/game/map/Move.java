package chessroguelike.game.map;
import chessroguelike.textRenderer.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
/**
 * A move is an object that can apply movement to a {@link:Piece}
 * Moves can only affect one piece at a time
 **/
// Moves only affect the piece that they are applied to.
// They can be reverted
// apply() moves the piece according to the move, and revert() moves the piece back
// This means that the engine doesn't need to store unnecessary piece objects in the search tree
// Each vertex simply stores the index of the piece, and the index of its move 

// visualize() returns a list of Pixel objects representing what this move would look like
public interface Move {
    /**
     * Test whether or not it's possible to apply this move
     * @param p The {@link Piece} that the move should be applied to 
     * @param m The {@link Room} that the piece is in
     **/
    boolean allowed(Piece p, Room m);
    boolean wouldAttack(Piece p, Room m);
    // Returns a piece that was taken (possibly null)
    Piece apply(Piece p, Room m);
	Move inverse();

    /**
     * Generate a list of pixels that visualize this move
     * @param attack Sets whether or not the visualized move is an attack.
                     This usually just means to use a different color.
     **/
    ArrayList<Pixel> visualize(boolean attack);

    /**
     * Generate a move based on the difference in position it creates
     * All the normal chess moves can be implemented with this
     * @param The applied difference on the x-axis
     * @param The applied difference on the y-axis
     **/
    public static Move fromDifference(int x, int y) {
        Move out = new Move() {
            @Override
            public boolean allowed(Piece p, Room m) {
				if (!m.pieces.containsKey(p)) return false;
				Position pos = m.pieces.get(p);
                return m.inRoom(pos.x + x, pos.y + y);
            }
            @Override
            public boolean wouldAttack(Piece p, Room m) {
				if (!m.pieces.containsKey(p)) return false;
                return m.filledPosition(m.pieces.get(p).add(x, y));

            }
            @Override
            public Piece apply(Piece p, Room m) {
                if (allowed(p, m)) {
					return m.updatePiece(p, m.pieces.get(p).add(x, y));
                }
                return null;
            }

			@Override 
            public Move inverse() {
				return Move.fromDifference(-x, -y);
			}

            @Override
            public ArrayList<Pixel> visualize(boolean attack) {
                Line l = new Line(x, y, 1);
                if (attack) l.fg = new Color(255, 0, 0);
                return l.draw();
            }
        };

        return out;
    }

    public static Move[] pawn = {
        Move.fromDifference(1, 0),
        Move.fromDifference(0, 1),
        Move.fromDifference(-1, 0),
        Move.fromDifference(0, -1)
    };

    public static Move[] rook = {
      Move.fromDifference(0, 1),
      Move.fromDifference(0, 2),
      Move.fromDifference(0, 3),
      Move.fromDifference(0, 4),
      Move.fromDifference(0, 5),
      Move.fromDifference(0, 6),
      Move.fromDifference(0, 7),
      Move.fromDifference(0, -1),
      Move.fromDifference(0, -2),
      Move.fromDifference(0, -3),
      Move.fromDifference(0, -4),
      Move.fromDifference(0, -5),
      Move.fromDifference(0, -6),
      Move.fromDifference(0, -7),

      Move.fromDifference(1, 0),
      Move.fromDifference(2, 0),
      Move.fromDifference(3, 0),
      Move.fromDifference(4, 0),
      Move.fromDifference(5, 0),
      Move.fromDifference(6, 0),
      Move.fromDifference(7, 0),
      Move.fromDifference(-1, 0),
      Move.fromDifference(-2, 0),
      Move.fromDifference(-3, 0),
      Move.fromDifference(-4, 0),
      Move.fromDifference(-5, 0),
      Move.fromDifference(-6, 0),
      Move.fromDifference(-7, 0)
    };

    public static Move[] knight = {
        Move.fromDifference(-1, 2),
        Move.fromDifference(1, 2),
        Move.fromDifference(2, 1),
        Move.fromDifference(2, -1),
        Move.fromDifference(1, -2),
        Move.fromDifference(-1, -2),
        Move.fromDifference(-2, -1),
        Move.fromDifference(-2, 1)
    };



    /*
    Hashmap for storing referecing the names with their moves
    */
    public static HashMap<String, Move[]> pieces = new HashMap<String, Move[]>(){
    {
        put("Knight", knight);
        put("Pawn", pawn);
        put("Rook", rook);
    }
    };
    
    // get an array of keys
    public static Object[] piece_names = pieces.keySet().toArray();

    Random generator = new Random();

    /*
    Method for getting a valid name for pieces (to be used as index for getting moves within the GameScene)
    */
    public static String randomPiece(){
        System.out.println(piece_names[generator.nextInt(piece_names.length)]);
        return (String) piece_names[generator.nextInt(piece_names.length)];
    }
}
