package chessroguelike.game.map;

import java.util.Comparator;

public class SearchName implements Comparator<SavedGame> {
    String name;
    int length;

    final int deleteCost, addCost, changeCost;

    public SearchName(String name){
        this(name, 1, 1, 1);
    }

    public SearchName(String name, int deleteCost, int addCost, int changeCost){
        this.name = name;
        this.length = name.length();
        this.deleteCost = deleteCost;
        this.addCost = addCost;
        this.changeCost = changeCost;
    }

    /**
     * Calculate the edit distance between two strings.
     *
     * More information here https://en.wikipedia.org/wiki/Edit_distance
     * */
    public int dist(String b) {
        // DP[i] stores the edit distance for the prefix of `a`
        // ending at i
        int[] dp = new int[name.length()+1];

        // Iterate over lengths of b
        // When the prefix of `b` starts at zero then every character
        // of `a` needs to be deleted
        for (int i = 0; i <= name.length(); i++) dp[i] = i*deleteCost;
        for (int i = 1; i <= b.length(); i++) {
            // prev represents the edit distance for the prefix of `a` starting at j-1, and the prefix of `b` starting at j-1
            int prev = dp[0];
            dp[0] = i*deleteCost;
            for (int j = 1; j <= name.length(); j++) {
                int current = Math.min(dp[j] + addCost, prev + changeCost);
                current = Math.min(current, dp[j-1] + deleteCost);
                if (b.charAt(i-1) == name.charAt(j-1)) {
                    current = prev;
                }
                prev = dp[j];
                dp[j] = current;
            }
        }
        return dp[length];
    }

    /**
     * Sort SavedGame by edit distance to the searched name
     **/
    public int compare(SavedGame A, SavedGame B) {
        return dist(A.name) - dist(B.name);
    }
}