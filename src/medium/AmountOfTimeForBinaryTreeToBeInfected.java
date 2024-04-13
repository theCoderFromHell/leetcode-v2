package medium;

import common.TreeNode;

import java.util.*;

public class AmountOfTimeForBinaryTreeToBeInfected {
    TreeNode startNode = null;
    public int amountOfTime(TreeNode root, int start) {
        HashMap<Integer, List<TreeNode>> adj = new HashMap<>();
        createAdjList(root, adj, start);
        Queue<TreeNode> queue = new LinkedList<>();
        HashSet<Integer> visited = new HashSet<>();
        queue.add(startNode);
        visited.add(startNode.val);
        int time = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode node = queue.poll();
                List<TreeNode> neighbors = adj.getOrDefault(node.val, new ArrayList<>());
                for (TreeNode t : neighbors) {
                    if (!visited.contains(t.val)) {
                        queue.add(t);
                        visited.add(t.val);
                    }
                }
            }
            if (!queue.isEmpty())
                time++;
        }
        return time;
    }

    private void createAdjList(TreeNode root, HashMap<Integer, List<TreeNode>> adj, int start) {
        if (root == null)
            return;
        if (root.val == start)
            this.startNode = root;
        List<TreeNode> neighbors = adj.getOrDefault(root.val, new ArrayList<>());
        if (root.left != null) {
            neighbors.add(root.left);
            List<TreeNode> neighborsOfLeft = adj.getOrDefault(root.left.val, new ArrayList<>());
            neighborsOfLeft.add(root);
            adj.put(root.left.val, neighborsOfLeft);
        }
        if (root.right != null) {
            neighbors.add(root.right);
            List<TreeNode> neighborsOfRight = adj.getOrDefault(root.right.val, new ArrayList<>());
            neighborsOfRight.add(root);
            adj.put(root.right.val, neighborsOfRight);
        }
        adj.put(root.val, neighbors);
        createAdjList(root.left, adj, start);
        createAdjList(root.right, adj, start);
    }

    public static void main(String[] args) {
        AmountOfTimeForBinaryTreeToBeInfected a = new AmountOfTimeForBinaryTreeToBeInfected();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(5);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.left.right.left = new TreeNode(9);
        root.left.right.right = new TreeNode(2);
        root.right.left = new TreeNode(10);
        root.right.right = new TreeNode(6);
        System.out.println(a.amountOfTime(root, 3));
    }
}
