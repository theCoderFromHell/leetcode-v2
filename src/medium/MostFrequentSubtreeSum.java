package medium;

import common.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// https://leetcode.com/problems/most-frequent-subtree-sum/
public class MostFrequentSubtreeSum {
    int highestFrequency = 0;
    public int[] findFrequentTreeSum(TreeNode root) {
        highestFrequency = 0;
        HashMap<Integer,Integer> frequency = new HashMap<>();
        calculate(root, frequency);
        List<Integer> temp = new ArrayList<>();
        for (int value : frequency.keySet())
            if (frequency.get(value) == highestFrequency)
                temp.add(value);
        int size = temp.size();
        int[] result = new int[size];
        for (int i = 0; i < size; i++)
            result[i] = temp.get(i);
        return result;
    }

    private int calculate(TreeNode root, HashMap<Integer, Integer> frequency) {
        if (root == null)
            return 0;
        int left = calculate(root.left, frequency);
        int right = calculate(root.right, frequency);
        int subTreeSum = left + root.val + right;
        int newFrequency = frequency.getOrDefault(subTreeSum, 0) + 1;
        highestFrequency = Math.max(highestFrequency, newFrequency);
        frequency.put(subTreeSum, newFrequency);
        return subTreeSum;
    }

    /*
     * Revision Note — Most Frequent Subtree Sum (Medium)
     *
     * Pattern: Post-order DFS + frequency map
     *
     * Key Insight: Post-order (left → right → root) naturally computes each subtree
     * sum bottom-up; track max frequency inline during DFS to avoid a second pass.
     *
     * Gotchas:
     * - Reset instance field highestFrequency at the start of each call (safe for reuse)
     * - Subtree sums can be negative — HashMap handles negative keys fine
     * - Overflow: node values ±10^4, up to 10^4 nodes → max sum ±10^8, fits in int
     *
     * Template:
     *   int dfs(node, freqMap):
     *       if null: return 0
     *       sum = dfs(left) + node.val + dfs(right)
     *       freqMap.merge(sum, 1, Integer::sum)
     *       maxFreq = max(maxFreq, freqMap.get(sum))
     *       return sum
     */
    public static void main(String[] args) {
        MostFrequentSubtreeSum M = new MostFrequentSubtreeSum();

        // [2, -3, 4] — sums: 2, -3, 4, -1(root's subtree), each freq 1 → all tied... actually let's trace
        TreeNode root1 = new TreeNode(5);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(-3);
        System.out.println(java.util.Arrays.toString(M.findFrequentTreeSum(root1))); // [2,-3,4]

        TreeNode root2 = new TreeNode(5);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(-5);
        System.out.println(java.util.Arrays.toString(M.findFrequentTreeSum(root2))); // [2]

        // single node
        System.out.println(java.util.Arrays.toString(M.findFrequentTreeSum(new TreeNode(1)))); // [1]

        // null root
        System.out.println(java.util.Arrays.toString(M.findFrequentTreeSum(null))); // []
    }
}
