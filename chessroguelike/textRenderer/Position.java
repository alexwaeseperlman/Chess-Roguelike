package chessroguelike.textRenderer;
/**
 * Simple data class to store an integer position in 2d space
 * */
public class Position {
	public int x, y;
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position add(Position rhs) {
		Position out = new Position(x+rhs.x, y+rhs.y);
		return out;
	}

	public Position add(int x, int y) {
		return add(new Position(x, y));
	}
}
