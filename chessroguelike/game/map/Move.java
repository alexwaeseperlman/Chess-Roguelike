package chessroguelike.game.map;
import chessroguelike.textRenderer.*;
import java.util.ArrayList;

// Moves only affect the piece that they are applied to.
// They can be reverted
// apply() moves the piece according to the move, and revert() moves the piece back
// This means that the engine doesn't need to store unnecessary piece objects in the search tree
// Each vertex simply stores the index of the piece, and the index of its move 

// visualize() returns a list of Pixel objects representing what this move would look like
public interface Move {
    boolean allowed(Piece p, Room m);
    void apply(Piece p, Room m);
    void revert(Piece p, Room m);
    ArrayList<Pixel> visualize(Piece p);

    public static Move fromDifference(int x, int y) {
        Move out = new Move() {
            @Override
            public boolean allowed(Piece p, Room m) {
                return m.inRoom(p.x + x, p.y + y);
            }
            @Override
            public void apply(Piece p, Room m) {
                if (allowed(p, m)) {
                    p.x += x;
                    p.y += y;
                }
            }
            @Override
            public void revert(Piece p, Room m) {
                if (m.inRoom(p.x - x, p.y - y)) {
                    p.x -= x;
                    p.y -= y;
                }
            }

            @Override
            public ArrayList<Pixel> visualize(Piece p) {
                Line l = new Line(p.x, p.y, p.x+x, p.y+y, 1);
                return l.draw();
            }
        };

        return out;
    }

    public static Move[] pawn = {
        Move.fromDifference(1, 0),
        Move.fromDifference(0, 1),
        Move.fromDifference(-1, 0),
        Move.fromDifference(0, -1)
    };

    public static Move[] knight = {
        Move.fromDifference(-1, 2),
        Move.fromDifference(1, 2),
        Move.fromDifference(2, 1),
        Move.fromDifference(2, -1),
        Move.fromDifference(-1, -2),
        Move.fromDifference(1, -2),
        Move.fromDifference(-2, 1),
        Move.fromDifference(-2, -1)
    };
}