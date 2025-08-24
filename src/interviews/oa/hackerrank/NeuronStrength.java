package interviews.oa.hackerrank;

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
