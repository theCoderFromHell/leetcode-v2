package medium;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.TreeSet;

// https://leetcode.com/problems/design-a-number-container-system/
public class DesignANumberContainerSystem {

    static class NumberContainers {
        HashMap<Integer, TreeSet<Integer>> numbers;
        HashMap<Integer, Integer> indices;
        public NumberContainers() {
            this.numbers = new HashMap<>();
            this.indices = new HashMap<>();
        }

        public void change(int index, int number) {
            if (indices.containsKey(index)) {
                int currNumber = indices.get(index);
                numbers.get(currNumber).remove(index);
                if (numbers.get(currNumber).isEmpty())
                    numbers.remove(currNumber);
            }
            indices.put(index, number);
            numbers.computeIfAbsent(number, k -> new TreeSet<>()).add(index);
        }

        public int find(int number) {
            if (!numbers.containsKey(number))
                return -1;
            return numbers.get(number).first();
        }
    }

    /*
     * V2 — lazy deletion. change() never removes anything: it records the index's new number and
     * pushes the index onto that number's min-heap. find() pops tops that have gone stale (the
     * index now holds some other number) until the top is valid, then peeks.
     */
    static class NumberContainersV2 {
        HashMap<Integer, PriorityQueue<Integer>> numbers;
        HashMap<Integer, Integer> indices;
        public NumberContainersV2() {
            this.numbers = new HashMap<>();
            this.indices = new HashMap<>();
        }

        public void change(int index, int number) {
            indices.put(index, number);
            numbers.computeIfAbsent(number, k -> new PriorityQueue<>()).add(index);
        }

        public int find(int number) {
            PriorityQueue<Integer> heap = numbers.get(number);
            if (heap == null)
                return -1;
            while (!heap.isEmpty() && indices.get(heap.peek()) != number)
                heap.poll();
            return heap.isEmpty() ? -1 : heap.peek();
        }
    }

    /*
     * Revision Note — Design a Number Container System (Medium)
     * Pattern: two maps kept in sync — index -> current number (the source of truth) and
     *          number -> TreeSet of its indices (sorted, so the smallest is first()).
     * Key Insight: change() is a REPLACE, so it must unhook the index from its old number before
     *              hooking it to the new one. Only the index -> number map can tell you what the
     *              old number was.
     * Gotchas:
     *   - Without the unhook, find(10) after change(1, 20) still returns 1 (Test 1.3).
     *   - Drop a number's set once it empties, or check isEmpty() in find(): first() on an empty
     *     TreeSet throws NoSuchElementException (Tests 2.2, 2.5).
     *   - Setting an index to the number it already holds must leave it findable: remove, then
     *     re-add, keeps it (Test 2.1).
     *   - Indices and numbers reach 10^9, so both sides are HashMaps, never arrays (Test 2.8).
     *   - map.put returns the previous value (or null), so one call replaces containsKey + get + put.
     * Complexity: change O(log k), find O(log k) — first() walks the tree's left spine — where k is
     *             the number of indices holding that number. O(n) space for n distinct indices.
     * V2 — lazy deletion (NumberContainersV2): number -> min-heap of indices, and change() only
     *   pushes. find() pops while the top's CURRENT number (from index -> number) isn't this one,
     *   then peeks. Nothing is ever removed from the middle of a structure — the reason to prefer
     *   it over PriorityQueue.remove(Object), which is O(n).
     *   - A heap can hold stale entries AND duplicates (index 3 in Test 4.4 is pushed onto 5's heap
     *     twice). Both are harmless, because every top is checked against index -> number first.
     *   - indices.get(...) != number is safe only because number is a primitive int, so the
     *     Integer unboxes. Integer != Integer compares references and breaks above 127 (2764's
     *     bug); Test 4.8 uses 10^9.
     *   - Complexity: change O(log c), find amortised O(log c) — each pushed entry is popped at
     *     most once. Space O(c) for c change() calls, not O(n): stale entries stay until popped.
     * Template:
     *   change(i, x): old = idx.put(i, x); if old != null: sets[old].remove(i), drop it if empty
     *                 sets[x].add(i)
     *   find(x):      sets[x] missing ? -1 : sets[x].first()
     */
    public static void main(String[] args) {
        NumberContainers N = new NumberContainers();

        // Test 1: LeetCode example 1, replayed call by call on this instance
        System.out.println("Test 1.1: " + N.find(10) + " (Expected: -1)");
        N.change(2, 10);
        N.change(1, 10);
        N.change(3, 10);
        N.change(5, 10);
        System.out.println("Test 1.2: " + N.find(10) + " (Expected: 1)");
        N.change(1, 20);
        System.out.println("Test 1.3: " + N.find(10) + " (Expected: 2)");

        // Test 2: a fresh instance
        N = new NumberContainers();

        // Test 2.1-2.3: same index set to the same number twice — the second change unhooks and re-adds 4,
        // so it stays findable — then to a different number, which must empty 7's set
        // (find(7) is -1, not an exception from first() on an empty set)
        N.change(4, 7);
        N.change(4, 7);
        System.out.println("Test 2.1: " + N.find(7) + " (Expected: 4)");
        N.change(4, 8);
        System.out.println("Test 2.2: " + N.find(7) + " (Expected: -1)");
        System.out.println("Test 2.3: " + N.find(8) + " (Expected: 4)");

        // Test 2.4-2.5: index changed away and back — each change unhooks it from the previous number,
        // so 6 ends up with no indices at all
        N.change(3, 5);
        N.change(3, 6);
        N.change(3, 5);
        System.out.println("Test 2.4: " + N.find(5) + " (Expected: 3)");
        System.out.println("Test 2.5: " + N.find(6) + " (Expected: -1)");

        // Test 2.6-2.7: the smallest index of a number moves to another number — 1's next-smallest index (9)
        // takes over, and 2 now answers for 3
        N.change(9, 1);
        N.change(2, 1);
        N.change(2, 3);
        System.out.println("Test 2.6: " + N.find(1) + " (Expected: 9)");
        System.out.println("Test 2.7: " + N.find(3) + " (Expected: 2)");

        // Test 2.8: index and number both at 10^9 — they're keys, not array positions, so both maps are HashMaps
        N.change(1000000000, 1000000000);
        System.out.println("Test 2.8: " + N.find(1000000000) + " (Expected: 1000000000)");

        // ---- V2: lazy deletion with a min-heap per number ----
        NumberContainersV2 N2 = new NumberContainersV2();

        // Test 3: V2 on LeetCode example 1 — after change(1, 20), index 1 is a stale top of 10's heap
        System.out.println("Test 3.1: " + N2.find(10) + " (Expected: -1)");
        N2.change(2, 10);
        N2.change(1, 10);
        N2.change(3, 10);
        N2.change(5, 10);
        System.out.println("Test 3.2: " + N2.find(10) + " (Expected: 1)");
        N2.change(1, 20);
        System.out.println("Test 3.3: " + N2.find(10) + " (Expected: 2)");

        // Test 4: V2 on Test 2's sequence, a fresh instance
        N2 = new NumberContainersV2();

        // Test 4.1-4.3: index 4 pushed onto 7's heap twice; once it moves to 8, both copies are stale
        N2.change(4, 7);
        N2.change(4, 7);
        System.out.println("Test 4.1: " + N2.find(7) + " (Expected: 4)");
        N2.change(4, 8);
        System.out.println("Test 4.2: " + N2.find(7) + " (Expected: -1)");
        System.out.println("Test 4.3: " + N2.find(8) + " (Expected: 4)");

        // Test 4.4-4.5: index 3 changed away and back — 5's heap holds 3 twice (both valid again),
        // and 6's heap holds a stale 3
        N2.change(3, 5);
        N2.change(3, 6);
        N2.change(3, 5);
        System.out.println("Test 4.4: " + N2.find(5) + " (Expected: 3)");
        System.out.println("Test 4.5: " + N2.find(6) + " (Expected: -1)");

        // Test 4.6-4.7: the stale 2 sits on top of 1's heap and must be popped to reach 9
        N2.change(9, 1);
        N2.change(2, 1);
        N2.change(2, 3);
        System.out.println("Test 4.6: " + N2.find(1) + " (Expected: 9)");
        System.out.println("Test 4.7: " + N2.find(3) + " (Expected: 2)");

        // Test 4.8: index and number at 10^9 — far outside the Integer cache, so the stale check
        // must compare as int, not Integer != Integer
        N2.change(1000000000, 1000000000);
        System.out.println("Test 4.8: " + N2.find(1000000000) + " (Expected: 1000000000)");
    }
}
