package medium;

import common.Util;

// https://leetcode.com/problems/count-vowel-strings-in-ranges/
public class CountVowelStringsInRanges {
    public int[] vowelStrings(String[] words, int[][] queries) {
        int size = words.length;
        int[] prefix = new int[size];
        for (int i = 0; i < size; i++)
            prefix[i] = (i > 0 ? prefix[i - 1] : 0) + ((isVowel(words[i].charAt(0)) && isVowel(words[i].charAt(words[i].length() - 1))) ? 1 : 0);
        int length = queries.length;
        int[] result = new int[length];
        for (int i = 0; i < length; i++)
            result[i] = (queries[i][0] == 0) ? prefix[queries[i][1]] : prefix[queries[i][1]] - prefix[queries[i][0] - 1];
        return result;
    }

    private boolean isVowel(char c) {
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
            return true;
        return false;
    }

    /*
     * Revision Note — Count Vowel Strings in Ranges (Medium)
     *
     * Pattern: Prefix count array + range sum query
     *
     * Key Insight: Build prefix[i] = number of qualifying words in words[0..i];
     * each range query [l, r] then resolves to prefix[r] - prefix[l-1] in O(1).
     *
     * Gotchas:
     * - Special-case l == 0 (no prefix[l-1] to subtract) — or use size+1 sentinel array to avoid it
     * - Single-char word: charAt(0) and charAt(length-1) are the same char — still works correctly
     * - Use char (primitive) not Character in isVowel to avoid autoboxing
     *
     * Template:
     *   int[] prefix = new int[n];
     *   for (int i = 0; i < n; i++)
     *       prefix[i] = (i > 0 ? prefix[i-1] : 0) + (qualifies(words[i]) ? 1 : 0);
     *   for each query [l, r]:
     *       result = (l == 0) ? prefix[r] : prefix[r] - prefix[l-1]
     */
    public static void main(String[] args) {
        CountVowelStringsInRanges C = new CountVowelStringsInRanges();
        // "aba"✓ "bcb"✗ "ece"✓ "aa"✓ "e"✓ → prefix=[1,1,2,3,4]
        Util.printArray(C.vowelStrings(new String[]{"aba", "bcb", "ece", "aa", "e"}, new int[][]{{0, 2}, {1, 4}, {1, 3}})); // [2, 3, 2]
        Util.printArray(C.vowelStrings(new String[]{"a", "e", "i"}, new int[][]{{0, 2}, {0, 0}, {2, 2}}));                  // [3, 1, 1]
        Util.printArray(C.vowelStrings(new String[]{"xyz", "abc"}, new int[][]{{0, 1}}));                                    // [0]
        Util.printArray(C.vowelStrings(new String[]{"umbrella"}, new int[][]{{0, 0}}));                                      // [1] (starts 'u'✓ ends 'a'✓)
    }
}
