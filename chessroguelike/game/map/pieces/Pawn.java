package chessroguelike.game.map.pieces;

import chessroguelike.game.map.*;
import java.util.ArrayList;
import chessroguelike.textRenderer.*;

/**
 * A simple Piece, mainly made as an example
 * It is used in the default generated game scene
 * The move set is called "pawn", which just allows
 * 
 **/
public class Pawn extends Piece {
    @Override
    public ArrayList<Pixel> drawPiece() {
        ArrayList<Pixel> out = new ArrayList<Pixel>();
        out.add(new Pixel('^', 0, 0, 5));
        return out;
    }

    public Pawn() {
        this.moves = Move.pawn;
    }
}