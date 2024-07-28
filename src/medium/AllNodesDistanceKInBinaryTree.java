package medium;

import common.TreeNode;

import java.util.*;

public class AllNodesDistanceKInBinaryTree {
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        if (k == 0)
            return List.of(target.val);
        HashMap<Integer, List<Integer>> adjList = new HashMap<>();
        createGraph(root, adjList);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(target.val);
        List<Integer> result = new ArrayList<>();
        int distance = 0;
        HashSet<Integer> visited = new HashSet<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            distance++;
            while (size-- > 0) {
                int node = queue.poll();
                visited.add(node);
                List<Integer> neighbors = adjList.getOrDefault(node, new ArrayList<>());
                for (int neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        queue.add(neighbor);
                        if (distance == k)
                            result.add(neighbor);
                    }
                }
            }
            if (distance == k)
                break;
        }
        return result;
    }

    private void createGraph(TreeNode root, HashMap<Integer, List<Integer>> adjList) {
        if (root == null)
            return;
        List<Integer> neighbors = adjList.getOrDefault(root.val, new ArrayList<>());
        if (root.left != null) {
            neighbors.add(root.left.val);
            List<Integer> neighborsOfLeft = adjList.getOrDefault(root.left.val, new ArrayList<>());
            neighborsOfLeft.add(root.val);
            adjList.put(root.left.val, neighborsOfLeft);
        }
        if (root.right != null) {
            neighbors.add(root.right.val);
            List<Integer> neighborsOfRight = adjList.getOrDefault(root.right.val, new ArrayList<>());
            neighborsOfRight.add(root.val);
            adjList.put(root.right.val, neighborsOfRight);
        }
        adjList.put(root.val, neighbors);
        createGraph(root.left, adjList);
        createGraph(root.right, adjList);
    }
}
