package chessroguelike.game;

import java.io.Serializable;
import java.util.HashMap;

/**
* Class for storing player stats, including levels completed,
* enemies killed, and types & frequency if piece played
*/
public class PlayerStats implements Serializable {
    public int levels_completed;
    public int enemies_killed;
    
    public HashMap<String, Integer> pieces_played;

    /**
    * Constructor function, initializes everything
    */
    public PlayerStats(){
        levels_completed = 0;
        enemies_killed = 0;
        pieces_played = new HashMap<String, Integer>();
    }

    /**
    * Adds a piece to the pieces played
    * @param piece_name : name of piece that the player played as
    */
    public void addPiece(String piece_name){
        pieces_played.put(piece_name, pieces_played.getOrDefault(piece_name, 0) + 1);
    }

    /**
    * Displays the current stats neatly
    * @return current stats as a String
    */
    public String displayStats(){
        // Displays levels beaten and enemies killed
        String str = "\nLevels Beaten: " + levels_completed;
        str += "\nEnemies Killed: " + enemies_killed;

        // if there are pieces the player has played as, display them
        if (! pieces_played.isEmpty()){
            str += "\nPlayed as:";
            for (String piece : pieces_played.keySet()){
                str += "\n" + piece + ": " + pieces_played.get(piece) + " times. ";
            }
        }

        return str;
    }
}
