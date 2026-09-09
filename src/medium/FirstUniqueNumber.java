package medium;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

// https://leetcode.com/problems/first-unique-number/
public class FirstUniqueNumber {
    static class FirstUnique {
        HashMap<Integer,Integer> count;
        Deque<Integer> DQ;
        public FirstUnique(int[] nums) {
            int size = nums.length;
            this.count = new HashMap<>();
            this.DQ = new ArrayDeque<>();
            for (int i = 0; i < size; i++) {
                DQ.add(nums[i]);
                count.put(nums[i], count.getOrDefault(nums[i], 0) + 1);
            }
        }

        public int showFirstUnique() {
            while (!DQ.isEmpty() && count.get(DQ.peek()) > 1)
                DQ.poll();
            if (DQ.isEmpty())
                return -1;
            return DQ.peek();
        }

        public void add(int value) {
            DQ.add(value);
            count.put(value, count.getOrDefault(value, 0) + 1);
        }
    }

    /*
     * Revision Note — First Unique Number (Medium)
     * Pattern: Queue + frequency map with lazy deletion
     * Key Insight: Keep every value in insertion order in a deque and its count in a map.
     *              On query, drain from the head while the head's count > 1; whatever survives
     *              at the front is the first unique. Never rebuild or re-scan.
     * Gotchas:
     *   - Lazy deletion is only valid because counts are MONOTONIC — add() only increments, so a
     *     value that reaches 2 can never return to 1. Evicting it is permanent and safe. If the
     *     stale condition could reverse, lazy deletion would be wrong and eager updates needed.
     *   - The drain loop must guard emptiness FIRST: while (!DQ.isEmpty() && count.get(DQ.peek()) > 1).
     *     Checking isEmpty() after the loop throws NPE — peek() returns null on an empty deque and
     *     count.get(null) returns null, which NPEs on unboxing to int.
     *   - showFirstUnique is amortized O(1), not O(1) worst case: a single call can drain many
     *     elements, but each element is polled at most once over the object's whole lifetime.
     *   - Duplicates stay in the deque until they reach the head; that is harmless but means space
     *     is O(total adds), not O(distinct values).
     *   - Values reach 10^8, so no counting array — the map is required.
     * Alternative: two sets (LinkedHashSet unique + HashSet seen), moving a value out of `unique`
     *              on its second sighting. True O(1) worst case and a non-mutating query.
     */
    public static void main(String[] args) {
        // Test 1: LeetCode example 1 — uniqueness shifts as duplicates arrive
        FirstUnique F = new FirstUnique(new int[]{2, 3, 5});
        System.out.println("Test 1: " + F.showFirstUnique() + " (Expected: 2)");
        F.add(5);
        System.out.println("Test 2: " + F.showFirstUnique() + " (Expected: 2)");
        F.add(2);
        System.out.println("Test 3: " + F.showFirstUnique() + " (Expected: 3)");
        F.add(3);
        System.out.println("Test 4: " + F.showFirstUnique() + " (Expected: -1)");

        // Test 5: LeetCode example 2 — every initial value is a duplicate
        FirstUnique G = new FirstUnique(new int[]{7, 7, 7, 7, 7, 7});
        System.out.println("Test 5: " + G.showFirstUnique() + " (Expected: -1)");
        G.add(7);
        G.add(3);
        G.add(3);
        G.add(7);
        G.add(17);
        System.out.println("Test 6: " + G.showFirstUnique() + " (Expected: 17)");

        // Test 7: LeetCode example 3 — single element, then duplicated away
        FirstUnique H = new FirstUnique(new int[]{809});
        System.out.println("Test 7: " + H.showFirstUnique() + " (Expected: 809)");
        H.add(809);
        System.out.println("Test 8: " + H.showFirstUnique() + " (Expected: -1)");

        // Test 9: repeated calls must be idempotent — no state corruption
        FirstUnique I = new FirstUnique(new int[]{1, 2, 1});
        System.out.println("Test 9: " + I.showFirstUnique() + " (Expected: 2)");
        System.out.println("Test 10: " + I.showFirstUnique() + " (Expected: 2)");

        // Test 11: a value can become unique-first only after earlier ones duplicate
        FirstUnique J = new FirstUnique(new int[]{5, 5, 6});
        System.out.println("Test 11: " + J.showFirstUnique() + " (Expected: 6)");
        J.add(6);
        System.out.println("Test 12: " + J.showFirstUnique() + " (Expected: -1)");
        J.add(4);
        System.out.println("Test 13: " + J.showFirstUnique() + " (Expected: 4)");
    }
}
