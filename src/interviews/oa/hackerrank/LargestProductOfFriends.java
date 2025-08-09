package interviews.oa.hackerrank;

import java.util.*;

public class LargestProductOfFriends {
    public int countCompanies(int friends_nodes, List<Integer> friends_from, List<Integer> friends_to, List<Integer> friends_weight) {
        // Step 1: Group edges by weight
        HashMap<Integer, List<int[]>> weightEdges = new HashMap<>();
        for (int i = 0; i < friends_weight.size(); i++) {
            int weight = friends_weight.get(i);
            weightEdges.computeIfAbsent(weight, k -> new ArrayList<>())
                    .add(new int[]{friends_from.get(i), friends_to.get(i)});
        }
        int maxSize = 0;
        int maxProduct = 0;
        // Step 2: Process each weight group
        for (List<int[]> edges : weightEdges.values()) {
            // Build adjacency list
            List<List<Integer>> adjList = new ArrayList<>();
            for (int i = 0; i <= friends_nodes; i++)
                adjList.add(new ArrayList<>());
            for (int[] edge : edges) {
                adjList.get(edge[0]).add(edge[1]);
                adjList.get(edge[1]).add(edge[0]);
            }
            boolean[] visited = new boolean[friends_nodes + 1];
            // Step 3: BFS to find components
            for (int i = 1; i <= friends_nodes; i++) {
                if (!visited[i] && !adjList.get(i).isEmpty()) {
                    List<Integer> component = new ArrayList<>();
                    Queue<Integer> queue = new LinkedList<>();
                    queue.add(i); visited[i] = true;
                    while (!queue.isEmpty()) {
                        int friend = queue.poll();
                        component.add(friend);
                        List<Integer> neighbours = adjList.get(friend);
                        for (int neighbour : neighbours) {
                            if (!visited[neighbour]) {
                                visited[neighbour] = true;
                                queue.add(neighbour);
                            }
                        }
                    }
                    // Step 4: Update max size and product
                    if (component.size() > maxSize) {
                        maxSize = component.size();
                        component.sort(Collections.reverseOrder());
                        int product = (component.size() >= 2) ? component.get(0) * component.get(1)
                                : component.get(0) * component.get(0);
                        maxProduct = product;
                    } else if (component.size() == maxSize) {
                        component.sort(Collections.reverseOrder());
                        int product = (component.size() >= 2) ? component.get(0) * component.get(1)
                                : component.get(0) * component.get(0);
                        if (product > maxProduct) maxProduct = product;
                    }
                }
            }
        }
        return maxProduct;
    }
}
/*
There are friends numbered from 1 to friends_nodes.
Two friends friends_from[i] and friends_to[i] are connected by an edge having weight friends_weight[i].
All the friends connected by the edges having same weight form a connected component.
Find the largest size connected component and return the largest product of two friend numbers within that group

Complete the method in Java

public int countCompanies(int friends_nodes, List<Integer> friends_from, List<Integer> friends_to, List<Integer> friends_weight) {}

 */