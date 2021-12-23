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
