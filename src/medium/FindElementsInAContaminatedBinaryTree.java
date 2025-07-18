package medium;

import common.TreeNode;

import java.util.HashSet;

public class FindElementsInAContaminatedBinaryTree {
    public static void main(String[] args) {
        System.out.println("FindElementsInAContaminatedBinaryTree");
    }
}

class FindElements {
    private HashSet<Integer> values;

    public FindElements(TreeNode root) {
        this.values = new HashSet<>();
        recoverTree(root, 0);
    }

    private void recoverTree(TreeNode root, int value) {
        if (root == null)
            return;
        if (root.val == -1) {
            root.val = value;
            values.add(value);
        }
        recoverTree(root.left, value * 2 + 1);
        recoverTree(root.right, value * 2 + 2);
    }

    public boolean find(int target) {
        return values.contains(target);
    }
}
