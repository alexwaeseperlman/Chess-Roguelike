package chessroguelike.game.engine;

import chessroguelike.game.map.*;
import chessroguelike.textRenderer.Position;
import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;

public class Engine implements Runnable {
    Room room;
    private Thread t;

    private boolean running = false;

    public Engine(Room room) {
        this.room = room;
    }

    public synchronized boolean calculating() {
        return running;
    }

    public synchronized void join() {
        try {
            if (running) t.join();
        }
        catch (InterruptedException e) {
            // do nothing
        }
    }

    public synchronized void start() {
        running = true;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        running = false;
    }

    /**
     * Make the best moves for the given pieces
     * */
    public synchronized void makeMoves(Piece player) {
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