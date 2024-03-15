package medium;

import common.TreeNode;

import java.util.*;
import java.util.stream.Collectors;

public class DeleteNodesAndReturnForest {
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        List<TreeNode> result = new ArrayList<>();
        HashSet<Integer> toDelete = new HashSet<>();
        for(int i : to_delete)
            toDelete.add(i);
        if (!toDelete.contains(root.val))
            result.add(root);
        delete(root, toDelete, result);
        return result;
    }

    private TreeNode delete(TreeNode root, HashSet<Integer> toDelete, List<TreeNode> result) {
        if (root == null)
            return null;
        root.left = delete(root.left, toDelete, result);
        root.right = delete(root.right, toDelete, result);
        if (toDelete.contains(root.val)) {
            if (root.left != null)
                result.add(root.left);
            if (root.right != null)
                result.add(root.right);
            return null;
        }
        return root;
    }
}
