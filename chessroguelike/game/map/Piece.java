
package chessroguelike.game.map; 
import chessroguelike.textRenderer.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a general game piece.
 * It stores the list of moves that the piece is allowed to make and a way to draw the piece, including visualizing moves
 * */
public class Piece implements RenderObject, Serializable {
    // initialize conditional booleans and set to false
    public boolean visualizingMove = false, attacking = false;
    // set index of selected move to 0
    public int selectedMove = 0;

	// The symbol used to represent this piece
	public char symbol;

	public Piece(char symbol) {
		this.symbol = symbol;
	}

	// All the moves that pieces have access to
	// It's important that is initialized to the same values every time
	// so that pieces have the same moves when saved files are loaded
	public static ArrayList<Move> moveSet = new ArrayList<Move>();
	// Initialize moveSet to have every move
	static {
		for (String id : Move.pieces.keySet()) {
			for (Move m : Move.pieces.get(id)) moveSet.add(m);
		}

	};
	// The index of moves that this piece has access to out of the moveSet
	// This is necessary instead of just storying the move object themselves for two reasons
	// 1. Piece objects must be serializable, and moves objects are not
	// 2. Storing indices takes less overhead than storing move objects, which is 
	//    important for the performance of a potential engine
    public int[] moves;

	/**
	 * Get a list of pixels representing what this piece should look like.
	 * */
    public ArrayList<Pixel> drawPiece() {
		ArrayList<Pixel> out = new ArrayList<Pixel>();
		out.add(new Pixel(symbol, 0, 0, 5));
		return out;
	}

	/**
	 * Cycle between moves that this piece is allowed to make.
	 * */
	public void cycleMove(int x) {
		if (moves.length == 0) return;
		selectedMove = (moves.length+selectedMove+x)%moves.length;
	}

	/**
	 * Set the moves for this piece to be the given objects.
	 * Assume that all the given moves are in `moveSet`.
	 * */
    public void setMoves(Move[] moves){
		this.moves = new int[moves.length];
		for (int i = 0; i < moves.length; i++) {
			this.moves[i] = moveSet.indexOf(moves[i]);
		}
    }

    /**
    * Selects the closest move to current other that increases in the direction of difference
    * @param difference : A direction vector representing which way to move
	* @param current : The position that the player would end in if they made their currently selected move
    * @param m : The room that this move would be applied in
    */
    public void increase(Position difference, Position current, Room m) {
        // declare and initialize variables used for tracking
        int current_length = Integer.MAX_VALUE; // length in direction of current candidate moves
        int best = 100; // best (min) difference in other direction
        ArrayList<Integer> possible_moves = new ArrayList<Integer>(); // indexes of possible moves
        // loop through possible moves
        for (int i=0; i < moves.length; i++){
            Position move = moveSet.get(moves[i]).simulate(this, m);
            // if the move is not allowed, skip it
            if (!moveSet.get(moves[i]).allowed(this, m)) continue;

            // if the move is father in the direction of difference
            if (((difference.x == 0) || 
				 (Integer.signum(current.x - move.x) == difference.x)) &&
				((difference.y == 0) || 
				 (Integer.signum(current.y - move.y) == difference.y))) {

                // if move is equal to length of other candidate moves, add it to possible_moves
                if (move.squareDist(current) == current_length){
                    possible_moves.add(i);
                } 
                // if move is closer than other candidate moves
                // update current_length and  clear possible_moves 
                // then add the move to possible_moves
                else if (move.squareDist(current) < current_length){
                    current_length = move.squareDist(current);
                    possible_moves.clear();
                    possible_moves.add(i);
                } 
            }
        }
        // if there is/are possible moves
        if (!possible_moves.isEmpty()){
            // loop through them and find the one that is closer in the other direction to the current one
            for (int moveId : possible_moves){
				Position outcome = moveSet.get(moves[moveId]).simulate(this, m);
                if (outcome.squareDist(current) < best){
                    best = outcome.squareDist(current);
                    selectedMove = moveId;
                }
            }
        }
    }

    /**
    * Returns an arraylist of pixels for the selected move
    */
    @Override
    public ArrayList<Pixel> draw() {
        // Start by drawing the piece
        ArrayList<Pixel> p = drawPiece();

        // if in mode of visualizing move and the selectedMove index is valid,
        // add the corresponding pixels to arraylist p
        if (visualizingMove && selectedMove >= 0 && selectedMove < moves.length) {
            p.addAll(getSelectedMove().visualize(attacking));
        }
        // return result
        return p;
    }
	/**
	 * Returns the players currently selected move
	 * */
	public Move getSelectedMove() {
		return moveSet.get(moves[selectedMove]);
	}


	/**
	 * Generate a pawn piece
	 * */
	static Piece pawn() {
		Piece pawn = new Piece('^');
		pawn.setMoves(Move.pawn);
		return pawn;
	}
}
