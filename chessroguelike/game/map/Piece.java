package chessroguelike.game.map; 
import chessroguelike.textRenderer.*;
import java.util.ArrayList;


/**
 * Represents a general game piece.
 * It stores the list of moves that the piece is allowed to make and a way to draw the piece, including visualizing moves
 * */
abstract public class Piece implements RenderObject {
    public boolean visualizingMove = false;
    public int selectedMove = 0;
    public Move[] moves;

	/**
	 * Test whether or not this piece is alive
	 * */
    public abstract boolean alive();
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

    @Override
    public ArrayList<Pixel> draw() {
        ArrayList<Pixel> p = drawPiece();
        if (visualizingMove && selectedMove >= 0 && selectedMove < moves.length) p.addAll(moves[selectedMove].visualize());
        return p;
    
    }
}
