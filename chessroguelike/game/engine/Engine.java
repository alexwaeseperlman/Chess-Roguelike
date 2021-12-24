package chessroguelike.game.engine;

import chessroguelike.game.map.*;
import chessroguelike.textRenderer.Position;
import java.util.HashMap;
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

        // variable declaration
        double closest_dis; // straight line distance between move and player
        Move selected = Move.fromDifference(0, 0); // blank move
        
        ArrayList<Piece> pieces = new ArrayList<Piece>(); // pieces at hand
        for (Piece p : room.pieces.keySet()) {
            // Don't make moves for the player
            if (p == player){
                continue;
            }
            pieces.add(p);
        }

        // for every piece
        for (Piece p : pieces) {
            // Don't make moves if this piece doesn't have options
            if (p == null || p.moves == null) continue;
            // set closest distance to a large number
            closest_dis = 1000;
            for (Move move : p.moves){
                // if the piece can kill the player, do so, then break out of the method
                if (move.wouldAttack(p, player, room)){
                    move.apply(p, room);
                    return;
                } 
                // if the piece if going to attack another enemy piece, don't
                else if (move.wouldAttack(p, room)) continue;
				Position outcome = move.simulate(p, room);
				int distance = outcome.squareDist(room.pieces.get(player));
                // if this move is closer to the player than the previous ones, select it
                if (distance < closest_dis){
                    closest_dis = distance;
                    selected = move;
                }
            }
            
            // apply the selected move
            selected.apply(p, room);
        }
    }

}
