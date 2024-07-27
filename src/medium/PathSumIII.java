package medium;

import common.TreeNode;

import java.util.HashMap;
import java.util.HashSet;

public class PathSumIII {
    Integer count = 0;
    public int pathSum(TreeNode root, int targetSum) {
        if (root == null)
            return 0;
        HashMap<Long,Integer> sumsInThePath = new HashMap<>();
        sumsInThePath.put(0L, 1);
        findPaths(root, targetSum, 0, sumsInThePath);
        return count;
    }

    private void findPaths(TreeNode root, int targetSum, long sum, HashMap<Long,Integer> sumsInThePath) {
        if (root == null)
            return;
        long sumHere = sum + root.val;
        count += (sumsInThePath.getOrDefault(sumHere - targetSum, 0));
        sumsInThePath.put(sumHere, sumsInThePath.getOrDefault(sumHere, 0) + 1);
        findPaths(root.left, targetSum, sumHere, sumsInThePath);
        findPaths(root.right, targetSum, sumHere, sumsInThePath);
        sumsInThePath.put(sumHere, sumsInThePath.getOrDefault(sumHere, 0) - 1);
        if (sumsInThePath.get(sumHere) == 0)
            sumsInThePath.remove(sumHere);
    }
}
