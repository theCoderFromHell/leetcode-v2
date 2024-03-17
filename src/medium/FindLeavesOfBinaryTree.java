package medium;

import common.TreeNode;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FindLeavesOfBinaryTree {
    public List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null)
            return result;
        while (root != null) {
            List<Integer> current = new ArrayList<>();
            root = find(root, current);
            result.add(current);
        }
        return result;
    }

    private TreeNode find(TreeNode root, List<Integer> current) {
        boolean isLeaf = (root.left == null && root.right == null);
        if (root.left != null)
            root.left = find(root.left, current);
        if (root.right != null)
            root.right = find(root.right, current);
        if (isLeaf) {
            current.add(root.val);
            return null;
        }
        return root;
    }
}
