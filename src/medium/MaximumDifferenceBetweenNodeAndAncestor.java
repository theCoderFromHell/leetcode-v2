package medium;

import common.TreeNode;

import java.util.TreeMap;

public class MaximumDifferenceBetweenNodeAndAncestor {
    int maxDiff;
    public int maxAncestorDiff(TreeNode root) {
        TreeMap<Integer,Integer> map = new TreeMap<>();
        maxDiff = 0;
        findMaxDiff(root, map);
        return maxDiff;
    }

    private void findMaxDiff(TreeNode root, TreeMap<Integer,Integer> map) {
        if (root == null)
            return;
        map.put(root.val, map.getOrDefault(root.val, 0) + 1);
        maxDiff = Math.max(maxDiff, Math.abs(map.lastKey() - map.firstKey()));
        findMaxDiff(root.left, map);
        findMaxDiff(root.right, map);
        map.put(root.val, map.getOrDefault(root.val, 0) - 1);
        if (map.get(root.val) == 0)
            map.remove(root.val);
    }

    public static void main(String[] args) {
        MaximumDifferenceBetweenNodeAndAncestor M =  new MaximumDifferenceBetweenNodeAndAncestor();

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(1);
        root.right = new TreeNode(8);
        System.out.println(M.maxAncestorDiff(root));
    }
}
