package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// https://leetcode.com/problems/count-nodes-with-the-highest-score/
public class CountNodesWithTheHighestScore {
    public int countHighestScoreNodes(int[] parents) {
        int size = parents.length;
        List<List<Integer>> children = new ArrayList<>();
        for (int i = 0; i < size; i++)
            children.add(new ArrayList<>());
        for (int i = 1; i < size; i++)
            children.get(parents[i]).add(i);
        HashMap<Long,Integer> count = new HashMap<>();
        solve(0, children, size, count);
        int result = 0;
        long highestScore = 0;
        for (long score : count.keySet()) {
            if (score > highestScore) {
                highestScore = score;
                result = count.get(highestScore);
            }
        }
        return result;
    }

    private int solve(int root, List<List<Integer>> children, int size, HashMap<Long, Integer> count) {
        List<Integer> childNodes = children.get(root);
        if (childNodes == null || childNodes.isEmpty()) {
            count.put((long) (size - 1), count.getOrDefault((long)size - 1, 0) + 1);
            return 1;
        }
        long score = 1;
        int left, right = 0, top = size - 1;
        left = solve(childNodes.getFirst(), children, size, count);
        top -= left;
        score *= left;
        if (childNodes.size() == 2) {
            right = solve(childNodes.get(1), children, size, count);
            top -= right;
            score *= right;
        }
        if (top > 1)
            score *= top;
        count.put(score, count.getOrDefault(score, 0) + 1);
        return (left + right + 1);
    }

    /*
     * Revision Note — Count Nodes With the Highest Score (Medium)
     * Pattern: Post-order DFS on a tree built from a flat parents[] array
     * Key Insight: Removing node v splits the tree into at most 3 components:
     *              {leftSubtreeSize, rightSubtreeSize, n - subtreeSize(v)}.
     *              Score = product of the NON-ZERO component sizes (skip 0-sized "missing"
     *              components like the root's parent side or a leaf's empty children).
     * Gotchas:
     *   - Use long for the score: worst case (n/3)^3 ≈ 3.7e13 — int overflows at ~2.1e9
     *   - When using HashMap<Long, ?>, every key lookup must also produce a Long; an int
     *     key autoboxes to Integer and never matches Long entries (silent miss → returns
     *     default). Use a long local or cast both sides explicitly.
     *   - Leaves still need to record a score (n - 1) — easy to forget when the base case
     *     returns early.
     *   - Tracking (maxScore, countOfMax) inline avoids the HashMap entirely and is cleaner
     *     than building a full frequency map you only ever read the max key of.
     */
    public static void main(String[] args) {
        CountNodesWithTheHighestScore C = new CountNodesWithTheHighestScore();

        // Example 1: leaves 1, 3, 4 each remove to leave a single component of size 4 → max 4, count 3
        System.out.println("Test 1: " + C.countHighestScoreNodes(new int[]{-1, 2, 0, 2, 0}) + " (Expected: 3)");

        // Example 2: path 0→2→1; removing 0 or 1 leaves a 2-node component → max 2, count 2
        System.out.println("Test 2: " + C.countHighestScoreNodes(new int[]{-1, 2, 0}) + " (Expected: 2)");

        // n = 2 minimum: both nodes score 1 (removing either leaves a single-node component)
        System.out.println("Test 3: " + C.countHighestScoreNodes(new int[]{-1, 0}) + " (Expected: 2)");

        // Linear chain 0-1-2-3-4: nodes 0 and 4 score 4, node 2 scores 2*2=4, nodes 1,3 score 3
        // Max 4, count 3 (nodes 0, 2, 4)
        System.out.println("Test 4: " + C.countHighestScoreNodes(new int[]{-1, 0, 1, 2, 3}) + " (Expected: 3)");

        // Perfect binary tree n=7: root scores 3*3=9 (unique max); each internal scores 1*1*4=4;
        // each of 4 leaves scores 6. Max 9, count 1.
        System.out.println("Test 5: " + C.countHighestScoreNodes(new int[]{-1, 0, 0, 1, 1, 2, 2}) + " (Expected: 1)");

        // Two-leaf root (n=3 perfect): root scores 1, both leaves score 2 → max 2, count 2
        System.out.println("Test 6: " + C.countHighestScoreNodes(new int[]{-1, 0, 0}) + " (Expected: 2)");

        // Perfect tree n=15: root scores 7*7=49; nodes 1,2 score 3*3*8=72 (max);
        // nodes 3..6 score 1*1*12=12; leaves 7..14 score 14. Max 72, count 2.
        System.out.println("Test 7: " + C.countHighestScoreNodes(new int[]{-1, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6})
                + " (Expected: 2)");
    }
}
