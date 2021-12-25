package chessroguelike.textRenderer;

import java.io.Serializable;

/**
 * Simple data class to store an integer position in 2d space
 * */
public class Position implements Serializable {
    // x and y of this position
	public int x, y, layer = 0;
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Position(int x, int y, int layer) {
		this(x, y);
		this.layer = layer;
	}

    /**
     * Perform vector addition
     * @param rhs The vector on the right hand side of the addition expression
     *            (think this+rhs)
     **/
	public Position add(Position rhs) {
		Position out = new Position(x+rhs.x, y+rhs.y);
		return out;
	}

    /**
     * Construct a new position and perform
     * vector addition with int
     **/
	public Position add(int x, int y) {
		return add(new Position(x, y));
	}

	/**
	 * Get the additive inverse of this position
	 * */
	public Position negative() {
		return new Position(-x, -y);
	}

	/**
	 * Get the squared distance between this position and another
	 * */
	public int squareDist(Position rhs) {
		return (x-rhs.x)*(x-rhs.x) + (y-rhs.y)*(y-rhs.y);
	}

    /**
     * Check if two positions are exactly equal
     **/
    public boolean equals(Position p) {
        return x == p.x && y == p.y;
    }
}
