package medium;

import common.TreeNode;

import java.util.HashMap;

public class CousinsInBinaryTreeII {
    public TreeNode replaceValueInTree(TreeNode root) {
        if (root == null)
            return root;
        if (root.left == null && root.right == null) {
            root.val = 0;
            return root;
        }
        HashMap<Integer, Integer> levelSum = new HashMap<>();
        HashMap<TreeNode, Integer> siblingSum = new HashMap<>();
        siblingSum.put(root, root.val);
        solve(root, levelSum, siblingSum, 0);
        updateTree(root, levelSum, siblingSum, 0);
        return root;
    }

    private void updateTree(TreeNode root, HashMap<Integer, Integer> levelSum, HashMap<TreeNode, Integer> siblingSum, int level) {
        if (root == null)
            return;
        int currentLevelSum = levelSum.get(level);
        int nodeSiblingSum = siblingSum.get(root);
        root.val = currentLevelSum - nodeSiblingSum;
        updateTree(root.left, levelSum, siblingSum, level+1);
        updateTree(root.right, levelSum, siblingSum, level+1);
    }

    private void solve(TreeNode root, HashMap<Integer, Integer> levelSum, HashMap<TreeNode, Integer> siblingSum, int level) {
        if (root == null)
            return;
        int sum = (root.left == null ? 0 : root.left.val) + (root.right == null ? 0 : root.right.val);
        if (root.left != null)
            siblingSum.put(root.left, sum);
        if (root.right != null)
            siblingSum.put(root.right, sum);
        levelSum.put(level, levelSum.getOrDefault(level, 0) + root.val);
        solve(root.left, levelSum, siblingSum, level+1);
        solve(root.right, levelSum, siblingSum, level+1);
    }

    public static void main(String[] args) {
        CousinsInBinaryTreeII C = new CousinsInBinaryTreeII();

        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(9);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(10);
        root.right.right = new TreeNode(7);

        System.out.println(C.replaceValueInTree(root));
    }
}
