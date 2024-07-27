package medium;

import common.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PathSumII {
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null)
            return result;
        LinkedList<Integer> current = new LinkedList<>();
        findPaths(root, targetSum, current, result, 0);
        return result;
    }

    private void findPaths(TreeNode root, int targetSum, LinkedList<Integer> current, List<List<Integer>> result, int sum) {
        if (root == null)
            return;
        sum += root.val;
        current.addLast(root.val);
        if (root.left == null && root.right == null && sum == targetSum)
            result.add(new ArrayList<>(current));
        findPaths(root.left, targetSum, current, result, sum);
        findPaths(root.right, targetSum, current, result, sum);
        sum -= root.val;
        current.removeLast();
    }
}
