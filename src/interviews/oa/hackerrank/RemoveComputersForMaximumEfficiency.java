package interviews.oa.hackerrank;

import java.util.ArrayList;
import java.util.List;

public class RemoveComputersForMaximumEfficiency {
    public long getMaximumEfficiency(int connectNodes,
                                     List<Integer> connectFrom,
                                     List<Integer> connectTo,
                                     List<Integer> computer_val,
                                     int k) {
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i <= connectNodes; i++)
            adjList.add(new ArrayList<>());

        for (int i = 0; i < connectFrom.size(); i++) {
            int src = connectFrom.get(i);
            int dest = connectTo.get(i);
            adjList.get(src).add(dest);
            adjList.get(dest).add(src);
        }
        long[] values = new long[connectNodes + 1];
        for (int i = 0; i < computer_val.size(); i++)
            values[i + 1] = computer_val.get(i);
        return dfs(1, -1, adjList, values, k);
    }

    private long dfs(int node, int parent, List<List<Integer>> adjList, long[] values, int k) {
        long subtreeValue = values[node];
        for (int child : adjList.get(node)) {
            if (child != parent) {
                long childBestValue = dfs(child, node, adjList, values, k);
                // For each child, we can either:
                // 1. Keep the child subtree (add its best value given by childBestValue)
                // 2. Remove the child subtree (subtract k, add 0 for the subtree)
                subtreeValue += Math.max(childBestValue, -k);
            }
        }
        return Math.max(-k, subtreeValue);
    }
}

/*
Given a network of n computer systems represented as a rooted tree (with the master computer 1 as the root),
the connections between computers are described using two arrays, connect_from,[] and connect_to[].
Each pair (connect_from[i], connect_to[i]) represents an undirected edge between two computers.
Each computer has a value assigned to it, represented by the array computer_val[].

To maximize the network's throughput, inefficient systems can be removed.
In one operation, any computer node and all nodes in its subtree, can be removed.
The number of such operations applied is denoted as num_ops.

For a given parameter k, the efficiency of the network after num_ops operations
is calculated as: (sum of values of all remaining computer nodes - k* num_ops).

Determine the maximum possible efficiency after applying some (possibly zero) operations optimally.

Note: The node values can be negative.

write a method in java
public long getMaximumEfficiency(int connectNodes, List<Integer> connectFrom, List<Integer> connectTo, List<Integer> computer_val, int k) {}
 */
