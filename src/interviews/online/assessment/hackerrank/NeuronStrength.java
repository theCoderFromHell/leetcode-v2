package interviews.online.assessment.hackerrank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NeuronStrength {
    public List<Integer> getNeuronStrengths(int neuronNodes,
                                            List<Integer> neuronFrom,
                                            List<Integer> neuronTo,
                                            List<Integer> strongConnectivity) {
        int[] weight = new int[neuronNodes];
        for (int i = 0; i < neuronNodes; i++)
            weight[i] = strongConnectivity.get(i) == 1 ? 1 : -1;

        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < neuronNodes; i++)
            adjList.add(new ArrayList<>());
        for (int i = 0; i < neuronFrom.size(); i++) {
            int source = neuronFrom.get(i) - 1; // 0-based
            int destination = neuronTo.get(i) - 1;
            adjList.get(source).add(destination);
            adjList.get(destination).add(source);
        }

        int[] dpDown = new int[neuronNodes];
        int[] dpUp = new int[neuronNodes];
        int[] result = new int[neuronNodes];

        // Step 3: First DFS (post-order)
        dfsDown(0, -1, adjList, weight, dpDown);

        // Step 4: Second DFS (rerooting)
        dfsUp(0, -1, adjList, dpDown, dpUp, result);

        List<Integer> ans = new ArrayList<>();
        for (int val : result)
            ans.add(val);
        return ans;
    }

    private void dfsDown(int u, int parent, List<List<Integer>> graph, int[] weight, int[] dpDown) {
        dpDown[u] = weight[u];
        for (int v : graph.get(u)) {
            if (v == parent) continue;
            dfsDown(v, u, graph, weight, dpDown);
            dpDown[u] += Math.max(0, dpDown[v]);
        }
    }

    private void dfsUp(int u, int parent, List<List<Integer>> graph, int[] dpDown, int[] dpUp, int[] result) {
        result[u] = dpDown[u] + dpUp[u];
        for (int v : graph.get(u)) {
            if (v == parent)
                continue;
            // Exclude v’s contribution when rerooting
            int withoutV = dpDown[u] - Math.max(0, dpDown[v]);
            dpUp[v] = Math.max(0, dpUp[u] + withoutV);
            dfsUp(v, u, graph, dpDown, dpUp, result);
        }
    }

    public static void main(String[] args) {
        NeuronStrength N = new NeuronStrength();
        System.out.println(N.getNeuronStrengths(4,
                Arrays.asList(1, 1, 1),
                Arrays.asList(2, 3, 4),
                Arrays.asList(0, 0, 1, 0)));
        // Expected: [0, -1, 1, -1]
    }
}

/*
A neural network has n neurons numbered from 1 to n.
If the neuron has strong connectivity, strongConnectivity[i] = 1.
If it has weak connectivity, strongConnectivity[i] = 0.

The neurons form a tree-like network with n-1 connections,
where the ith connection connects neurons neuronFrom[i] and neuronTo[i].
A neuron's strength is defined as the maximum difference between strongly connected and weakly connected neurons
in any subnetwork including that neuron.

Return an array of n integers, where the ith integer represents the strength of neuron i.

Note: A subnetwork is a connected subgraph of the given network.

1 < n < 2*10⁵

Example:

n=4
neuronFrom=[1, 1, 1]
neuronTo= [2, 3, 4]
strongConnectivity= [0, 0, 1, 0]

→ Only Neuron 3 has strong connectivity.
→ For neuron 1's strength, consider the subnetwork with neurons 1 and 3: 1 strong, 1 weak → strength = 0
→ For neuron 2's strength, consider the subnetwork with neurons 1, 2, and 3: 1 strong, 2 weak → strength =-1
→ For neuron 3's strength, consider the subnetwork with only neuron 3: 1 strong, 0 weak → strength = 1
→ For neuron 4's strength, consider the subnetwork with only neuron 4: 0 strong weak → strength = -1

The neuronStrengths array is [0, -1, 1, -1].

Write an efficient method in Java

public List<Integer> getNeuronStrenhths(int neuronNodes,
                                        List<Integer> neuronFrom,
                                        List<Integer> neuronTo,
                                        List<Integer> strongConnectivity) {}
 */
