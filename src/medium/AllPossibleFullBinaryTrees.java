package medium;

import common.TreeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllPossibleFullBinaryTrees {
    public List<TreeNode> allPossibleFBT(int n) {
        HashMap<Integer, List<TreeNode>> dp = new HashMap<>();
        List<TreeNode> result = getTrees(n, dp);
        return result;
    }
    public List<TreeNode> getTrees(int n, HashMap<Integer, List<TreeNode>> dp) {
        if (n % 2 == 0)
            return new ArrayList<>();
        if (n == 1) {
            TreeNode root = new TreeNode(0);
            return List.of(root);
        }
        if (dp.get(n) != null)
            return dp.get(n);
        int left = 1;
        int right;
        List<TreeNode> result = new ArrayList<>();
        while (left <= n-1) {
            right = n-1-left;
            List<TreeNode> leftNodes = getTrees(left, dp);
            List<TreeNode> rightNodes = getTrees(right, dp);
            for (TreeNode l : leftNodes) {
                for (TreeNode r : rightNodes) {
                    TreeNode root = new TreeNode(0);
                    root.left = l;
                    root.right = r;
                    result.add(root);
                }
            }
            left += 2;
        }
        dp.put(n, result);
        return result;
    }

    public static void main(String[] args) {
        AllPossibleFullBinaryTrees A = new AllPossibleFullBinaryTrees();
        System.out.println(A.allPossibleFBT(3));
        System.out.println(A.allPossibleFBT(4));
        System.out.println(A.allPossibleFBT(7));
        System.out.println(A.allPossibleFBT(11));
        System.out.println(A.allPossibleFBT(9));
    }
}
