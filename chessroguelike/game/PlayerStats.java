package chessroguelike.game;

import java.util.HashMap;


public class PlayerStats{
    public int levels_completed;
    public int enemies_killed;
    
    public HashMap<String, Integer> pieces_played;

    public PlayerStats(){
        levels_completed = 0;
        enemies_killed = 0;
        pieces_played = new HashMap<String, Integer>();
    }


    public void addPiece(String piece_name){
        pieces_played.put(piece_name, pieces_played.getOrDefault(piece_name, 0) + 1);
    }

    public String displayStats(){
        String str = "\nLevels Beaten: " + levels_completed;
        str += "\nEnemies Killed: " + enemies_killed;

        if (pieces_played.isEmpty()){
            str += "\nPlayed as:";
            for (String piece : pieces_played.keySet()){
                str += "\n" + piece + ": " + pieces_played.get(piece) + " times. ";
            }
        }

        return str;
    }
}