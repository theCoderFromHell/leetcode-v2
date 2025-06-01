package interviews.oa.hackerrank;

import java.util.ArrayList;
import java.util.List;

public class BinaryTreeMaxSumDownwardPath {
    private int maxSum = Integer.MIN_VALUE;

    public int maxDownwardPathSum(TreeNode root) {
        if (root == null)
            return 0;
        maxSum = Integer.MIN_VALUE;
        dfs(root);
        return maxSum;
    }

    private int dfs(TreeNode node) {
        if (node == null)
            return 0;
        int bestChildPath = 0;
        for (TreeNode child : node.children)
            bestChildPath = Math.max(bestChildPath, dfs(child));
        int currentMaxDown = Math.max(node.val, node.val + bestChildPath);
        maxSum = Math.max(maxSum, currentMaxDown);
        return currentMaxDown;
    }

    class TreeNode {
        int val;
        List<TreeNode> children;

        TreeNode(int val) {
            this.val = val;
            this.children = new ArrayList<>();
        }

        TreeNode(int val, List<TreeNode> children) {
            this.val = val;
            this.children = children != null ? children : new ArrayList<>();
        }
    }
}
