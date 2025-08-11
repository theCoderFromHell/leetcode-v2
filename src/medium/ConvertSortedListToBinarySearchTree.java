package medium;

import common.ListNode;
import common.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class ConvertSortedListToBinarySearchTree {
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null)
            return null;
        List<Integer> values = toList(head);
        int size = values.size();
        return toTree(values, 0, size-1);
    }

    private TreeNode toTree(List<Integer> values, int start, int end) {
        if (start > end)
            return null;
        if (start == end)
            return new TreeNode(values.get(start));
        int mid = start + (end - start)/2;
        TreeNode root = new TreeNode(values.get(mid));
        root.left = toTree(values, start, mid - 1);
        root.right = toTree(values, mid + 1, end);
        return root;
    }

    private List<Integer> toList(ListNode head) {
        List<Integer> values = new ArrayList<>();
        while (head != null) {
            values.add(head.val);
            head = head.next;
        }
        return values;
    }
}
