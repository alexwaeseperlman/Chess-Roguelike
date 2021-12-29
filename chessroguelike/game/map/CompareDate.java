package chessroguelike.game.map;

import java.util.Comparator;

public class CompareDate implements Comparator<SavedGame> {
    /**
     * Sort SavedGame objects by date saved
     **/
    public int compare(SavedGame A, SavedGame B) {
        return A.lastModified.compareTo(B.lastModified);
    }
}
