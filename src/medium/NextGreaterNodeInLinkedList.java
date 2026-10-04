package medium;

import common.ListNode;

import java.util.*;

// https://leetcode.com/problems/next-greater-node-in-linked-list/
public class NextGreaterNodeInLinkedList {
    public int[] nextLargerNodes(ListNode head) {
        List<Integer> values = new ArrayList<>();
        int size = countNodes(head, values);
        int[] result = new int[size];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < size; i++) {
            while (!stack.isEmpty() && values.get(stack.peek()) < values.get(i)) {
                int top = stack.pop();
                result[top] = values.get(i);
            }
            stack.push(i);
        }
        return result;
    }

    private int countNodes(ListNode head, List<Integer> values) {
        int count = 0;
        while (head != null) {
            count++;
            values.add(head.val);
            head = head.next;
        }
        return count;
    }

    /*
     * Revision Note — Next Greater Node In Linked List (Medium)
     *
     * Pattern: Monotonic stack of INDICES over a materialised value array
     *
     * Key Insight: Walk left to right holding a stack of positions still waiting for an
     * answer, kept decreasing in value from bottom to top. Each new value resolves every
     * stacked position it beats — one arrival can settle several, which is where the
     * amortised O(n) comes from. Whatever is still stacked at the end has no greater value
     * ahead, and Java's default 0 is already the required sentinel, so nothing to write.
     *
     * Gotchas:
     * - Stack holds INDICES, not nodes. Holding ListNode forces a Map<ListNode,Integer> to
     *   find where to write, and that map is only correct because ListNode defines neither
     *   equals nor hashCode (identity hashing keeps equal-valued nodes distinct). Add a
     *   value-based equals later and every duplicate silently collides. Indices are immune
     * - Strict <, not <=. "Strictly larger" means equal values must NOT resolve each other;
     *   [2,2,3] must give [3,3,0], not [2,3,0]
     * - A linked list has no random access, so materialise the values first — one pass to
     *   collect, one pass for the stack. Trying to run the stack over the list directly is
     *   what pushes you toward the node-keyed map
     * - ArrayDeque over Stack: Stack extends Vector and is synchronised
     *
     * Template:
     *   collect node values into an array/list
     *   for i in 0..n-1:
     *     while stack nonempty and values[stack.peek()] < values[i]:
     *       result[stack.pop()] = values[i]
     *     stack.push(i)
     *   return result            // unresolved positions keep 0
     */
    // O(n^2) reference, used only to cross-check the stack version
    private static int[] bruteForce(int[] vals) {
        int[] out = new int[vals.length];
        for (int i = 0; i < vals.length; i++)
            for (int j = i + 1; j < vals.length; j++)
                if (vals[j] > vals[i]) { out[i] = vals[j]; break; }
        return out;
    }

    public static void main(String[] args) {
        NextGreaterNodeInLinkedList N = new NextGreaterNodeInLinkedList();

        System.out.println("Test 1: " + Arrays.toString(N.nextLargerNodes(ListNode.createList(new int[]{2, 1, 5})))       + " (Expected: [5, 5, 0])");
        System.out.println("Test 2: " + Arrays.toString(N.nextLargerNodes(ListNode.createList(new int[]{2, 7, 4, 3, 5}))) + " (Expected: [7, 0, 5, 5, 0])");
        System.out.println("Test 3: " + Arrays.toString(N.nextLargerNodes(ListNode.createList(new int[]{1})))             + " (Expected: [0])");           // single node
        System.out.println("Test 4: " + Arrays.toString(N.nextLargerNodes(ListNode.createList(new int[]{1, 2, 3, 4})))    + " (Expected: [2, 3, 4, 0])");  // ascending
        System.out.println("Test 5: " + Arrays.toString(N.nextLargerNodes(ListNode.createList(new int[]{4, 3, 2, 1})))    + " (Expected: [0, 0, 0, 0])");  // descending
        System.out.println("Test 6: " + Arrays.toString(N.nextLargerNodes(ListNode.createList(new int[]{2, 2, 3})))       + " (Expected: [3, 3, 0])");     // duplicates need strict >

        // cross-check against brute force on random input, heavy on duplicates
        boolean agree = true;
        Random rnd = new Random(3);
        for (int t = 0; t < 500; t++) {
            int[] vals = new int[1 + rnd.nextInt(30)];
            for (int i = 0; i < vals.length; i++) vals[i] = 1 + rnd.nextInt(6);
            if (!Arrays.equals(N.nextLargerNodes(ListNode.createList(vals)), bruteForce(vals))) agree = false;
        }
        System.out.println("Test 7: " + agree + " (Expected: true)  — matches brute force, 500 random");
    }
}
