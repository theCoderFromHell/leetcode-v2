package medium;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/best-team-with-no-conflicts/
public class BestTeamWithNoConflicts {
    public int bestTeamScore(int[] scores, int[] ages) {
        int size = scores.length;
        List<int[]> players = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            players.add(new int[]{scores[i], ages[i]});
        }
        players.sort((o1, o2) -> {
            if (o1[1] == o2[1])
                return o1[0] - o2[0];
            return o1[1] - o2[1];
        });
        int[] dp = new int[size];
        dp[0] = players.get(0)[0];
        int result = dp[0];
        for (int i = 1; i < size; i++) {
            dp[i] = players.get(i)[0];
            for (int j = 0; j < i; j++) {
                if (players.get(j)[0] <= players.get(i)[0])
                    dp[i] = Math.max(dp[i], dp[j] + players.get(i)[0]);

            }
            result = Math.max(result, dp[i]);
        }
        return result;
    }


    /*
     * Revision Note — Best Team With No Conflicts (Medium)
     *
     * Pattern: LIS-style DP — maximum sum non-decreasing subsequence
     *
     * Key Insight: Sort players by age (then by score for same age), then find the
     * maximum sum subsequence where scores are non-decreasing — identical to LIS
     * but optimising sum instead of length.
     *
     * Gotchas:
     * - Sort same-age players by score ascending — ensures both can always be included together
     * - Initialise result = dp[0] before the loop, not inside it — otherwise single-player
     *   input (or when player 0 is the best team alone) returns 0
     * - Condition is score[j] <= score[i] (non-decreasing), not strictly less
     *
     * Template:
     *   sort players by (age ASC, score ASC)
     *   dp[0] = score[0]; result = dp[0]
     *   for i in 1..n-1:
     *       dp[i] = score[i]
     *       for j in 0..i-1:
     *           if score[j] <= score[i]: dp[i] = max(dp[i], dp[j] + score[i])
     *       result = max(result, dp[i])
     *   return result
     */
    public static void main(String[] args) {
        BestTeamWithNoConflicts B = new BestTeamWithNoConflicts();
        System.out.println(B.bestTeamScore(new int[]{1,3,5,10,15}, new int[]{1,2,3,4,5}));   // 34 (take all)
        System.out.println(B.bestTeamScore(new int[]{4,5,6,5}, new int[]{2,1,2,1}));          // 16
        System.out.println(B.bestTeamScore(new int[]{1,2,3,5}, new int[]{8,9,10,1}));         // 6  (ages 8,9,10)
        System.out.println(B.bestTeamScore(new int[]{1}, new int[]{4}));                       // 1  (single player)
        System.out.println(B.bestTeamScore(new int[]{10,1}, new int[]{1,2}));                  // 10 (younger has higher score, can't pick both)
    }
}
