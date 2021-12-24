
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

    int currentTarget[];

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
        currentTarget = moves[selectedMove].getTarget();
    }

    /**
    * Selects the closest move that increases in direction
    * @param direction : 0 for x, 1 for y
    */
    public void increase(int direction, int other){
        // finds a move that moves father in direction
        // while storing an alternative that is equal but different
        int current_length = 100, best = 100;
        ArrayList<Integer> possible_moves = new ArrayList<Integer>();
        int move;
        for (int i=0; i<moves.length; i++){
            move = moves[i].getTarget()[direction];
            if (move > currentTarget[direction]){
                if (move == current_length){
                    possible_moves.add(i);
                } else if (move < current_length){
                    current_length = move;
                    possible_moves.clear();
                    possible_moves.add(i);
                } 
            }
        }
        if (! possible_moves.isEmpty()){
            for (int m : possible_moves){
                if (Math.abs(moves[m].getTarget()[other] - currentTarget[other]) < best){
                    best = Math.abs(moves[m].getTarget()[other] - currentTarget[other]);
                    selectedMove = m;
                }
            }
        }
        
        currentTarget = moves[selectedMove].getTarget();
    }

    /**
    * Selects the closest move that decreases in direction
    * @param direction : 0 for x, 1 for y
    */
    public void decrease(int direction, int other){
        // finds a move that moves father in direction
        // while storing an alternative that is equal but different
        int current_length = -100, best = 100;
        ArrayList<Integer> possible_moves = new ArrayList<Integer>();
        int move;
        for (int i=0; i<moves.length; i++){
            move = moves[i].getTarget()[direction];
            if (move < currentTarget[direction]){
                if (move == current_length){
                    possible_moves.add(i);
                } else if (move > current_length){
                    current_length = move;
                    possible_moves.clear();
                    possible_moves.add(i);
                } 
            }
        }
        if (! possible_moves.isEmpty()){
            for (int m : possible_moves){
                if (Math.abs(moves[m].getTarget()[other] - currentTarget[other]) < best){
                    best = Math.abs(moves[m].getTarget()[other] - currentTarget[other]);
                    selectedMove = m;
                }
            }
        }
        
        currentTarget = moves[selectedMove].getTarget();
    }


    /**
    * Returns an arraylist of pixels for the selected move
    */
    @Override
    public ArrayList<Pixel> draw() {
        // declare a array for storing the pixels
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
