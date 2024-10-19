package medium;

import common.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FindDuplicateSubtrees {
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        List<TreeNode> result = new ArrayList<>();
        HashMap<String, Integer> subTreeToId = new HashMap<>();
        HashMap<Integer,Integer> idToCount = new HashMap<>();
        find(root, subTreeToId, idToCount, result);
        return result;
    }

    private int find(TreeNode root, HashMap<String, Integer> subTreeToId, HashMap<Integer, Integer> idToCount, List<TreeNode> result) {
        if (root == null)
            return 0;
        String subTree = find(root.left, subTreeToId, idToCount, result) + "-" + root.val + "-" + find(root.right, subTreeToId, idToCount, result);
        if (!subTreeToId.containsKey(subTree))
            subTreeToId.put(subTree, subTreeToId.size() + 1);
        int id = subTreeToId.get(subTree);
        idToCount.put(id, idToCount.getOrDefault(id, 0) + 1);
        if (idToCount.get(id) == 2)
            result.add(root);
        return id;
    }
}
