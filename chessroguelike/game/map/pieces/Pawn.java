package chessroguelike.game.map.pieces;

import chessroguelike.game.map.*;
import java.util.ArrayList;
import chessroguelike.textRenderer.*;

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