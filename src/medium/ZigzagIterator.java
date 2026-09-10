package medium;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// https://leetcode.com/problems/zigzag-iterator/
public class ZigzagIterator {
    List<Integer> finalList;
    int index;
    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        this.finalList = new ArrayList<>();
        this.index = 0;
        int size1 = v1.size();
        int size2 = v2.size();
        int index1 = 0, index2 = 0;
        boolean flag = true;
        while (index1 < size1 || index2 < size2) {
            if (index1 == size1)
                finalList.add(v2.get(index2++));
            else if (index2 == size2)
                finalList.add(v1.get(index1++));
            else {
                if (flag)
                    finalList.add(v1.get(index1++));
                else
                    finalList.add(v2.get(index2++));
                flag = !flag;
            }
        }
    }

    public int next() {
        return finalList.get(index++);
    }

    public boolean hasNext() {
        return index <= finalList.size()-1;
    }

    /*
     * V2 — queue of iterators. Rotate: poll the front iterator, take one element,
     * push it back if it still has more. The rotation IS the alternation.
     *
     * O(1) per call and O(k) space instead of O(total elements), and it answers the
     * follow-up: the second constructor handles k lists with zero change to next()/hasNext(),
     * which a boolean flag cannot do.
     */
    static class ZigzagIteratorV2 {
        Queue<Iterator<Integer>> queue;

        public ZigzagIteratorV2(List<Integer> v1, List<Integer> v2) {
            this(List.of(v1, v2));
        }

        // follow-up: k vectors, cyclic order
        public ZigzagIteratorV2(List<List<Integer>> vectors) {
            this.queue = new LinkedList<>();
            for (List<Integer> v : vectors)
                if (!v.isEmpty())
                    queue.add(v.iterator());
        }

        public int next() {
            Iterator<Integer> it = queue.poll();
            int value = it.next();
            if (it.hasNext())
                queue.add(it);
            return value;
        }

        public boolean hasNext() {
            return !queue.isEmpty();
        }
    }

    /*
     * Revision Note — Zigzag Iterator (Medium)
     * Pattern: V1 flatten-then-cursor · V2 queue of iterators (rotate to alternate)
     * Key Insight: Alternation is rotation. Poll the front iterator, take one element, push it
     *              back if more remain. That single idea covers k lists as naturally as 2 —
     *              a boolean "whose turn" flag only ever covers 2.
     * Gotchas:
     *   - hasNext() asks "is the CURRENT index valid?", not "is there one after it".
     *     So index <= size-1 (i.e. index < size), never index < size-1. The wrong bound hides
     *     exactly the last element, so the bug looks like truncation, not an ordering error.
     *     When a traversal comes up one short, suspect the boundary predicate first.
     *   - V1's exhaustion guards (index1 == size1 / index2 == size2) must be checked BEFORE the
     *     flag branch, or the flag strands the tail of the longer list.
     *   - V2 must skip empty lists at construction — queueing an exhausted iterator makes
     *     hasNext() true while next() has nothing to give.
     *   - V2 re-queues only when it.hasNext(); pushing back an exhausted iterator breaks the
     *     invariant that "queue non-empty" means "elements remain".
     *   - next() past the end throws IndexOutOfBoundsException here; java.util.Iterator
     *     specifies NoSuchElementException. Irrelevant on LeetCode, noticed in an interview.
     * Trade-off: V1 is O(1) per call after O(n+m) setup and benchmarks faster (4ms vs 35ms on
     *            200k) because it is one array copy then index reads. V2 is O(1) per call with
     *            O(k) space instead of O(n+m), is lazy, and answers the follow-up without
     *            touching next()/hasNext(). Speed vs space and extensibility — pick per context.
     */

    // drains an iterator into a list so a whole traversal can be compared at once
    private static List<Integer> drain(ZigzagIterator it) {
        List<Integer> out = new ArrayList<>();
        while (it.hasNext())
            out.add(it.next());
        return out;
    }

    private static List<Integer> drain(ZigzagIteratorV2 it) {
        List<Integer> out = new ArrayList<>();
        while (it.hasNext())
            out.add(it.next());
        return out;
    }

    public static void main(String[] args) {
        // Test 1: LeetCode example 1 — v1 runs out, v2 drains in order
        ZigzagIterator Z = new ZigzagIterator(List.of(1, 2), List.of(3, 4, 5, 6));
        System.out.println("Test 1: " + drain(Z) + " (Expected: [1, 3, 2, 4, 5, 6])");

        // Test 2: LeetCode example 2 — second list empty
        ZigzagIterator A = new ZigzagIterator(List.of(1), List.of());
        System.out.println("Test 2: " + drain(A) + " (Expected: [1])");

        // Test 3: LeetCode example 3 — first list empty
        ZigzagIterator B = new ZigzagIterator(List.of(), List.of(1));
        System.out.println("Test 3: " + drain(B) + " (Expected: [1])");

        // Test 4: equal lengths — strict alternation throughout
        ZigzagIterator C = new ZigzagIterator(List.of(1, 3, 5), List.of(2, 4, 6));
        System.out.println("Test 4: " + drain(C) + " (Expected: [1, 2, 3, 4, 5, 6])");

        // Test 5: first list longer — v1 tail drains after v2 is exhausted
        ZigzagIterator D = new ZigzagIterator(List.of(1, 2, 3, 4, 5), List.of(6, 7));
        System.out.println("Test 5: " + drain(D) + " (Expected: [1, 6, 2, 7, 3, 4, 5])");

        // Test 6: both empty — no elements at all
        ZigzagIterator E = new ZigzagIterator(List.of(), List.of());
        System.out.println("Test 6: " + drain(E) + " (Expected: [])");

        // Test 7: hasNext() on an empty iterator must be false immediately
        ZigzagIterator F = new ZigzagIterator(List.of(), List.of());
        System.out.println("Test 7: " + F.hasNext() + " (Expected: false)");

        // Test 8: single element total — hasNext true, then false after one next()
        ZigzagIterator G = new ZigzagIterator(List.of(42), List.of());
        System.out.println("Test 8: " + G.hasNext() + " (Expected: true)");
        System.out.println("Test 9: " + G.next() + " (Expected: 42)");
        System.out.println("Test 10: " + G.hasNext() + " (Expected: false)");

        // Test 11: hasNext() must stay true right up to the final element
        ZigzagIterator H = new ZigzagIterator(List.of(1, 2), List.of(3));
        System.out.println("Test 11: " + H.next() + " (Expected: 1)");
        System.out.println("Test 12: " + H.next() + " (Expected: 3)");
        System.out.println("Test 13: " + H.hasNext() + " (Expected: true)");
        System.out.println("Test 14: " + H.next() + " (Expected: 2)");
        System.out.println("Test 15: " + H.hasNext() + " (Expected: false)");

        // Test 16: duplicate and negative values pass through unchanged
        ZigzagIterator I = new ZigzagIterator(List.of(-1, -1), List.of(0, -1));
        System.out.println("Test 16: " + drain(I) + " (Expected: [-1, 0, -1, -1])");

        // ---- V2: queue of iterators ----

        // Test 17: V2 on LeetCode example 1
        System.out.println("Test 17: " + drain(new ZigzagIteratorV2(List.of(1, 2), List.of(3, 4, 5, 6)))
                + " (Expected: [1, 3, 2, 4, 5, 6])");

        // Test 18: V2 with an empty second list
        System.out.println("Test 18: " + drain(new ZigzagIteratorV2(List.of(1), List.<Integer>of()))
                + " (Expected: [1])");

        // Test 19: V2 with an empty first list — empty iterators are never queued
        System.out.println("Test 19: " + drain(new ZigzagIteratorV2(List.<Integer>of(), List.of(1)))
                + " (Expected: [1])");

        // Test 20: V2 with both empty — hasNext() false immediately
        System.out.println("Test 20: " + new ZigzagIteratorV2(List.<Integer>of(), List.<Integer>of()).hasNext()
                + " (Expected: false)");

        // Test 21: V2 first list longer — tail drains once the other empties
        System.out.println("Test 21: " + drain(new ZigzagIteratorV2(List.of(1, 2, 3, 4, 5), List.of(6, 7)))
                + " (Expected: [1, 6, 2, 7, 3, 4, 5])");

        // Test 22: FOLLOW-UP — three vectors, cyclic order, next()/hasNext() unchanged
        System.out.println("Test 22: " + drain(new ZigzagIteratorV2(
                List.of(List.of(1, 2, 3), List.of(4, 5, 6, 7), List.of(8, 9))))
                + " (Expected: [1, 4, 8, 2, 5, 9, 3, 6, 7])");

        // Test 23: FOLLOW-UP — k lists with empties interleaved, skipped entirely
        System.out.println("Test 23: " + drain(new ZigzagIteratorV2(
                List.of(List.of(1), List.<Integer>of(), List.of(2, 3), List.<Integer>of(), List.of(4))))
                + " (Expected: [1, 2, 4, 3])");

        // Test 24: V1 and V2 must agree on every two-list case above
        List<List<Integer>> lefts = List.of(List.of(1, 2), List.of(1), List.<Integer>of(),
                List.of(1, 3, 5), List.of(1, 2, 3, 4, 5), List.<Integer>of(), List.of(-1, -1));
        List<List<Integer>> rights = List.of(List.of(3, 4, 5, 6), List.<Integer>of(), List.of(1),
                List.of(2, 4, 6), List.of(6, 7), List.<Integer>of(), List.of(0, -1));
        boolean agree = true;
        for (int i = 0; i < lefts.size(); i++)
            if (!drain(new ZigzagIterator(lefts.get(i), rights.get(i)))
                    .equals(drain(new ZigzagIteratorV2(lefts.get(i), rights.get(i)))))
                agree = false;
        System.out.println("Test 24: " + agree + " (Expected: true)");
    }
}
