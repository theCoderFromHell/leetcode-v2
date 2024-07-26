package medium;

import common.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BinaryTreeRightSideView {
    public List<Integer> rightSideView(TreeNode root) {
        if (root == null)
            return new ArrayList<>();
        if (root.left == null && root.right == null)
            return List.of(root.val);
        HashMap<Integer, TreeNode> levelWiseRightest = new HashMap<>();
        levelWiseRightest.put(0, root);
        searchRightNodes(root, levelWiseRightest, 0, 0);
        List<Integer> rightNodes = new ArrayList<>();
        for (Integer level : levelWiseRightest.keySet())
            rightNodes.add(levelWiseRightest.get(level).val);
        return rightNodes;
    }

    private void searchRightNodes(TreeNode root, HashMap<Integer, TreeNode> levelWiseRightest, int width, int level) {
        if (root == null)
            return;
        if (!levelWiseRightest.containsKey(level))
            levelWiseRightest.put(level, root);
        searchRightNodes(root.right, levelWiseRightest, width + 1, level + 1);
        searchRightNodes(root.left, levelWiseRightest, width - 1, level + 1);
    }
}
