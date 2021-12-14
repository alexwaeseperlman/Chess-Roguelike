package chessroguelike.inputs;

import java.util.HashSet;
import java.io.*;

/**
 * This class runs as an input listener in the background
 **/
public class Input implements Runnable {
    /**
     * A listener interface that other classes use to react to keypresses
     **/
    public static interface Listener {
        public void keyPressed(char c);
    }
    private HashSet<Listener> listeners = new HashSet<Listener>();
    private final InputStream stream;
    private Thread thread;

    // Stores whether or not the input listener is running
    private boolean running = false;

    /**
     * Constructs a new input object from the given stream
     **/
    public Input(InputStream inp) {
        stream = inp;
        thread = new Thread(this);
    }

    /**
     * Adds the given listener to this object
     **/
    public synchronized void addListener(Listener l) {
        listeners.add(l);
    }

    /**
     * Removes this listener from this object
     **/
    public synchronized void removeListener(Listener l) {
        listeners.remove(l);
    }

    /**
     * Broadcasts the given char to all of the listeners
     **/
    synchronized void broadcast(char c) {
        for (Listener l : listeners) {
            l.keyPressed(c);
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                // Broadcast all the new characters
                while (stream.available() > 0) {
                    broadcast((char)stream.read());
                }
            }
            catch (IOException exception) {
                exception.printStackTrace();
                // Continue waiting for input
                System.out.println(exception);
            }
        }
    }

    public synchronized void open() {
        running = true;
        thread.start();
    }

    public synchronized void close() {
        running = false;
    }
}