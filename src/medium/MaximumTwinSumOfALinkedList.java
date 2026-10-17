package medium;

import common.ListNode;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/maximum-twin-sum-of-a-linked-list/
public class MaximumTwinSumOfALinkedList {
    public int pairSum(ListNode head) {
        if (head == null || head.next == null)
            return -1;
        List<Integer> original = new ArrayList<>();
        List<Integer> reverse = new ArrayList<>();
        original = sum(head, original, true);
        reverse = sum(head, reverse, false);
        int N = original.size();
        int result = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            result = Math.max(result, original.get(i) + reverse.get(i));
        }
        return result;
    }

    private List<Integer> sum(ListNode head, List<Integer> list, boolean original) {
        if (head == null)
            return list;
        if (original) {
            list.add(head.val);
             return sum(head.next, list, original);
        } else {
            sum(head.next, list, original);
            list.add(head.val);
            return list;
        }
    }

    /*
     * Revision Note - Maximum Twin Sum of a Linked List (Medium)
     *
     * Pattern: pair node i with node n-1-i. Here: collect values front-to-back (pre-order
     * recursion) and back-to-front (add AFTER the recursive call returns), then reverse.get(i) is
     * the twin of original.get(i).
     *
     * Key Insight: the twin of i is the i-th node counted from the END, so any way of reading the
     * list backwards lines the twins up - post-order recursion, a stack, or reversing the second
     * half in place.
     *
     * Gotchas:
     * - The loop runs all n indices, not n/2: each pair is just seen twice (i and n-1-i give the
     *   same sum), which is harmless.
     * - Recursion depth is n (up to 1e5) for each of the two passes - accepted on LeetCode, but
     *   a default JVM stack can overflow on long lists. The in-place version avoids it.
     * - n is always even and >= 2 per the constraints, so the -1 guard never fires on valid input.
     * - Sum fits int: values <= 1e5, so a twin sum <= 2e5.
     *
     * Complexity: O(n) time, O(n) space (two lists + O(n) recursion stack).
     *
     * O(1)-space alternative (Reorder List 143's first two passes):
     *   slow/fast -> middle; reverse the second half; walk head and the reversed half together,
     *   best = max(best, a.val + b.val)
     *
     * Template:
     *   front = values in order; back = values in reverse (post-order add)
     *   for i in 0..n/2-1: best = max(best, front[i] + back[i])
     */

    public static void main(String[] args) {
        MaximumTwinSumOfALinkedList M = new MaximumTwinSumOfALinkedList();

        System.out.println("Test 1: " + M.pairSum(ListNode.createList(new int[]{5, 4, 2, 1})) + " (Expected: 6)");
        System.out.println("Test 2: " + M.pairSum(ListNode.createList(new int[]{4, 2, 2, 3})) + " (Expected: 7)");
        System.out.println("Test 3: " + M.pairSum(ListNode.createList(new int[]{1, 100000})) + " (Expected: 100001)");        // n = 2: the only pair
        System.out.println("Test 4: " + M.pairSum(ListNode.createList(new int[]{1, 1, 9, 9, 1, 1})) + " (Expected: 18)");     // best pair is the middle one
        System.out.println("Test 5: " + M.pairSum(ListNode.createList(new int[]{9, 1, 1, 1, 1, 9})) + " (Expected: 18)");     // best pair is the outermost
        System.out.println("Test 6: " + M.pairSum(ListNode.createList(new int[]{9, 9, 1, 1})) + " (Expected: 10)");           // adjacent big values aren't twins
    }
}
