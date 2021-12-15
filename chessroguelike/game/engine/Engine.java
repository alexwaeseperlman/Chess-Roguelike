package chessroguelike.game.engine;

import chessroguelike.game.map.*;
import chessroguelike.textRenderer.Position;
import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;

/**
 * The engine object controls pieces
 * Right now it just gives them random moves, but in
 * the future it can become more intelligent
 **/
public class Engine implements Runnable {
    Room room;
    private Thread t;

    private boolean running = false;

    /**
     * Construct an engine for a given room {@link Room}
     **/
    public Engine(Room room) {
        this.room = room;
    }

    public synchronized boolean calculating() {
        return running;
    }

    synchronized void join() {
        try {
            if (running) t.join();
        }
        catch (InterruptedException e) {
            // do nothing
        }
    }

    /**
     * Start deciding on moves to make
     **/
    public synchronized void start() {
        running = true;
        t = new Thread(this);
        t.start();
        // Wait to finish calculating
        join();
    }

    @Override
    public void run() {
        running = false;
    }

    /**
     * Make the best moves for given pieces
     * */
    public synchronized void makeMoves(Piece player) {
        // Make moves for pieces in the room

        // Just do it randomly
        Random rand = new Random();
        
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        for (Piece p : room.pieces.keySet()) {
            // Don't make moves for the player
            if (p == player) continue;
            pieces.add(p);
        }
        for (Piece p : pieces) {
            // Don't make moves if this piece doesn't have options
            if (p == null || p.moves == null) continue;
            int move = rand.nextInt(p.moves.length);
            if (move >= 0 && move < p.moves.length) p.moves[move].apply(p, room);
        }
    }

}