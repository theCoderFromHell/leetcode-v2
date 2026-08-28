package medium;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

// https://leetcode.com/problems/custom-sort-string/
public class CustomSortString {
    public String customSortString(String order, String s) {
        HashMap<Character,Integer> orderMap = new HashMap<>();
        int length = order.length();
        for (int i = 0; i < length; i++)
            orderMap.put(order.charAt(i), i);
        int size = s.length();
        Character[] sChars = new Character[size];
        for (int i = 0; i < size; i++)
            sChars[i] = s.charAt(i);
        Arrays.sort(sChars, Comparator.comparingInt(o -> orderMap.getOrDefault(o, order.length())));
        StringBuilder sb = new StringBuilder();
        for (char c : sChars)
            sb.append(c);
        return sb.toString();
    }

    /*
     * Revision Note — Custom Sort String (Medium)
     *
     * Pattern: Sort by custom priority map
     *
     * Key Insight: Build a rank map from order, then sort s using getOrDefault with
     * sentinel order.length() so characters absent from order naturally land at the end.
     *
     * Gotchas:
     * - Must use getOrDefault, not get — chars in s but not in order return null → NPE on unboxing
     * - Arrays.sort requires Character[] (boxed), not char[] — extra allocation
     * - Arrays.toString(charArray) returns "[a, b, c]" not "abc" — use StringBuilder
     * - Alternative: frequency count + linear rebuild is O(n) and avoids boxing entirely
     *
     * Template:
     *   Map<Character,Integer> rank = build from order
     *   sort s by rank.getOrDefault(c, order.length())
     *   return joined string
     */
    public static void main(String[] args) {
        CustomSortString C = new CustomSortString();
        System.out.println("Test 1: " + C.customSortString("cba", "abcd") + " (Expected: cbad)");
        System.out.println("Test 2: " + C.customSortString("bcafg", "abcd") + " (Expected: bcad)");
        System.out.println("Test 3: " + C.customSortString("", "abc") + " (Expected: abc or any permutation)");
        System.out.println("Test 4: " + C.customSortString("xyz", "abc") + " (Expected: abc or any permutation)");
        System.out.println("Test 5: " + C.customSortString("z", "zzz") + " (Expected: zzz)");
    }
}
