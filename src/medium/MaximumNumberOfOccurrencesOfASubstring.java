package medium;

import java.util.HashMap;

// https://leetcode.com/problems/maximum-number-of-occurrences-of-a-substring/
public class MaximumNumberOfOccurrencesOfASubstring {
    public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
        int length = s.length();
        int start = 0;
        int[] count = new int[26];
        HashMap<String,Integer> frequency = new HashMap<>();
        int result = 0;
        for (int end = 0; end < length; end++) {
            count[s.charAt(end) - 'a']++;
            if (end - start + 1 > minSize) {
                count[s.charAt(start) - 'a']--;
                start++;
            }
            if (end - start + 1 == minSize) {
                if (valid(count, maxLetters)) {
                    String curr = s.substring(start, end + 1);
                    frequency.put(curr, frequency.getOrDefault(curr, 0) + 1);
                    result = Math.max(result, frequency.get(curr));
                }
            }
        }
        return result;
    }

    private boolean valid(int[] count, int maxLetters) {
        int unique = 0;
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0)
                unique++;
        }
        return unique <= maxLetters;
    }

    /*
     * Revision Note - Maximum Number of Occurrences of a Substring (Medium)
     *
     * Pattern: fixed-size sliding window with a 26-count array + HashMap<String, Integer> of
     * window frequencies. Same window as 567 Permutation in String; the per-window test is
     * "distinct letters <= maxLetters" instead of "counts match the target".
     *
     * Key Insight: maxSize is a decoy. Any valid substring t longer than minSize has a prefix p of
     * length exactly minSize that is also valid (a part of t can't have MORE distinct letters) and
     * occurs at least as often (every copy of t starts with a copy of p). So the best answer is
     * always reached at length minSize, and only windows of exactly that size need checking.
     *
     * Gotchas:
     * - Overlapping occurrences count: "aaaa" with size 3 gives "aaa" twice (indices 0-2, 1-3),
     *   so slide one step at a time, never in chunks.
     * - The prefix argument needs t to be LONGER than minSize. With minSize = maxSize there is
     *   nothing shorter to fall back to, which is consistent: the only length is minSize anyway.
     * - Shrink before checking: add s[end], drop s[start] once the window exceeds minSize, then
     *   test only when the size is exactly minSize. The first minSize-1 iterations record nothing.
     * - No valid window at all returns 0 ("abcde", maxLetters 2, size 3).
     *
     * Complexity: O(n * (26 + minSize)) time. Each window rescans the 26 counts and builds and
     * hashes a minSize-length key; minSize <= 26, so effectively O(n). O(n * minSize) space for the
     * map keys in the worst case (all windows distinct).
     *
     * Template:
     *   for end in 0..n-1:
     *       count[s[end]]++
     *       if window > minSize: count[s[start++]]--
     *       if window == minSize and distinct(count) <= maxLetters:
     *           freq[s[start..end]]++; best = max(best, freq[...])
     *   return best
     */

    public static void main(String[] args) {
        MaximumNumberOfOccurrencesOfASubstring M = new MaximumNumberOfOccurrencesOfASubstring();

        System.out.println("Test 1: " + M.maxFreq("aababcaab", 2, 3, 4) + " (Expected: 2)");
        System.out.println("Test 2: " + M.maxFreq("aaaa", 1, 3, 3) + " (Expected: 2)");           // overlapping copies count
        System.out.println("Test 3: " + M.maxFreq("aabcabcab", 2, 2, 3) + " (Expected: 3)");      // "ab" x3 beats every length-3 string
        System.out.println("Test 4: " + M.maxFreq("abcde", 2, 3, 3) + " (Expected: 0)");          // no window passes maxLetters
        System.out.println("Test 5: " + M.maxFreq("a", 1, 1, 1) + " (Expected: 1)");              // single character
        System.out.println("Test 6: " + M.maxFreq("ababab", 2, 2, 4) + " (Expected: 3)");         // decoy: "ab" x3 beats "abab" x2
        System.out.println("Test 7: " + M.maxFreq("abcabcaa", 2, 3, 3) + " (Expected: 1)");       // most frequent window "abc" is invalid, only "caa" counts
        System.out.println("Test 8: " + M.maxFreq("zzzzzzzzzz", 1, 1, 10) + " (Expected: 10)");   // maxSize = n, answer still comes from minSize = 1
    }
}
