package medium;

import common.ListNode;

import java.util.HashSet;

public class DeleteNodesFromLinkedListPresentInArray {
    public ListNode modifiedList(int[] nums, ListNode head) {
        HashSet<Integer> prohibited = new HashSet<>();
        for (int num : nums)
            prohibited.add(num);
        ListNode current = head;
        ListNode prev = null;
        while (current != null) {
            if (prohibited.contains(current.val)) {
                if (prev == null) {
                    head = current.next;
                } else {
                    prev.next = current.next;
                }
            } else {
                prev = current;
            }
            current = current.next;
        }
        return head;
    }

    public ListNode modifiedListV2(int[] nums, ListNode head) {
        int N = nums.length;
        boolean[] prohibited = new boolean[100002];
        for (int i = 0; i < N; i++)
            prohibited[nums[i]] = true;
        ListNode current = head;
        ListNode prev = null;
        while (current != null) {
            if (prohibited[current.val]) {
                if (prev == null) {
                    head = current.next;
                } else {
                    prev.next = current.next;
                }
            } else {
                prev = current;
            }
            current = current.next;
        }
        return head;
    }
}
