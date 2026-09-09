package medium;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// https://leetcode.com/problems/maximum-score-from-removing-stones/

public class MaximumScoreFromRemovingStones {
    public int maximumScore(int a, int b, int c) {
        List<Integer> piles = new ArrayList<>();
        piles.add(a);
        piles.add(b);
        piles.add(c);
        piles.sort(Comparator.naturalOrder());
        if (piles.get(0) + piles.get(1) <= piles.get(2))
            return piles.get(0) + piles.get(1);
        else
            return piles.get(2) + (piles.get(0) + piles.get(1) - piles.get(2))/2;
    }

    /*
     * Pattern: Greedy math on 3 piles
     * Key Insight: Answer = min(total - max, total/2).
     *   - If the largest pile >= sum of the other two, it absorbs them entirely.
     *   - Otherwise stones distribute evenly and the answer is floor(total/2).
     * Gotchas:
     *   - The formula `large + (small+medium-large)/2` is equivalent to total/2
     *     but written in terms of the sorted values — both are correct.
     *   - Simpler alternative: Math.min(a+b+c - Math.max(a,Math.max(b,c)), (a+b+c)/2)
     */
    public static void main(String[] args) {
        MaximumScoreFromRemovingStones M = new MaximumScoreFromRemovingStones();
        System.out.println("Test 1: " + M.maximumScore(2, 4, 6) + " (Expected: 6)");
        System.out.println("Test 2: " + M.maximumScore(4, 4, 6) + " (Expected: 7)");
        System.out.println("Test 3: " + M.maximumScore(1, 8, 8) + " (Expected: 8)");
        System.out.println("Test 4: " + M.maximumScore(2, 2, 2) + " (Expected: 3)");
        System.out.println("Test 5: " + M.maximumScore(0, 0, 5) + " (Expected: 0)");
        System.out.println("Test 6: " + M.maximumScore(1, 1, 1) + " (Expected: 1)");
    }
}
