package medium;

import common.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LeafSimilarTrees {
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null)
            return true;
        if (root1 == null || root2 == null)
            return false;
        List<Integer> leaves1 = new ArrayList<>();
        List<Integer> leaves2 = new ArrayList<>();
        leavesOfTree(root1, leaves1);
        leavesOfTree(root2, leaves2);
        if (leaves1.size() != leaves2.size())
            return false;
        int N = leaves1.size();
        for (int i = 0; i < N; i++) {
            if (!Objects.equals(leaves1.get(i), leaves2.get(i)))
                return false;
        }
        return true;
    }

    private void leavesOfTree(TreeNode root, List<Integer> leaves) {
        if (root != null) {
            leavesOfTree(root.left, leaves);
            if (root.left == null && root.right == null)
                leaves.add(root.val);
            leavesOfTree(root.right, leaves);
        }
    }
}
