package medium;

import java.util.PriorityQueue;

public class SortTheStudentsByTheirKthScore {
    public int[][] sortTheStudents(int[][] score, int k) {
        int rows = score.length;
        int columns = score[0].length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o2[0] - o1[0]);
        for (int i = 0; i < rows; i++)
            pq.add(new int[]{score[i][k], i});
        int[][] result = new int[rows][columns];
        for (int i = 0; i < rows; i++)
            result[i] = score[pq.poll()[1]];
        return result;
    }
}
