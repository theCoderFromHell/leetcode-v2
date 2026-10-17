package medium;

import common.ListNode;

// https://leetcode.com/problems/reverse-nodes-in-even-length-groups/
public class ReverseNodesInEvenLengthGroups {
    public ListNode reverseEvenLengthGroups(ListNode head) {
        int currGroupLength = 1;
        ListNode curr = head;
        ListNode previous = null;
        int count = countNodes(curr);
        while (curr != null && curr.next != null) {
            currGroupLength = Math.min(currGroupLength, count);
            if (currGroupLength % 2 == 1) {
                for (int i = 0; i < currGroupLength && curr != null; i++) {
                    previous = curr;
                    curr = curr.next;
                }
            } else {
                previous.next = reverse(previous.next, currGroupLength);
                curr = previous.next;
                for (int i = 0; i < currGroupLength && curr != null; i++) {
                    previous = curr;
                    curr = curr.next;
                }
            }
            count -= currGroupLength;
            currGroupLength++;
        }
        return head;
    }

    private int countNodes(ListNode head) {
        int count = 0;
        while (head != null) {
            count++;
            head = head.next;
        }
        return count;
    }

    private ListNode reverse(ListNode head, int currGroupLength) {
        if (head == null)
            return null;
        ListNode curr = head;
        ListNode previous = null, next;
        while (currGroupLength > 0 && curr != null) {
            next = curr.next;
            curr.next = previous;
            previous = curr;
            curr = next;
            currGroupLength--;
        }
        head.next = curr;
        return previous;
    }

    /*
     * Revision Note - Reverse Nodes in Even Length Groups (Medium)
     *
     * Pattern: in-place reversal of consecutive segments (25 Reverse Nodes in k-Group / 92 Reverse
     * Linked List II), with a growing segment size and a per-segment reverse-or-skip decision.
     *
     * Key Insight: decide on the group's ACTUAL length, not its planned one. Only the last group
     * can be short, and its parity can flip either way: planned 4 -> actual 3 must NOT reverse,
     * planned 3 -> actual 2 MUST. Count the nodes once up front and track how many remain;
     * min(planned, remaining) is the real length of every group.
     *
     * Gotchas:
     * - The first version tested the planned length's parity: wrong only on the last group
     *   (1,1,0,6,5 stayed unreversed; 1..9 reversed its 3-node tail).
     * - After reversing, `previous` must move to the reversed group's TAIL (the old first node).
     *   Leaving it before the group was hidden while planned parities alternate, but breaks
     *   once two reversed groups are adjacent (1..14: full group 4, then a 4-node short group 5).
     * - reverse() must reattach the old first node to the node after the group (head.next = curr).
     * - Group 1 never reverses, so `previous` is set before the first reversal needs it.
     *
     * Complexity: O(n) time - one counting pass, then each node is reversed and walked a
     * constant number of times. O(1) space.
     *
     * Template:
     *   remaining = count(head); prev = null; curr = head; len = 1
     *   while curr != null:
     *       actual = min(len, remaining)
     *       if actual is even: tail = prev.next; prev.next = reverse(prev.next, actual); prev = tail
     *       else: advance prev by actual
     *       curr = prev.next; remaining -= actual; len++
     */

    // Reads a list back as [a,b,c].
    private static String show(ListNode head) {
        StringBuilder sb = new StringBuilder("[");
        for (ListNode curr = head; curr != null; curr = curr.next)
            sb.append(curr.val).append(curr.next == null ? "" : ",");
        return sb.append("]").toString();
    }

    public static void main(String[] args) {
        ReverseNodesInEvenLengthGroups R = new ReverseNodesInEvenLengthGroups();

        System.out.println("Test 1: " + show(R.reverseEvenLengthGroups(ListNode.createList(new int[]{5, 2, 6, 3, 9, 1, 7, 3, 8, 4}))) + " (Expected: [5,6,2,3,9,1,4,8,3,7])");
        System.out.println("Test 2: " + show(R.reverseEvenLengthGroups(ListNode.createList(new int[]{1, 1, 0, 6}))) + " (Expected: [1,0,1,6])");          // short last group of length 1: odd, stays
        System.out.println("Test 3: " + show(R.reverseEvenLengthGroups(ListNode.createList(new int[]{1, 1, 0, 6, 5}))) + " (Expected: [1,0,1,5,6])");     // last group meant to be 3, actually 2: reversed
        System.out.println("Test 4: " + show(R.reverseEvenLengthGroups(ListNode.createList(new int[]{1}))) + " (Expected: [1])");                         // single node
        System.out.println("Test 5: " + show(R.reverseEvenLengthGroups(ListNode.createList(new int[]{1, 2}))) + " (Expected: [1,2])");                    // group 2 has only 1 node: odd, no reversal
        System.out.println("Test 6: " + show(R.reverseEvenLengthGroups(ListNode.createList(new int[]{1, 2, 3}))) + " (Expected: [1,3,2])");               // list ends exactly at a reversed group
        System.out.println("Test 7: " + show(R.reverseEvenLengthGroups(ListNode.createList(new int[]{1, 2, 3, 4, 5, 6, 7, 8}))) + " (Expected: [1,3,2,4,5,6,8,7])");          // meant 4, actually 2: reversed
        System.out.println("Test 8: " + show(R.reverseEvenLengthGroups(ListNode.createList(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}))) + " (Expected: [1,3,2,4,5,6,7,8,9])");     // meant 4 (even), actually 3: NOT reversed
        System.out.println("Test 9: " + show(R.reverseEvenLengthGroups(ListNode.createList(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}))) + " (Expected: [1,3,2,4,5,6,10,9,8,7,11,12,13,14,15])");   // five full groups: reversed groups reconnect on both sides
        System.out.println("Test 10: " + show(R.reverseEvenLengthGroups(ListNode.createList(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14}))) + " (Expected: [1,3,2,4,5,6,10,9,8,7,14,13,12,11])");   // meant 5 (odd), actually 4: reversed
    }
}
