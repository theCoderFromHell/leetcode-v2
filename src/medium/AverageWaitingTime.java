package medium;

// https://leetcode.com/problems/average-waiting-time/

public class AverageWaitingTime {
    public double averageWaitingTime(int[][] customers) {
        int size = customers.length;
        int nextStartTime = 0;
        long totalWaitTime = 0;
        for (int i = 0; i < size; i++) {
            int arrivalTime = customers[i][0];
            int preparationTime = customers[i][1];

            nextStartTime = Integer.max(nextStartTime, arrivalTime) + preparationTime;
            totalWaitTime += (nextStartTime - arrivalTime);
        }
        return (double) totalWaitTime / (double) size;
    }

    public static void main(String[] args) {
        AverageWaitingTime A = new AverageWaitingTime();
        System.out.println(A.averageWaitingTime(new int[][]{{1, 2}, {2, 5}, {4, 3}}));           // 5.0
        System.out.println(A.averageWaitingTime(new int[][]{{5, 2}, {5, 4}, {10, 3}, {20, 1}})); // 3.25
        System.out.println(A.averageWaitingTime(new int[][]{{1, 5}}));                           // 5.0 — single customer
        System.out.println(A.averageWaitingTime(new int[][]{{1, 1}, {10, 1}, {20, 1}}));         // 1.0 — chef always idle
    }
}
