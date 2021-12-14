package chessroguelike.textRenderer;
/**
 * Simple data class to store an integer position in 2d space
 * */
public class Position {
    // x and y of this position
	public int x, y;
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
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
     * Check if two positions are exactly equal
     **/
    public boolean equals(Position p) {
        return x == p.x && y == p.y;
    }
}
