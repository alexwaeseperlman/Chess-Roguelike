package chessroguelike.game.map; 
import chessroguelike.textRenderer.*;
import java.util.ArrayList;


abstract public class Piece implements RenderObject {
    public boolean visualizingMove = false;
    public int selectedMove = 0;
    public Move[] allowedMoves;

    public abstract boolean alive();
    abstract public ArrayList<Pixel> drawPiece();

	public void cycleMove(int x) {
		if (allowedMoves.length == 0) return;
		selectedMove = (allowedMoves.length+selectedMove+x)%allowedMoves.length;
	}

    @Override
    public ArrayList<Pixel> draw() {
        ArrayList<Pixel> p = drawPiece();
        if (visualizingMove && selectedMove >= 0 && selectedMove < allowedMoves.length) p.addAll(allowedMoves[selectedMove].visualize());
        return p;
    
    }
}
