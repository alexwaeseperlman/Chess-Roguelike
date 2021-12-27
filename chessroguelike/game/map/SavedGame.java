package chessroguelike.game.map;

import java.io.*;
import chessroguelike.game.scenes.GameScene;
import chessroguelike.game.PlayerStats;
import java.time.*;
import java.util.Date;

/**
 * Saved game objects hold enough information to load back to a previous state. They are serializable which means that they can be saved and loaded
 * */
public class SavedGame implements Serializable {
	public PlayerStats stats;
	public Room room;
	public Piece player;
	public String piece_name;
	public LocalDate lastModified = LocalDate.now();
	public String name;

	/**
	 * Apply the saved data to a game scene
	 * */
	public void apply(GameScene gs) {
		gs.stats = stats;
		gs.room = room;
		gs.player = player;
		gs.piece_name = piece_name;
	}
}
