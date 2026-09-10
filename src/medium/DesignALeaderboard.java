package medium;

import java.util.*;

// https://leetcode.com/problems/design-a-leaderboard/
public class DesignALeaderboard {
    static class Leaderboard {
        PriorityQueue<int[]> queue;
        HashMap<Integer,Integer> scores;
        public Leaderboard() {
            this.queue = new PriorityQueue<>((a, b) -> b[0] - a[0]);
            this.scores = new HashMap<>();
        }

        public void addScore(int playerId, int score) {
            scores.put(playerId, scores.getOrDefault(playerId, 0) + score);
            queue.add(new int[]{scores.get(playerId), playerId});
        }

        public int top(int K) {
            int sum = 0;
            List<int[]> temp = new ArrayList<>();
            HashSet<Integer> visited = new HashSet<>();
            while (K > 0) {
                if (queue.isEmpty())
                    break;
                int[] score = queue.poll();
                if (!visited.contains(score[1]) && scores.containsKey(score[1]) && scores.get(score[1]) == score[0]) {
                    visited.add(score[1]);
                    sum += score[0];
                    temp.add(score);
                    K--;
                }
            }
            queue.addAll(temp);
            return sum;
        }

        public void reset(int playerId) {
            scores.remove(playerId);
            queue.add(new int[]{0, playerId});
        }
    }

    /*
     * Revision Note — Design A Leaderboard (Medium)
     * Pattern: HashMap for player lookup + max-heap for score order, with lazy deletion
     *          and a NON-DESTRUCTIVE top() query
     * Key Insight: addScore/reset want a player-keyed structure; top() wants score ordering.
     *              Keep both: the map is the source of truth, the heap is a stale-tolerant index.
     *              A heap entry is live only if it still matches the map.
     * Gotchas:
     *   - Decrement K only when an entry is COUNTED, not on every poll. Decrementing per poll lets
     *     stale entries eat the budget and top(3) silently sums fewer than 3 players.
     *   - top() must not consume the heap. Buffer counted entries in temp and addAll them back,
     *     otherwise calling top() twice in a row returns 0 the second time.
     *   - The visited set is REQUIRED, not defensive. Matching on score alone is not a unique
     *     fingerprint: reset() breaks score monotonicity, so a player can climb back to a score
     *     they already held, leaving two live (score, id) entries that BOTH pass the staleness
     *     check and get summed twice. Without visited: add(1,10), reset(1), add(1,10), top(2)
     *     returns 20 instead of 10.
     *   - Contrast with First Unique Number: there counts only ever increase, so value-matching
     *     alone is a safe staleness test. Lazy deletion by value is only sound when the value
     *     cannot repeat for the same key — check for FALSE POSITIVES, not just stale hits.
     *   - Dropping a visited-duplicate without restoring it to temp is deliberate: a second entry
     *     for an already-counted player is dead weight, so discarding it keeps the heap bounded.
     *   - Comparator returns a difference (b[0]-a[0]), never a bare value. Max total is
     *     1000 calls x score 100 = 10^5, so no overflow.
     * Alternative: skip the heap entirely — HashMap plus sorting the values inside top().
     *              O(1) addScore/reset, O(n log n) top. At <= 1000 calls that is trivially fast,
     *              and one player = one map entry makes the double-count bug impossible by
     *              construction. The heap optimises a top() that was never the bottleneck.
     */
    public static void main(String[] args) {
        // Test 1: LeetCode example — top(1) and top(3), then resets and a re-add
        Leaderboard L = new Leaderboard();
        L.addScore(1, 73);
        L.addScore(2, 56);
        L.addScore(3, 39);
        L.addScore(4, 51);
        L.addScore(5, 4);
        System.out.println("Test 1: " + L.top(1) + " (Expected: 73)");
        System.out.println("Test 2: " + L.top(3) + " (Expected: 180)");
        L.reset(1);
        L.reset(2);
        L.addScore(2, 51);
        System.out.println("Test 3: " + L.top(3) + " (Expected: 141)");

        // Test 4: repeated addScore for the same player must accumulate, not overwrite
        Leaderboard M = new Leaderboard();
        M.addScore(1, 10);
        M.addScore(1, 20);
        M.addScore(1, 30);
        System.out.println("Test 4: " + M.top(1) + " (Expected: 60)");

        // Test 5: K larger than the number of players — sum everyone
        Leaderboard N = new Leaderboard();
        N.addScore(1, 5);
        N.addScore(2, 7);
        System.out.println("Test 5: " + N.top(10) + " (Expected: 12)");

        // Test 6: top() called twice in a row must give the same answer
        System.out.println("Test 6: " + N.top(10) + " (Expected: 12)");

        // Test 7: reset then addScore — player restarts from 0, not their old total
        Leaderboard O = new Leaderboard();
        O.addScore(1, 100);
        O.reset(1);
        O.addScore(1, 5);
        System.out.println("Test 7: " + O.top(1) + " (Expected: 5)");

        // Test 8: reset every player, leaderboard is effectively empty
        Leaderboard P = new Leaderboard();
        P.addScore(1, 50);
        P.addScore(2, 60);
        P.reset(1);
        P.reset(2);
        System.out.println("Test 8: " + P.top(2) + " (Expected: 0)");

        // Test 9: single player, top(1)
        Leaderboard Q = new Leaderboard();
        Q.addScore(7, 42);
        System.out.println("Test 9: " + Q.top(1) + " (Expected: 42)");

        // Test 10: ties — two players on the same score
        Leaderboard R = new Leaderboard();
        R.addScore(1, 30);
        R.addScore(2, 30);
        R.addScore(3, 10);
        System.out.println("Test 10: " + R.top(2) + " (Expected: 60)");

        // Test 11: accumulate past a rival, then query
        Leaderboard S = new Leaderboard();
        S.addScore(1, 10);
        S.addScore(2, 25);
        S.addScore(1, 20);
        System.out.println("Test 11: " + S.top(1) + " (Expected: 30)");
        System.out.println("Test 12: " + S.top(2) + " (Expected: 55)");

        // Test 13: interleaved reset and accumulation across several players
        Leaderboard T = new Leaderboard();
        T.addScore(1, 10);
        T.addScore(2, 20);
        T.addScore(3, 30);
        T.reset(2);
        T.addScore(1, 15);
        System.out.println("Test 13: " + T.top(3) + " (Expected: 55)");
        System.out.println("Test 14: " + T.top(1) + " (Expected: 30)");
    }
}
