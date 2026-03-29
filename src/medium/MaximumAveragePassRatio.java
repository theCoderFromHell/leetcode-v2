package medium;

import java.util.PriorityQueue;

public class MaximumAveragePassRatio {
    public double maxAverageRatio(int[][] classes, int extraStudents) {
        int size = classes.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> {
            double gain1 = (double) (o1[0] - o1[1]) / ((double)o1[1] * (o1[1] + 1));
            double gain2 = (double) (o2[0] - o2[1]) / ((double)o2[1] * (o2[1] + 1));
            return Double.compare(gain1, gain2);
        });
        for (int i = 0; i < size; i++)
            pq.add(classes[i]);
        while (extraStudents > 0) {
            int[] top = pq.poll();
            top[0]++;
            top[1]++;
            pq.add(top);
            extraStudents--;
        }
        double finalRatio = 0.0;
        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            finalRatio += (double) top[0] /top[1];
        }
        return finalRatio/size;
    }

    public static void main(String[] args) {
        MaximumAveragePassRatio M = new MaximumAveragePassRatio();
        System.out.println(M.maxAverageRatio(new int[][]{{1, 2}, {3, 5}, {2, 2}}, 2));          // 0.78333
        System.out.println(M.maxAverageRatio(new int[][]{{2, 4}, {3, 9}, {4, 5}, {2, 10}}, 4)); // 0.53485
        System.out.println(M.maxAverageRatio(new int[][]{{1, 2}}, 1));                           // 0.66667 — single class
        System.out.println(M.maxAverageRatio(new int[][]{{5, 5}, {5, 5}}, 3));                   // 1.0 — all passing
    }
}
