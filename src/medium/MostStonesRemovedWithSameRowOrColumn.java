package medium;

import java.util.HashSet;
import java.util.Objects;

// https://leetcode.com/problems/most-stones-removed-with-same-row-or-column/
public class MostStonesRemovedWithSameRowOrColumn {
    public int removeStones(int[][] stones) {
        int N = stones.length;
        if (N == 0 || N == 1 )
            return 0;
        int count = N;
        HashSet<IntegerPair> visited = new HashSet<>();
        for (int i = 0; i < N; i++) {
            if (!visited.contains(new IntegerPair(stones[i][0], stones[i][1]))) {
                dfs(stones, N, visited, stones[i][0], stones[i][1]);
                count--;
            }
        }
        return count;
    }

    private void dfs(int[][] stones, int size, HashSet<IntegerPair> visited, int x, int y) {
        visited.add(new IntegerPair(x, y));
        for (int i = 0; i < size; i++) {
            if (!visited.contains(new IntegerPair(stones[i][0], stones[i][1])) && (stones[i][0] == x || stones[i][1] == y))
                dfs(stones, size, visited, stones[i][0], stones[i][1]);
        }
    }

    /*
     * Revision Note — Most Stones Removed with Same Row or Column (Medium)
     * Pattern: Connected components via DFS — stones sharing a row or column are connected
     * Key Insight: Any connected component of k stones can be reduced to exactly 1 stone
     *              (remove them in reverse order of discovery), so the answer is
     *              n - (number of connected components). Never simulate the removals.
     * Gotchas:
     *   - IntegerPair MUST override equals and hashCode. Without them HashSet falls back to
     *     identity, every contains() returns false, and the DFS recurses forever.
     *   - count starts at n and decrements once per top-level dfs call, so it lands on
     *     n - components without ever counting component sizes.
     *   - Check visited BEFORE recursing, not at the top of dfs, or two mutually reachable
     *     stones bounce between each other.
     *   - IntegerPair is a non-static inner class: every instance carries a hidden reference
     *     to the enclosing solution object. Harmless at n <= 1000, but static is leaner.
     *   - O(n^2) with a fresh IntegerPair allocated per comparison. Fine at n <= 1000 (~20ms);
     *     Union-Find on row/column keys would be near O(n).
     *   - Recursion depth reaches n in the worst case (all stones in one row). Verified safe
     *     on the default JVM stack at n = 1000.
     */
    public static void main(String[] args) {
        MostStonesRemovedWithSameRowOrColumn M = new MostStonesRemovedWithSameRowOrColumn();

        // Test 1: LeetCode example 1 — one component of 6, so 5 removable
        int[][] t1 = {{0,0},{0,1},{1,0},{1,2},{2,1},{2,2}};
        System.out.println("Test 1: " + M.removeStones(t1) + " (Expected: 5)");

        // Test 2: LeetCode example 2 — two components (4 stones + isolated 1,1)
        int[][] t2 = {{0,0},{0,2},{1,1},{2,0},{2,2}};
        System.out.println("Test 2: " + M.removeStones(t2) + " (Expected: 3)");

        // Test 3: LeetCode example 3 — single stone, nothing to remove
        int[][] t3 = {{0,0}};
        System.out.println("Test 3: " + M.removeStones(t3) + " (Expected: 0)");

        // Test 4: two stones sharing a row
        int[][] t4 = {{0,0},{0,1}};
        System.out.println("Test 4: " + M.removeStones(t4) + " (Expected: 1)");

        // Test 5: two stones sharing a column
        int[][] t5 = {{0,0},{1,0}};
        System.out.println("Test 5: " + M.removeStones(t5) + " (Expected: 1)");

        // Test 6: two stones sharing neither — separate components
        int[][] t6 = {{0,0},{1,1}};
        System.out.println("Test 6: " + M.removeStones(t6) + " (Expected: 0)");

        // Test 7: all stones on a diagonal — every one is its own component
        int[][] t7 = {{0,0},{1,1},{2,2},{3,3}};
        System.out.println("Test 7: " + M.removeStones(t7) + " (Expected: 0)");

        // Test 8: fully connected 2x2 block
        int[][] t8 = {{0,0},{0,1},{1,0},{1,1}};
        System.out.println("Test 8: " + M.removeStones(t8) + " (Expected: 3)");

        // Test 9: one cluster plus a far isolated stone
        int[][] t9 = {{5,5},{5,9},{9,5},{9,9},{100,100}};
        System.out.println("Test 9: " + M.removeStones(t9) + " (Expected: 3)");

        // Test 10: plus shape — all five connected through the centre
        int[][] t10 = {{0,1},{1,0},{1,1},{1,2},{2,1}};
        System.out.println("Test 10: " + M.removeStones(t10) + " (Expected: 4)");
    }

    class IntegerPair {
        Integer key;
        Integer value;

        public IntegerPair(Integer key, Integer value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            IntegerPair pair = (IntegerPair) o;

            if (!Objects.equals(key, pair.key)) return false;
            return Objects.equals(value, pair.value);
        }

        @Override
        public int hashCode() {
            int result = key != null ? key.hashCode() : 0;
            result = 31 * result + (value != null ? value.hashCode() : 0);
            return result;
        }
    }
}

