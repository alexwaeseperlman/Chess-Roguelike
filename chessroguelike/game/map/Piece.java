
package chessroguelike.game.map; 
import chessroguelike.textRenderer.*;
import java.util.ArrayList;


/**
 * Represents a general game piece.
 * It stores the list of moves that the piece is allowed to make and a way to draw the piece, including visualizing moves
 * */
abstract public class Piece implements RenderObject {
    // initialize conditional booleans and set to false
    public boolean visualizingMove = false, attacking = false;
    // set index of selected move to 0
    public int selectedMove = 0;
    // declare an array of moves
    public Move[] moves;

	/**
	 * Get a list of pixels representing what this piece should look like.
	 * */
    abstract public ArrayList<Pixel> drawPiece();

	/**
	 * Cycle between moves that this piece is allowed to make.
	 * */
	public void cycleMove(int x) {
		if (moves.length == 0) return;
		selectedMove = (moves.length+selectedMove+x)%moves.length;
	}

    public void setMoves(Move[] moves){
        this.moves = moves;
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
        for (int i=0; i<moves.length; i++){
            Position move = moves[i].simulate(this, m);
            // if the move is not allowed, skip it
            if (!moves[i].allowed(this, m)) continue;

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
				Position outcome = moves[moveId].simulate(this, m);
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
            p.addAll(moves[selectedMove].visualize(attacking));
        }
        // return result
        return p;
    }
}
