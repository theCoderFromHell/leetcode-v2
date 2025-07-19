package medium;

import common.TreeNode;

public class ConvertBSTToGreaterTree {
    int currSum;
    public TreeNode convertBST(TreeNode root) {
        currSum = 0;
        updateTree(root);
        return root;
    }

    private void updateTree(TreeNode root) {
        if (root == null)
            return;
        updateTree(root.right);
        root.val += currSum;
        currSum = root.val;
        updateTree(root.left);
    }
}
