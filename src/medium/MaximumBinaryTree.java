package medium;

import common.TreeNode;

public class MaximumBinaryTree {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        int size = nums.length;
        return createTree(nums, 0, size-1);
    }

    private TreeNode createTree(int[] nums, int start, int end) {
        if (start > end)
            return null;
        if (start == end)
            return new TreeNode(nums[start]);
        int index = findMax(nums, start, end);
        TreeNode root = new TreeNode(nums[index]);
        root.left = createTree(nums, start, index - 1);
        root.right = createTree(nums, index + 1, end);
        return root;
    }

    private int findMax(int[] nums, int start, int end) {
        int index = start;
        for (int i = start; i <= end; i++) {
            if (nums[i] > nums[index])
                index = i;
        }
        return index;
    }
}
