package medium;

import common.TreeNode;

public class BSTIterator {
    TreeNode listRoot = null;
    TreeNode prev = null;
    public BSTIterator(TreeNode root) {
        createList(root);
    }

    private void createList(TreeNode root) {
        if (root == null)
            return;
        createList(root.left);
        TreeNode current = new TreeNode(root.val);
        if (prev != null)
            prev.right = current;
        else
            listRoot = current;
        prev = current;
        createList(root.right);
    }

    public int next() {
        if (listRoot != null) {
            int value = listRoot.val;
            listRoot = listRoot.right;
            return value;
        }
        return -1;
    }

    public boolean hasNext() {
        return (listRoot != null);
    }
}
