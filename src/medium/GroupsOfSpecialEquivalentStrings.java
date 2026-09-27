package medium;

import java.util.*;

// https://leetcode.com/problems/groups-of-special-equivalent-strings/
public class GroupsOfSpecialEquivalentStrings {
    public int numSpecialEquivGroups(String[] words) {
        int size = words.length;
        HashSet<String> groups = new HashSet<>();
        for (int i = 0; i < size; i++) {
            String word = words[i];
            int length = word.length();
            char[] even = new char[(length + 1)/2];
            char[] odd = new char[length/2];
            for (int j = 0; j < length; j++) {
                if (j % 2 == 0)
                    even[j/2] = word.charAt(j);
                else
                    odd[(j-1)/2] = word.charAt(j);
            }
            Arrays.sort(even);
            Arrays.sort(odd);
            groups.add(new String(even) + "+" + new String(odd));
        }
        return groups.size();
    }

    /*
     * Revision Note — Groups of Special-Equivalent Strings (Medium)
     *
     * Pattern: Canonical-key hashing — count equivalence classes by unique signature
     *
     * Key Insight: Swaps are allowed only between same-parity indices, so a word's identity
     * is exactly (multiset of even-index chars, multiset of odd-index chars). Sort each half
     * independently and concatenate — equivalent words collide, others do not. The answer is
     * the number of distinct keys.
     *
     * Gotchas:
     * - MULTISET, not set: duplicates matter. even=[a,a,b] and even=[a,b,b] are different
     *   groups even though both have char-set {a,b}. Sorting a char[] preserves multiplicity
     * - `words.length` (array size) vs `word.length()` (string length) type-check identically.
     *   Getting it wrong either throws StringIndexOutOfBounds or — worse — silently hashes
     *   only a prefix and merges unrelated words
     * - Split sizes are (n+1)/2 even and n/2 odd, so odd-length words work without a special case
     * - Only the KEY COUNT matters, so a HashSet<String> of signatures is the right structure —
     *   a Map<String,List<String>> would build group lists that nothing ever reads
     * - Sorting is O(L log L); a 52-slot count array (26 even + 26 odd) gets it to O(L)
     *
     * Template:
     *   for each word:
     *     split chars by index parity into even[] and odd[]
     *     sort both
     *     key = new String(even) + "+" + new String(odd)
     *     keys.add(key)
     *   return keys.size()
     */
    public static void main(String[] args) {
        GroupsOfSpecialEquivalentStrings G = new GroupsOfSpecialEquivalentStrings();

        System.out.println("Test 1: " + G.numSpecialEquivGroups(new String[]{"abcd", "cdab", "cbad", "xyzz", "zzxy", "zzyx"}) + " (Expected: 3)");
        System.out.println("Test 2: " + G.numSpecialEquivGroups(new String[]{"abc", "acb", "bac", "bca", "cab", "cba"})       + " (Expected: 3)");
        System.out.println("Test 3: " + G.numSpecialEquivGroups(new String[]{"a", "b", "c", "a"})                             + " (Expected: 3)"); // single-char words
        System.out.println("Test 4: " + G.numSpecialEquivGroups(new String[]{"aa"})                                           + " (Expected: 1)"); // single word
        System.out.println("Test 5: " + G.numSpecialEquivGroups(new String[]{"axaybz", "axbybz"})                             + " (Expected: 2)"); // multiset, not set: [a,a,b] != [a,b,b]
    }
}
