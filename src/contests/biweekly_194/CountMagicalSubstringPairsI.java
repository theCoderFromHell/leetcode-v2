package contests.biweekly_194;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

// https://leetcode.com/problems/count-magical-substring-pairs-i/
public class CountMagicalSubstringPairsI {
    public int countMagicalPairs(String s) {
        int size = s.length();
        HashSet<Character> currentChars = new HashSet<>();
        HashMap<Integer, List<int[]>> frequencyMap = new HashMap<>();
        for (int start = 0; start < size; start++) {
            currentChars.clear();
            for (int end = start; end < size; end++) {
                currentChars.add(s.charAt(end));
                frequencyMap.computeIfAbsent(currentChars.size(), k -> new ArrayList<>()).add(new int[]{start, end});
            }
        }
        int result = 0;
        for (int key : frequencyMap.keySet()) {
            List<int[]> bounds = frequencyMap.get(key);
            int[] startCount = new int[size + 1];
            for (int[] b : bounds)
                startCount[b[0]]++;
            for (int[] b : bounds)
                result += startCount[b[1] + 1];
        }
        return result;
    }

    /*
     * Revision Note — Count Magical Substring Pairs I (Medium, Biweekly 194 Q3)
     *
     * Pattern: Enumerate every substring with an incrementally grown distinct-char set, bucket the
     * substrings by distinct count, then count ADJACENT pairs inside each bucket with a hash join
     * on the boundary — not a pairwise scan.
     *
     * Key insight: [s1, e1] and [s2, e2] are adjacent iff e1 + 1 == s2. So count how many
     * intervals START at each index, then every interval [s, e] pairs with startCount[e + 1].
     * That turns an O(N^2) check over N intervals into O(N): one pass to count, one to look up.
     *
     * Gotchas:
     *  - startCount needs length size + 1: the lookup index e + 1 reaches size for any substring
     *    ending at the last character.
     *  - Reset startCount per bucket — pairs only count when both sides have the same distinct count.
     *  - Same distinct count is NOT same length: "aa" | "b" is a pair (aab = 3).
     *  - The uniqueCharacters n×n matrix was written but never read — dropped (n^2 ints for nothing).
     *  - Triplets total C(n + 1, 3) when every pair counts: n <= 2000 gives at most 1,333,333,000,
     *    which fits an int — but an int would overflow past n ≈ 2340.
     *
     * Complexity: O(n^2) time (n^2 / 2 substrings, O(1) set add each; buckets <= 26, each
     * startCount O(n)), O(n^2) space for the bucketed int[] intervals.
     *
     * Template:
     *   for start: clear set; for end >= start: set.add(s[end]); bucket[set.size()].add({start, end})
     *   for each bucket: cnt[start]++ for all;  result += cnt[end + 1] for all
     *
     * Alternative (same O(n^2) time, O(26n) space, no n^2 / 2 object allocations):
     *   count[k][start] = substrings starting at start with k distinct chars (pass 1),
     *   then for each substring (start, end, k): result += count[k][end + 1] (pass 2).
     *   A boolean[26] seen array + counter replaces the HashSet<Character> and its boxing.
     */
    public static void main(String[] args) {
        CountMagicalSubstringPairsI C = new CountMagicalSubstringPairsI();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + C.countMagicalPairs("aba") + " (Expected: 2)");

        // Test 2: LeetCode example 2
        System.out.println("Test 2: " + C.countMagicalPairs("kkk") + " (Expected: 4)");

        // Test 3: LeetCode example 3
        System.out.println("Test 3: " + C.countMagicalPairs("abcac") + " (Expected: 7)");

        // Test 4: single char — no second substring to pair with
        System.out.println("Test 4: " + C.countMagicalPairs("a") + " (Expected: 0)");

        // Test 5: smallest pair — two different chars, 1 distinct each
        System.out.println("Test 5: " + C.countMagicalPairs("ab") + " (Expected: 1)");

        // Test 6: equal distinct count with unequal lengths still pairs ("aa" | "b")
        System.out.println("Test 6: " + C.countMagicalPairs("aab") + " (Expected: 3)");

        // Test 7: one bucket holds everything — every adjacent pair counts, C(n + 1, 3)
        System.out.println("Test 7: " + C.countMagicalPairs("zzzzzzzzzz") + " (Expected: 165)");

        // Test 8: all 26 letters — 26 buckets, distinct count == length
        System.out.println("Test 8: " + C.countMagicalPairs("abcdefghijklmnopqrstuvwxyz") + " (Expected: 169)");

        // Test 9: max n, one letter — the largest possible answer, C(2001, 3), still fits an int
        System.out.println("Test 9: " + C.countMagicalPairs("a".repeat(2000)) + " (Expected: 1333333000)");
    }
}
