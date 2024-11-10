package contests.biweekly_142;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindSubtreeSizesAfterChanges {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int N = parent.length;
        char[] values = s.toCharArray();
        List<List<Integer>> adjList = buildAdjList(parent, N);
        int[][] chasSeenForNode = new int[26][N];
        for (int k = 0; k < 26; k++)
            Arrays.fill(chasSeenForNode[k], -1);
        dfs(adjList, 0, chasSeenForNode, values);
        int[] newParent = new int[N];
        newParent[0] = -1;
        for (int i = 1; i < N; i++) {
            int charIndex = values[i] - 'a';
            int p = chasSeenForNode[charIndex][parent[i]];
            newParent[i] = (p == -1) ? parent[i] : p;
        }
        List<List<Integer>> updatedAdjList = buildAdjList(newParent, N);
        int[] subtreeSize = new int[N];
        updateSubtreeSize(updatedAdjList, 0, subtreeSize);
        return subtreeSize;
    }

    private void dfs(List<List<Integer>> adjList, int node, int[][] chasSeenForNode, char[] values) {
        int charIndex = values[node] - 'a';
        chasSeenForNode[charIndex][node] = node;
        for (int n : adjList.get(node)) {
            for (int k = 0; k < 26; k++)
                chasSeenForNode[k][n] = chasSeenForNode[k][node];
            dfs(adjList, n, chasSeenForNode, values);
        }
    }

    private int updateSubtreeSize(List<List<Integer>> adjList, int node, int[] subtreeSize) {
        int count = 1;
        for (int n : adjList.get(node))
            count += updateSubtreeSize(adjList, n, subtreeSize);
        subtreeSize[node] = count;
        return subtreeSize[node];
    }

    private List<List<Integer>> buildAdjList(int[] parent, int N) {
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < N; i++)
            adjList.add(new ArrayList<>());
        for (int i = 1; i < N; i++)
            adjList.get(parent[i]).add(i);
        return adjList;
    }

    public static void main(String[] args) {
        FindSubtreeSizesAfterChanges F = new FindSubtreeSizesAfterChanges();
        System.out.println(Arrays.toString(F.findSubtreeSizes(new int[]{-1, 0, 0, 1, 1, 1}, "abaabc")));
        System.out.println(Arrays.toString(F.findSubtreeSizes(new int[]{-1, 0, 4, 0, 1}, "abbba")));
        System.out.println(Arrays.toString(F.findSubtreeSizes(new int[]{-1, 10, 0, 12, 10, 18, 11, 12, 2, 3, 2, 2, 2, 0, 4, 11, 4, 2, 0}, "babadabbdabcbaceeda")));

    }
}
