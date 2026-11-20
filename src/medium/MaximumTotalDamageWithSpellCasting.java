package medium;

import java.util.*;

// https://leetcode.com/problems/maximum-total-damage-with-spell-casting/
public class MaximumTotalDamageWithSpellCasting {
    public long maximumTotalDamage(int[] power) {
        int size = power.length;
        HashMap<Integer,Integer> frequency = new HashMap<>();
        for (int i = 0; i < size; i++)
            frequency.put(power[i], frequency.getOrDefault(power[i], 0) + 1);
        List<int[]> powers = new ArrayList<>();
        for (Map.Entry<Integer, Integer> p : frequency.entrySet())
            powers.add(new int[]{p.getKey(), p.getValue()});
        Collections.sort(powers, Comparator.comparingInt(o -> o[0]));
        size = powers.size();
        long[] dp = new long[size+1];
        for (int i = 0; i < size; i++) {
            long exclude = dp[i];
            int j = i-1;
            long include;
            while (j >= 0 && powers.get(i)[0] - powers.get(j)[0] <= 2)
                j--;
            include = j == -1 ? 0 : dp[j+1];
            dp[i+1] = Math.max(exclude, include + (long) powers.get(i)[0] * powers.get(i)[1]);
        }
        return dp[size];
    }

    /*
     * Revision Note — Maximum Total Damage With Spell Casting (Medium)
     *
     * Pattern: group by value + sort distinct values + House Robber DP with a
     *          "safe predecessor" instead of i-2.
     *
     * Key insight: taking one spell of value v already bans v±1, v±2, so take
     *   EVERY copy: each distinct value collapses to one item worth v * count(v).
     *   Then process distinct values in sorted order; only SMALLER values can
     *   conflict, so dp[i] = max(skip: dp[i-1], take: v*cnt + dp[last value <= v-3]).
     *
     * Gotchas:
     *   - The walk back to the safe index is O(1), not O(n): only v-1 and v-2 can sit
     *     between it and i, so it moves back at most 2 steps (or use a forward-only pointer).
     *   - Values go up to 1e9, so the DP is indexed by distinct-value rank, never by value.
     *   - v * count overflows int (1e9 * 3 already does): cast to long before multiplying
     *     and keep dp as long[].
     *   - The conflict is "distance <= 2", so spacing 3 is compatible and spacing 2 isn't.
     *
     * Complexity: O(n log n) time for the sort (the DP is O(distinct)); O(n) space.
     *
     * Template:
     *   cnt = frequency map; vals = sorted distinct keys
     *   dp[0] = 0
     *   for i: j = i-1; while j >= 0 && vals[i] - vals[j] <= 2: j--
     *          dp[i+1] = max(dp[i], dp[j+1] + (long) vals[i] * cnt[vals[i]])
     */
    public static void main(String[] args) {
        MaximumTotalDamageWithSpellCasting M = new MaximumTotalDamageWithSpellCasting();

        // Test 1: LeetCode example 1 — both 1s are taken together (value grouping)
        System.out.println("Test 1: " + M.maximumTotalDamage(new int[]{1, 1, 3, 4}) + " (Expected: 6)");

        // Test 2: LeetCode example 2 — unsorted input; 6 and 7 conflict, so 1 + 6 + 6 wins over 1 + 7
        System.out.println("Test 2: " + M.maximumTotalDamage(new int[]{7, 1, 6, 6}) + " (Expected: 13)");

        // Test 3: values spaced exactly 3 apart — distance 3 is compatible, so all are taken
        System.out.println("Test 3: " + M.maximumTotalDamage(new int[]{1, 4, 7}) + " (Expected: 12)");

        // Test 4: values spaced exactly 2 apart — distance 2 conflicts, so only every other value
        System.out.println("Test 4: " + M.maximumTotalDamage(new int[]{1, 3, 5, 7, 9}) + " (Expected: 15)");

        // Test 5: unsorted, with repeated values — the safe predecessor is 2 steps back (7 skips 5, lands on 3)
        System.out.println("Test 5: " + M.maximumTotalDamage(new int[]{5, 5, 5, 1, 2, 3, 7, 7, 9}) + " (Expected: 26)");

        // Test 6: repeated maximum values — 1e9 * 3 overflows int; needs the long cast
        System.out.println("Test 6: " + M.maximumTotalDamage(new int[]{1000000000, 1000000000, 1000000000}) + " (Expected: 3000000000)");
    }
}
