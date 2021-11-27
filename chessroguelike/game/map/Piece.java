package chessroguelike.game.map; 
import chessroguelike.textRenderer.*;
import java.util.ArrayList;


abstract class Piece implements RenderObject {
    // Global positions
    boolean visualizingMove = false;
    int selectedMove = 0;
    Move[] allowedMoves;

    public abstract boolean alive();
    abstract ArrayList<Pixel> drawPiece();

    @Override
    public ArrayList<Pixel> draw() {
        ArrayList<Pixel> p = drawPiece();
        if (visualizingMove) p.addAll(allowedMoves[selectedMove].visualize());
        return p;
    
    }
}
