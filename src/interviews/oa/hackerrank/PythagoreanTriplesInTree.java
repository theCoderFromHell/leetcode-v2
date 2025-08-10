package interviews.oa.hackerrank;

import java.util.*;

public class PythagoreanTriplesInTree {
    public int countPythagoreanTriples (
            int treeNodes,
            List<Integer> treeFrom,
            List<Integer> treeTo,
            int x, int y, int z) {

        // Build adjacency list
        HashMap<Integer,List<Integer>> adjList = new HashMap<>();
        for (int i = 0; i < treeNodes; ++i)
            adjList.put(i, new ArrayList<>());
        int size = treeFrom.size();
        for (int i = 0; i < size; ++i) {
            int src = treeFrom.get(i);
            int dest = treeTo.get(i);
            adjList.get(src).add(dest);
            adjList.get(dest).add(src);
        }

        // Compute distances from x, y, z
        int[] distanceFromX = bfs(x, treeNodes, adjList);
        int[] distanceFromY = bfs(y, treeNodes, adjList);
        int[] distanceFromZ = bfs(z, treeNodes, adjList);

        int count = 0;
        for (int node = 0; node < treeNodes; ++node) {
            int[] distance = {distanceFromX[node], distanceFromY[node], distanceFromZ[node]};
            Arrays.sort(distance);
            if (distance[0] >= 1 && distance[1] >= 1 && distance[2] >= 1
                    && distance[0] * distance[0] + distance[1] * distance[1] == distance[2] * distance[2]) {
                count++;
            }
        }
        return count;
    }

    // Standard BFS for all nodes
    private int[] bfs(int source, int treeNodes, HashMap<Integer,List<Integer>> adjList) {
        int[] dist = new int[treeNodes];
        Arrays.fill(dist, -1);
        Queue<Integer> queue = new ArrayDeque<>();
        dist[source] = 0;
        queue.offer(source);
        while (!queue.isEmpty()) {
            int node = queue.poll();
            List<Integer> neighbours = adjList.get(node);
            for (int neighbour : neighbours) {
                if (dist[neighbour] == -1) {
                    dist[neighbour] = dist[node] + 1;
                    queue.offer(neighbour);
                }
            }
        }
        return dist;
    }
}
/*
Given a tree with tree_nodes number of nodes numbered from 0 to tree_nodes-1 where tree_nodes can be upto 10^5.
The distance between two nodes is the number of edges between them.
A node is special if its distance to three specific nodes x, y, and z when sorted in ascending order forms a pythagoras triplet.
Find the number of special nodes in the given tree.

Edges are given in the form of two lists List<Integer> treeFrom and List<Integer> treeTo ,
where for an index i treeFrom[i] has a undirectional edge towards treeTo[i]
 */
