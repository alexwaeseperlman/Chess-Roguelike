package chessroguelike.game.map;

import chessroguelike.textRenderer.*;
import chessroguelike.game.Scene;
import java.util.ArrayList;

public class Map extends Scene {
    ArrayList<Room> rooms;
    final int width, height;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
    }
    boolean inMap(int x, int y) {
        return true;
    }
}