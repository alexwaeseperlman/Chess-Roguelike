package chessroguelike.game.map;

import java.util.Comparator;

public class SearchLevel implements Comparator<SavedGame> {
    int level;

    public SearchLevel(int level){
        this.level = level;
    }

    /**
    * Calculates the absolute distance between given level and searched level
    * adjustment given so higher levels would always rank higher than lower levels
    * ex. if searched level is 3, 4 would rank before 2
    * @param save_lvl : level of the saved game being searched
    */
    int distance(int save_lvl){
        // if level is the one being searched, return -1 (lowest possible)
        if (save_lvl == level){
            return -1;
        } 
        // if level is greater than the one being searched, return their difference minus one (so it's smaller than the plain difference)
        else if (save_lvl > level){
            return save_lvl - level - 1;
        } 
        // if level is less than the one being searched, return their plain difference
        else{
            return level - save_lvl;
        }
    }

    /**
     * Sort SavedGame by edit distance to the searched name
     **/
    public int compare(SavedGame A, SavedGame B) {        
        return distance(A.stats.levels_completed) - distance(B.stats.levels_completed);
    }
}