package medium;

import common.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ParallelCourses {
    public static int minimumSemesters(int n, int[][] relations) {
        List<List<Integer>> graph = new ArrayList<>(n+1);
        for (int i = 0; i <= n ; i++)
            graph.add(new ArrayList<>());
        int[] dependencyCount = new int[n+1];
        for (int[] relation : relations) {
            graph.get(relation[0]).add(relation[1]);
            dependencyCount[relation[1]]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        Queue<Integer> nextQueue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (dependencyCount[i] == 0)
                queue.add(i);
        }
        int steps = 0;
        while (!queue.isEmpty()) {
            Integer node = queue.poll();
            n--;
            for (Integer neighbour : graph.get(node)) {
                dependencyCount[neighbour]--;
                if (dependencyCount[neighbour] == 0)
                    nextQueue.add(neighbour);
            }
            if (queue.isEmpty()) {
                steps++;
                queue.addAll(nextQueue);
                nextQueue = new LinkedList<>();
            }
        }
        return (n == 0) ? steps : -1;
    }

    public static void main(String[] args) {
        System.out.println(minimumSemesters(3, new int[][]{{1,3},{2,3}}));
        System.out.println(minimumSemesters(3, new int[][]{{1,2},{2,3},{3,1}}));
    }
}
