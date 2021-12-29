package chessroguelike.game.map;

import java.util.Comparator;

public class CompareLevel implements Comparator<SavedGame> {
    /**
     * Sort SavedGame objects by levels completed, ascending
     **/
    public int compare(SavedGame A, SavedGame B) {
        return A.stats.levels_completed - B.stats.levels_completed;
    }
}


