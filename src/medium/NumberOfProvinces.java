package medium;

import java.util.HashSet;

public class NumberOfProvinces {
    public static int findCircleNum(int[][] isConnected) {
        int N = isConnected.length;
        int[] parent = new int[N];
        int[] rank = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
        }
        int result = N;
        for (int i = 0; i <N; i++)
            for (int j = i+1; j < N; j++)
                if (isConnected[i][j] == 1 && find(i, parent) != find(j, parent)) {
                    result--;
                    union(i, j, parent, rank);
                }
        return result;
    }

    private static int find(int node, int[] parent) {
        if (parent[node] != node)
            return parent[node] = find(parent[node], parent);
        return parent[node];
    }
    private static void union(int node1, int node2, int[] parent, int[] rank) {
        int parent1 = find(node1, parent);
        int parent2 = find(node2, parent);
        if (parent1 == parent2)
            return;
        if (rank[parent1] >= rank[parent2]) {
            parent[parent2] = parent1;
            rank[parent1] += rank[parent2];
        }
        else if (rank[parent1] < rank[parent2]) {
            parent[parent1] = parent2;
            rank[parent2] += rank[parent1];
        }
    }

    public static void main(String[] args) {
        System.out.println(findCircleNum(new int[][]{{1,1,0,0,0,0,0,1,0,0,0,0,0,0,0},{1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,1,0,1,1,0,0,0,0,0,0,0,0},{0,0,0,0,1,0,0,0,0,1,1,0,0,0,0},{0,0,0,1,0,1,0,0,0,0,1,0,0,0,0},{0,0,0,1,0,0,1,0,1,0,0,0,0,1,0},{1,0,0,0,0,0,0,1,1,0,0,0,0,0,0},{0,0,0,0,0,0,1,1,1,0,0,0,0,1,0},{0,0,0,0,1,0,0,0,0,1,0,1,0,0,1},{0,0,0,0,1,1,0,0,0,0,1,1,0,0,0},{0,0,0,0,0,0,0,0,0,1,1,1,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},{0,0,0,0,0,0,1,0,1,0,0,0,0,1,0},{0,0,0,0,0,0,0,0,0,1,0,0,0,0,1}}));
    }
}
