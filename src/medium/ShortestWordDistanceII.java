package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// https://leetcode.com/problems/shortest-word-distance-ii/
public class ShortestWordDistanceII {

    static class WordDistance {
        HashMap<String, List<Integer>> indices;

        public WordDistance(String[] wordsDict) {
            this.indices = new HashMap<>();
            int size = wordsDict.length;
            for (int i = 0; i < size; i++) {
                String word = wordsDict[i];
                List<Integer> index = indices.getOrDefault(word, new ArrayList<>());
                index.add(i);
                indices.put(word, index);
            }
        }

        public int shortest(String word1, String word2) {
            List<Integer> indices1 = indices.get(word1);
            List<Integer> indices2 = indices.get(word2);
            int shortest = Integer.MAX_VALUE;
            int i = 0, j = 0;
            int size1 = indices1.size();
            int size2 = indices2.size();
            while (i < size1 && j < size2) {
                shortest = Math.min(shortest, Math.abs(indices1.get(i) - indices2.get(j)));
                if (indices1.get(i) < indices2.get(j))
                    i++;
                else
                    j++;
            }
            return shortest;
        }
    }

    /*
     * Revision Note — Shortest Word Distance II (Medium)
     * Pattern: Precompute index lists per word, then two-pointer merge per query
     * Key Insight: The "II" is the whole problem. 243 asks once, so a single O(n) pass tracking
     *              each word's last seen index is enough. Here shortest() is called repeatedly,
     *              so rescanning is 10^4 * 3*10^4 = 3*10^8. Move the work into the constructor:
     *              map each word to its list of indices, which comes out ALREADY SORTED because
     *              you build it left to right. Each query then merges two sorted lists.
     * Gotchas:
     *   - Precompute per WORD, not per PAIR. An all-pairs distance table is O(k^2) entries —
     *     at 3*10^4 distinct words that is ~9*10^8 and dies with OutOfMemoryError. The two
     *     constraints are the tell: n <= 3*10^4 with only <= 10^4 calls means the number of
     *     POSSIBLE queries (k^2) hugely exceeds the number of ACTUAL ones. Compute lazily.
     *   - Never key a map on word1 + word2. Concatenation is not injective: "ab"+"c" and
     *     "a"+"bc" both give "abc", so one pair silently overwrites the other.
     *     {ab, _, _, _, c, a, bc} returns 1 for ab/c when the truth is 4. If a composite key is
     *     ever needed, use a separator that cannot appear in the inputs ("ab|c").
     *   - Fields declared but never assigned NPE on first use. A main() that passes an empty
     *     array hides it completely — the loop never runs, so the scaffold's own placeholder is
     *     the one input that does not crash. Always test with real data.
     *   - Advance the pointer at the SMALLER index. Both lists ascend, so moving the larger one
     *     can only widen the gap; moving the smaller is the only step that can shrink it.
     *   - shortest() must not mutate state — it is called many times, and repeated or reversed
     *     queries must give the same answer.
     * Template:
     *   ctor:  for i, word: indices.computeIfAbsent(word, ArrayList::new).add(i)
     *   query: i = j = 0; best = MAX
     *          while i < a.size() && j < b.size():
     *              best = min(best, abs(a[i] - b[j]))
     *              if a[i] < b[j]: i++ else: j++
     * Complexity: O(n) build, O(n) space, O(|a| + |b|) per query — versus O(n*k) build and
     *             O(k^2) space for the all-pairs table that does not fit.
     */
    public static void main(String[] args) {
        // Test 1: LeetCode example — coding at 3, practice at 0
        WordDistance W = new WordDistance(new String[]{"practice", "makes", "perfect", "coding", "makes"});
        System.out.println("Test 1: " + W.shortest("coding", "practice") + " (Expected: 3)");

        // Test 2: duplicates matter — "makes" is at 1 and 4, the nearer one wins
        System.out.println("Test 2: " + W.shortest("makes", "coding") + " (Expected: 1)");

        // Test 3: repeated query must be idempotent — shortest() must not mutate state
        System.out.println("Test 3: " + W.shortest("makes", "coding") + " (Expected: 1)");

        // Test 4: argument order must not change the answer
        System.out.println("Test 4: " + W.shortest("coding", "makes") + " (Expected: 1)");

        // Test 5: minimum-size list
        WordDistance T = new WordDistance(new String[]{"a", "b"});
        System.out.println("Test 5: " + T.shortest("a", "b") + " (Expected: 1)");

        // Test 6: KEY-COLLISION GUARD — "ab"+"c" and "a"+"bc" both concatenate to "abc".
        //         A map keyed on raw concatenation returns 1 here; the truth is 4.
        WordDistance C = new WordDistance(new String[]{"ab", "zzz", "zzz", "zzz", "c", "a", "bc"});
        System.out.println("Test 6: " + C.shortest("ab", "c") + " (Expected: 4)");

        // Test 7: the colliding partner pair, genuinely adjacent
        System.out.println("Test 7: " + C.shortest("a", "bc") + " (Expected: 1)");

        // Test 8: both words repeat — closest pair is interior, not at either end
        WordDistance R = new WordDistance(new String[]{"a", "x", "x", "b", "x", "a", "b"});
        System.out.println("Test 8: " + R.shortest("a", "b") + " (Expected: 1)");

        // Test 9: one word at both extremes, the other in the middle
        WordDistance M = new WordDistance(new String[]{"a", "x", "x", "x", "b", "x", "x", "x", "a"});
        System.out.println("Test 9: " + M.shortest("a", "b") + " (Expected: 4)");

        // Test 10: SCALE GUARD — 3*10^4 distinct words. An all-pairs table is ~9*10^8 entries
        //          and throws OutOfMemoryError; per-word index lists build in milliseconds.
        String[] big = new String[30000];
        for (int i = 0; i < big.length; i++)
            big[i] = "w" + i;
        WordDistance B = new WordDistance(big);
        System.out.println("Test 10: " + B.shortest("w0", "w29999") + " (Expected: 29999)");

    }
}
