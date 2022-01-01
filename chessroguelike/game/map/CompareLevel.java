package chessroguelike.game.map;

import java.util.Comparator;

public class CompareLevel implements Comparator<SavedGame> {
    /**
     * Sort SavedGame objects by levels completed, ascending
     * Games with equal levels completed should be ordered alphabetically
     **/
    public int compare(SavedGame A, SavedGame B) {
        if (A.stats.levels_completed == B.stats.levels_completed) {
            return B.name.toLowerCase().compareTo(A.name.toLowerCase());
        }
        else {
            return A.stats.levels_completed - B.stats.levels_completed;
        }
    }
}


