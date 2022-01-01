package chessroguelike.game.map;

import java.util.Comparator;

public class CompareDate implements Comparator<SavedGame> {
    /**
     * Sort SavedGame objects by date saved
     * Games saved on the same date should be ordered alphabetically
     **/
    if (A.lastModified.equals(B.lastModified)) {
            return A.name.compareTo(B.name);
        }
    else {
        return A.lastModified.compareTo(B.lastModified);
    }
}
