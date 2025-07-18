package medium;

import common.TreeNode;

import java.util.HashMap;
import java.util.HashSet;

public class CreateBinaryTreeFromDescriptions {
    public TreeNode createBinaryTree(int[][] descriptions) {
        int size = descriptions.length;
        HashMap<Integer, TreeNode> treeMap = new HashMap<>();
        HashSet<Integer> children = new HashSet<>();
        for (int i = 0; i < size; i++) {
            TreeNode parent = treeMap.getOrDefault(descriptions[i][0], new TreeNode(descriptions[i][0]));
            TreeNode child = treeMap.getOrDefault(descriptions[i][1], new TreeNode(descriptions[i][1]));
            children.add(descriptions[i][1]);
            if (descriptions[i][2] == 1)
                parent.left = child;
            else
                parent.right = child;
            treeMap.put(descriptions[i][0], parent);
            treeMap.put(descriptions[i][1], child);
        }
        for (int key : treeMap.keySet()) {
            if (!children.contains(key))
                return treeMap.get(key);
        }
        return null;
    }
}
