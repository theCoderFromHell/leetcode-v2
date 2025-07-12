package medium;

import common.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class UniqueBinarySearchTreesII {
    public List<TreeNode> generateTrees(int n) {
        return generateTrees(1, n);
    }

    private List<TreeNode> generateTrees(int start, int end) {
        if (start > end)
            return List.of(new TreeNode(-1));
        if (start == end)
            return List.of(new TreeNode(start));
        if (start + 1 == end) {
            TreeNode r1 = new TreeNode(start);
            r1.right = new TreeNode(end);
            TreeNode r2 = new TreeNode(end);
            r2.left = new TreeNode(start);
            return List.of(r1, r2);
        }
        List<TreeNode> result = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            List<TreeNode> leftNodes = generateTrees(start, i-1);
            List<TreeNode> rightNodes = generateTrees(i+1, end);
            for (TreeNode leftNode : leftNodes) {
                for (TreeNode rightNode : rightNodes) {
                    TreeNode root = new TreeNode(i);
                    root.left = leftNode.val == -1 ? null : leftNode;
                    root.right = rightNode.val == -1 ? null : rightNode;
                    result.add(root);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        UniqueBinarySearchTreesII U = new UniqueBinarySearchTreesII();
        System.out.println(U.generateTrees(3));
    }
}
