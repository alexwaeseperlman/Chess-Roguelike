package chessroguelike.game.map.pieces;

import chessroguelike.game.map.*;
import java.util.ArrayList;
import chessroguelike.textRenderer.*;

/**
 * A simple Piece, mainly made as an example
 * It is used in the default generated game scene
 * The move set is called "pawn", which just allows
 * the piece to move in the four directions
 **/
public class Pawn extends Piece {
    /**
    * Generates a pawn piece at the orginin position
    * @param void
    * @return ArrayList<Pixel> containing one '^' at origin
    */
    @Override
    public ArrayList<Pixel> drawPiece() {
        ArrayList<Pixel> out = new ArrayList<Pixel>();
        out.add(new Pixel('^', 0, 0, 5));
        return out;
    }

    /**
    * Constructor method used to initialize the moves
    * of the class.
    */
    public Pawn() {
        this.moves = Move.pawn;
    }
}