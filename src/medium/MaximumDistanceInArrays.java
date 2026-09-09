package medium;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// https://leetcode.com/problems/maximum-distance-in-arrays/
public class MaximumDistanceInArrays {
    public int maxDistance(List<List<Integer>> arrays) {
        int size = arrays.size();
        int  minSoFar = arrays.getFirst().getFirst();
        int  maxSoFar = arrays.getFirst().getLast();
        int result = 0;
        for (int i = 1; i < size; i++) {
            List<Integer> curr = arrays.get(i);
            int low = curr.getFirst();
            int high = curr.getLast();
            result = Math.max(result,
                        Math.max(
                            Math.abs(high - minSoFar),
                            Math.abs(maxSoFar - low)
            ));
            minSoFar = Math.min(minSoFar, low);
            maxSoFar = Math.max(maxSoFar, high);
        }
        return result;
    }

    public int maxDistanceV2(List<List<Integer>> arrays) {
        List<int[]> streamlined = new ArrayList<>();
        int size = arrays.size();
        for (int i = 0; i < size; i++) {
            streamlined.add(new int[]{arrays.get(i).getFirst(), i});
            streamlined.add(new int[]{arrays.get(i).getLast(), i});
        }
        streamlined.sort(Comparator.comparingInt(a -> a[0]));
        if(streamlined.getFirst()[1] != streamlined.getLast()[1])
            return Math.abs(streamlined.getLast()[0] - streamlined.getFirst()[0]);
        else return Math.max(
                Math.abs(streamlined.getLast()[0] - streamlined.get(1)[0]),
                Math.abs(streamlined.get(streamlined.size() - 2)[0] - streamlined.getFirst()[0])
        );
    }

    /*
     * Revision Note — Maximum Distance in Arrays (Medium)
     * Pattern: Single-pass running min/max — compare each array against everything before it
     * Key Insight: Each array is sorted, so only its first and last matter. Walk left to right
     *              holding the min and max of all PREVIOUS arrays; the "two different arrays"
     *              rule is enforced by updating minSoFar/maxSoFar only AFTER comparing, so an
     *              array is never paired with itself. No sort, no provenance tracking needed.
     * Gotchas:
     *   - Update order is load-bearing. Folding the current array into minSoFar/maxSoFar before
     *     computing result would let it pair with itself and overcount.
     *   - Math.abs is safe here: both operands always come from genuinely different arrays,
     *     so either sign is an achievable distance.
     *   - getFirst()/getLast() are Java 21 SequencedCollection methods. Fine on LeetCode and
     *     in IntelliJ, but the CLI javac must be JDK 21 — the default java 11 on PATH fails with
     *     "cannot find symbol: method getFirst()".
     *   - V2 (sort by value, keep the array index) works too, but its else-branch depends on an
     *     unstated invariant: array k contributes exactly 2 entries, so if both the global min
     *     and max are array k's, they occupy positions 0 and size-1 and get(1)/get(size-2) are
     *     guaranteed to belong to other arrays. Correct, but O(m log m) and harder to justify.
     *   - Constraints give 2 <= m, so arrays.getFirst() is always safe.
     */
    public static void main(String[] args) {
        MaximumDistanceInArrays M = new MaximumDistanceInArrays();

        // Test 1: LeetCode example 1 — max 5 from array 1, min 1 from array 0
        List<List<Integer>> t1 = List.of(List.of(1, 2, 3), List.of(4, 5), List.of(1, 2, 3));
        System.out.println("Test 1: " + M.maxDistance(t1) + " (Expected: 4)");

        // Test 2: LeetCode example 2 — identical singletons
        List<List<Integer>> t2 = List.of(List.of(1), List.of(1));
        System.out.println("Test 2: " + M.maxDistance(t2) + " (Expected: 0)");

        // Test 3: global min and max both live in array 0 — must not pair with itself
        List<List<Integer>> t3 = List.of(List.of(1, 10), List.of(2, 3));
        System.out.println("Test 3: " + M.maxDistance(t3) + " (Expected: 8)");

        // Test 4: same trap with three arrays — 100 pairs with 2, not with its own 1
        List<List<Integer>> t4 = List.of(List.of(1, 100), List.of(50, 60), List.of(2, 3));
        System.out.println("Test 4: " + M.maxDistance(t4) + " (Expected: 98)");

        // Test 5: singleton array — first and last are the same element
        List<List<Integer>> t5 = List.of(List.of(5), List.of(1, 10));
        System.out.println("Test 5: " + M.maxDistance(t5) + " (Expected: 5)");

        // Test 6: identical arrays — ties at both ends
        List<List<Integer>> t6 = List.of(List.of(1, 10), List.of(1, 10));
        System.out.println("Test 6: " + M.maxDistance(t6) + " (Expected: 9)");

        // Test 7: all negative values
        List<List<Integer>> t7 = List.of(List.of(-10, -5), List.of(-3, -1));
        System.out.println("Test 7: " + M.maxDistance(t7) + " (Expected: 9)");

        // Test 8: min and max in same array, spanning zero
        List<List<Integer>> t8 = List.of(List.of(-100, 100), List.of(-1, 1));
        System.out.println("Test 8: " + M.maxDistance(t8) + " (Expected: 101)");

        // Test 9: every value identical across arrays
        List<List<Integer>> t9 = List.of(List.of(1, 1, 1), List.of(1, 1));
        System.out.println("Test 9: " + M.maxDistance(t9) + " (Expected: 0)");

        // Test 10: V2 must agree with the single-pass version
        System.out.println("Test 10: " + M.maxDistanceV2(t4) + " (Expected: 98)");
    }
}
