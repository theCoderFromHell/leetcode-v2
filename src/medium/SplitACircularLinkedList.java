package medium;

import common.ListNode;

// https://leetcode.com/problems/split-a-circular-linked-list/
public class SplitACircularLinkedList {
    public ListNode[] splitCircularLinkedList(ListNode list) {
        int size = getSize(list);
        int firstSize = (size+1)/2;
        ListNode[] result = split(list, firstSize);
        return result;
    }

    private ListNode[] split(ListNode head, int firstSize) {
        int count = 1;
        ListNode[] result = new ListNode[2];
        result[0] = head;
        ListNode curr = head;
        while (count < firstSize) {
            curr = curr.next;
            count++;
        }
        result[1] = curr.next;
        curr.next = head;
        curr = result[1];
        while (curr.next != head)
            curr = curr.next;
        curr.next = result[1];
        return result;
    }

    private int getSize(ListNode head) {
        if (head == null)
            return 0;
        ListNode curr = head;
        int count = 1;
        while (curr.next != head) {
            count++;
            curr = curr.next;
        }
        return count;
    }

    /*
     * Revision Note — Split a Circular Linked List (Medium)
     * Pattern: Count the length, walk to the ceil(n/2)-th node, then re-close BOTH circles
     * Key Insight: One cut in a circular list leaves TWO open ends, so it takes TWO next
     *              assignments to repair — not one. Count the rewires, not the cuts.
     *                  firstTail.next  = firstHead    (curr.next = head)
     *                  secondTail.next = secondHead   (the node still pointing at head)
     * Gotchas:
     *   - Setting curr.next = null gives a plain list, not a circular one. The problem wants
     *     both halves circular, so it must be curr.next = head.
     *   - The second rewire is the one that is easy to miss, because the bug is a line that is
     *     ABSENT rather than a line that is wrong. Without it the original tail still points at
     *     the original head, so the second half runs straight back into the first and the two
     *     results SHARE nodes. Test by walking each result and confirming it returns to its own
     *     head, and that the two halves have zero nodes in common by reference.
     *   - Order matters: curr.next = head must happen first, because the walk that finds the
     *     original tail looks for the only remaining node whose next is head.
     *   - ceil(n/2) is (size + 1) / 2. For n=3 the first half gets 2 nodes, not 1.
     *   - Termination is curr.next != head, never curr != null — nothing is ever null here.
     *   - Constraints give n >= 2, so both halves are always non-empty.
     *   - getSize already walks to the tail and discards it; returning it would save the third
     *     partial traversal. Same O(n), purely cosmetic.
     * Alternative: fast/slow in a single pass. Loop while fast.next != list && fast.next.next
     *              != list, then normalise fast to the true tail — it lands on exactly the node
     *              the second rewire needs. O(n) time, O(1) space, fiddlier loop condition.
     */

    // builds a circular list from an array — ListNode.createList produces a normal list
    private static ListNode buildCircular(int[] values) {
        ListNode head = new ListNode(values[0]);
        ListNode curr = head;
        for (int i = 1; i < values.length; i++) {
            curr.next = new ListNode(values[i]);
            curr = curr.next;
        }
        curr.next = head;
        return head;
    }

    // walks a supposedly-circular list with a hard cap, reporting if it is not properly closed
    private static String readCircular(ListNode head, int cap) {
        if (head == null)
            return "null";
        StringBuilder out = new StringBuilder("[");
        ListNode curr = head;
        int count = 0;
        while (count <= cap) {
            if (curr == null)
                return out.append("] NOT CIRCULAR (hit null)").toString();
            if (count > 0)
                out.append(", ");
            out.append(curr.val);
            count++;
            curr = curr.next;
            if (curr == head)
                return out.append("]").toString();
        }
        return out.append("] NOT CIRCULAR (never returned to head)").toString();
    }

    public static void main(String[] args) {
        SplitACircularLinkedList S = new SplitACircularLinkedList();

        // Test 1: LeetCode example 1 — odd length, first half gets ceil(3/2) = 2
        ListNode[] r1 = S.splitCircularLinkedList(buildCircular(new int[]{1, 2, 3}));
        System.out.println("Test 1: " + readCircular(r1[0], 6) + " " + readCircular(r1[1], 6) + " (Expected: [1, 2] [3])");

        // Test 2: LeetCode example 2 — even length, clean halves
        ListNode[] r2 = S.splitCircularLinkedList(buildCircular(new int[]{1, 2, 3, 4}));
        System.out.println("Test 2: " + readCircular(r2[0], 7) + " " + readCircular(r2[1], 7) + " (Expected: [1, 2] [3, 4])");

        // Test 3: minimum size n=2 — each half becomes a single self-pointing node
        ListNode[] r3 = S.splitCircularLinkedList(buildCircular(new int[]{1, 2}));
        System.out.println("Test 3: " + readCircular(r3[0], 5) + " " + readCircular(r3[1], 5) + " (Expected: [1] [2])");

        // Test 4: odd length n=5 — first half gets 3, second gets 2
        ListNode[] r4 = S.splitCircularLinkedList(buildCircular(new int[]{1, 2, 3, 4, 5}));
        System.out.println("Test 4: " + readCircular(r4[0], 8) + " " + readCircular(r4[1], 8) + " (Expected: [1, 2, 3] [4, 5])");

        // Test 5: odd length n=7 — ceil boundary again at a larger size
        ListNode[] r5 = S.splitCircularLinkedList(buildCircular(new int[]{1, 2, 3, 4, 5, 6, 7}));
        System.out.println("Test 5: " + readCircular(r5[0], 10) + " " + readCircular(r5[1], 10) + " (Expected: [1, 2, 3, 4] [5, 6, 7])");

        // Test 6: duplicate values — the split is positional, values are irrelevant
        ListNode[] r6 = S.splitCircularLinkedList(buildCircular(new int[]{7, 7, 7, 7}));
        System.out.println("Test 6: " + readCircular(r6[0], 7) + " " + readCircular(r6[1], 7) + " (Expected: [7, 7] [7, 7])");

        // Test 7: the two halves must share NO nodes — catches a missing second rewire
        ListNode[] r7 = S.splitCircularLinkedList(buildCircular(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}));
        java.util.Set<ListNode> nodes = java.util.Collections.newSetFromMap(new java.util.IdentityHashMap<>());
        ListNode walk = r7[0];
        do {
            nodes.add(walk);
            walk = walk.next;
        } while (walk != r7[0]);
        int shared = 0, secondCount = 0;
        walk = r7[1];
        do {
            secondCount++;
            if (!nodes.add(walk)) shared++;
            walk = walk.next;
        } while (walk != r7[1]);
        System.out.println("Test 7: first=" + (nodes.size() - secondCount) + " second=" + secondCount
                + " shared=" + shared + " (Expected: first=5 second=4 shared=0)");

        // Test 8: large input at the constraint ceiling — 100000 nodes split evenly
        int[] big = new int[100000];
        for (int i = 0; i < big.length; i++)
            big[i] = i + 1;
        ListNode[] r8 = S.splitCircularLinkedList(buildCircular(big));
        int countA = 0, countB = 0;
        ListNode c = r8[0];
        do { countA++; c = c.next; } while (c != r8[0]);
        c = r8[1];
        do { countB++; c = c.next; } while (c != r8[1]);
        System.out.println("Test 8: " + countA + " " + countB + " (Expected: 50000 50000)");
    }
}
