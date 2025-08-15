package medium;

import common.TreeNode;

import java.util.HashMap;

public class FindBottomLeftTreeValue {
    int maxLevel;
    public int findBottomLeftValue(TreeNode root) {
        if (root == null)
            return -1;
        maxLevel = 0;
        HashMap<Integer,Integer> firstNodeAtLevel = new HashMap<>();
        find(root, 0, firstNodeAtLevel);
        return firstNodeAtLevel.get(maxLevel);
    }

    private void find(TreeNode root, int level, HashMap<Integer, Integer> firstNodeAtLevel) {
        if (root == null)
            return;
        maxLevel = Math.max(maxLevel, level);
        if (!firstNodeAtLevel.containsKey(level))
            firstNodeAtLevel.put(level, root.val);
        find(root.left, level + 1, firstNodeAtLevel);
        find(root.right, level + 1, firstNodeAtLevel);
    }
}
