package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SatisfiabilityOfEqualityEquations {
    public boolean equationsPossible(String[] equations) {
        int size = equations.length;
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        int[] colours = new int[26];
        Arrays.fill(colours, -1);
        // build the graph
        for (int i = 0; i < size; i++) {
            if (equations[i].charAt(1) == '=') {
                int x = equations[i].charAt(0) - 'a';
                int y = equations[i].charAt(3) - 'a';
                List<Integer> xList = graph.getOrDefault(x, new ArrayList<>());
                xList.add(y);
                graph.put(x, xList);
                List<Integer> yList = graph.getOrDefault(y, new ArrayList<>());
                yList.add(x);
                graph.put(y, yList);
            }
        }

        // colour the graph
        for (int i = 0; i < 26; i++) {
            if (colours[i] == -1)
                dfs(i, i, graph, colours);
        }

        // check if an equation violates the graph
        for (int i = 0; i < size; i++) {
            if (equations[i].charAt(1) == '!') {
                int x = equations[i].charAt(0) - 'a';
                int y = equations[i].charAt(3) - 'a';
                if (colours[x] == colours[y])
                    return false;
            }
        }
        return true;
    }

    private void dfs(int node, int colour, HashMap<Integer, List<Integer>> graph, int[] colours) {
        if (colours[node] != -1)
            return;
        colours[node] = colour;
        if (graph.containsKey(node)) {
            List<Integer> neighbours = graph.get(node);
            for (Integer neighbour : neighbours)
                dfs(neighbour, colour, graph, colours);
        }
    }
}
