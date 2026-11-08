package contests.weekly_527;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/find-mismatched-segments-between-two-strings/
public class FindMismatchedSegmentsBetweenTwoStrings {
    public List<List<String>> findMismatchedSegments(String s1, String s2) {
        int size = s1.length();
        int index = 0;
        List<List<String>> result = new ArrayList<>();
        while (index < size) {
            int currIndex = index;
            while (index < size && s1.charAt(index) != s2.charAt(index))
                index++;
            if (index > currIndex) {
                List<String> segments = new ArrayList<>();
                segments.add(s1.substring(currIndex, index));
                segments.add(s2.substring(currIndex, index));
                result.add(segments);
            }
            index++;
        }
        return result;
    }

    /*
     * Revision Note — Find Mismatched Segments Between Two Strings (Medium, Weekly Contest 527)
     *
     * Pattern: run-length scan. Group consecutive indices by a predicate (here s1[i] != s2[i]) and
     * emit each maximal run.
     *
     * Key insight: from each start, extend while the characters differ. If the run is non-empty,
     * emit [s1.substring, s2.substring]. Then step over the one equal character that stopped it.
     * Every index is looked at once.
     *
     * Gotchas:
     *  - A run that reaches the last index has no equal character after it to "close" it. The
     *    inner loop's index < size bound handles this, so emit after the loop, not on a match (Test 4, Test 5).
     *  - The index++ after the inner loop skips the equal character, or steps past the end. Without
     *    it, a match makes the outer loop spin forever.
     *  - Runs of length 1 separated by single matches (Test 6) check that the skip is exactly one.
     *
     * Complexity: O(n) time, O(n) for the output substrings.
     *
     * Template:
     *   i = 0
     *   while i < n:
     *       start = i; while i < n && pred(i): i++
     *       if i > start: emit(start, i)
     *       i++
     */
    public static void main(String[] args) {
        FindMismatchedSegmentsBetweenTwoStrings F = new FindMismatchedSegmentsBetweenTwoStrings();

        // Test 1: LeetCode example 1 — the second run ends at the last index
        System.out.println("Test 1: " + F.findMismatchedSegments("earth", "empty") + " (Expected: [[ar, mp], [h, y]])");

        // Test 2: LeetCode example 2 — a run starting at index 0
        System.out.println("Test 2: " + F.findMismatchedSegments("action", "nation") + " (Expected: [[ac, na]])");

        // Test 3: LeetCode example 3 — identical strings, empty result
        System.out.println("Test 3: " + F.findMismatchedSegments("hot", "hot") + " (Expected: [])");

        // Test 4: every index differs — one run covering the whole string
        System.out.println("Test 4: " + F.findMismatchedSegments("abc", "xyz") + " (Expected: [[abc, xyz]])");

        // Test 5: mismatches only at both ends
        System.out.println("Test 5: " + F.findMismatchedSegments("abcde", "xbcdy") + " (Expected: [[a, x], [e, y]])");

        // Test 6: alternating match / mismatch — length-1 runs separated by a single equal character
        System.out.println("Test 6: " + F.findMismatchedSegments("abab", "aaaa") + " (Expected: [[b, a], [b, a]])");
    }
}
