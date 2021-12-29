package chessroguelike.game.map;

import java.util.Comparator;

public class CompareName implements Comparator<SavedGame> {
    /**
     * Sort SavedGame objects by file name, alphabetically
     **/
    public int compare(SavedGame A, SavedGame B) {
        return A.name.compareTo(B.name);
    }
}


