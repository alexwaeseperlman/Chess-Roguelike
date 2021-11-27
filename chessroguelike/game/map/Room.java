package chessroguelike.game.map;

import chessroguelike.textRenderer.*;
import java.util.HashMap;

public class Room extends Renderer {
    final int height, width;
    HashMap<Piece, Position> pieces;

    public Room(int width, int height) {
        super(width+1, height+1);
        this.width = width;
        this.height = height;
        objects.put(new Rect(width, height, 0), new Position(0, 0));
    }

    public void updatePiece(Piece p, Position pos) {
        pieces.put(p, pos);
        objects.put(p, pos);
    }

    boolean inRoom(Piece p) {
        return pieces.containsKey(p) && inRoom(pieces.get(p)) && p.alive();
    }

	boolean inRoom(Position p) {
		return inRoom(p.x, p.y);
	}
    boolean inRoom(int x, int y) {
        return x < width && y < height;
    }
}
