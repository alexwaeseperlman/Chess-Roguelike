package chessroguelike.inputs;

import java.util.HashSet;
import java.io.*;

public class Input implements Runnable {
    public static interface Listener {
        public void keyPressed(char c);
    }
    private HashSet<Listener> listeners = new HashSet<Listener>();
    private final InputStream stream;
    private Thread thread;

    private boolean running = false;

    public Input(InputStream inp) {
        stream = inp;
        thread = new Thread(this);
    }

    public synchronized void addListener(Listener l) {
        listeners.add(l);
    }
    public synchronized void removeListener(Listener l) {
        listeners.remove(l);
    }

    synchronized void broadcast(char c) {
        for (Listener l : listeners) {
            l.keyPressed(c);
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
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