package chessroguelike.game.engine;

import chessroguelike.game.map.*;
import chessroguelike.textRenderer.Position;
import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;

public class Engine {
    Room room;

    public Engine(Room room) {
        this.room = room;
    }

    /**
     * Make the best moves for the given pieces
     * */
    public void makeMoves(Piece player) {
        // Make moves for the given pieces

        // Just do it randomly
        Random rand = new Random();
        
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        for (Piece p : room.pieces.keySet()) {
            if (p == player) continue;
            pieces.add(p);
        }
        for (Piece p : pieces) {
            if (p == null || p.moves == null) continue;
            if (room == null) continue;
            int move = rand.nextInt(p.moves.length);
            if (move >= 0 && move < p.moves.length) p.moves[move].apply(p, room);
        }

    }

}