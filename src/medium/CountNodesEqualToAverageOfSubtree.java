package medium;

import common.TreeNode;

public class CountNodesEqualToAverageOfSubtree {
    public int averageOfSubtree(TreeNode root) {
        return find(root).count;
    }

    private NodeSum find(TreeNode root) {
        if (root == null)
            return new NodeSum(0, 0L, 0);
        NodeSum left = find(root.left);
        NodeSum right = find(root.right);
        NodeSum curr = new NodeSum(left.numberOfNodes + right.numberOfNodes + 1, left.sum + right.sum + root.val, left.count + right.count);
        if (root.val == curr.sum/curr.numberOfNodes)
            curr.count = curr.count + 1;
        return curr;
    }
}

class NodeSum {
    int numberOfNodes;
    long sum;
    int count;

    public NodeSum(int numberOfNodes, long sum, int count) {
        this.numberOfNodes = numberOfNodes;
        this.sum = sum;
        this.count = count;
    }

    @Override
    public String toString() {
        return "NodeSum{" +
                "numberOfNodes=" + numberOfNodes +
                ", sum=" + sum +
                ", count=" + count +
                '}';
    }
}
