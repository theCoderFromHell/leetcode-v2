package medium;

import java.util.*;

public class TheEarliestMomentWhenEveryoneBecomeFriends {
    public static int earliestAcq(int[][] logs, int n) {
        Arrays.sort(logs, Comparator.comparing(o -> o[0]));
        UnionFind uf = new UnionFind(n);
        int total = n;
        for (int[] log : logs) {
            if (uf.union(log[1], log[2]))
                total--;
            if (total == 1)
                return log[0];
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(earliestAcq(new int[][]{{5,4,3},{2,0,4},{1,1,2},{0,0,2},{9,1,3},{3,1,4},{8,2,4},{6,1,0}}, 5));
    }
}

class UnionFind {
    int[] parent;
    int[] rank;
    int size;

    public UnionFind(int size) {
        this.size = size;
        this.parent = new int[size];
        this.rank = new int[size];
        for (int i = 0; i < size; i++)
            parent[i] = i;
    }

    public int find(int node) {
        if (parent[node] != node) {
            parent[node] = find(parent[node]);
        }
        return parent[node];
    }

    public boolean union(int a, int b) {
        int parentA = find(a);
        int parentB = find(b);
        if(parentA == parentB)
            return false;
        if(rank[parentA] < rank[parentB]) {
            parent[parentA] = parentB;
            rank[parentB] += 1;
        }
        else if(rank[parentA] > rank[parentB]) {
            parent[parentB] = parentA;
            rank[parentA] += 1;
        }
        else {
            parent[parentA] = parentB;
            rank[parentB] += 1;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UnionFind{" +
                "parent=" + Arrays.toString(parent) +
                ", rank=" + Arrays.toString(rank) +
                ", size=" + size +
                '}';
    }
}
