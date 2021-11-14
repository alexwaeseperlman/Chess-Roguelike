package Game.Map;

import chessroguelike.textRenderer.*;
import java.util.ArrayList;

public class Room extends Renderer {
    final int height, width;
    ArrayList<Piece> pieces;

    public Room(int width, int height) {
        super(width, height);
        this.width = width;
        this.height = height;
        objects.add(new Rect(0, 0, width, height, 0));
    }

    public void addPiece(Piece p) {
        if (!pieces.contains(p)) pieces.add(p);
        objects.add(p);
    }

    boolean inRoom(Piece p) {
        return pieces.contains(p) && inRoom(p.x, p.y) && p.alive();
    }

    boolean inRoom(int x, int y) {
        return x < width && y < height;
    }
}