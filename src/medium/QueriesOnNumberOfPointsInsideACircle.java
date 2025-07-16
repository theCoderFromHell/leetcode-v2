package medium;

import java.util.Arrays;

public class QueriesOnNumberOfPointsInsideACircle {
    public int[] countPoints(int[][] points, int[][] queries) {
        int size = points.length;
        int qSize = queries.length;
        int[] result = new int[qSize];
        for (int i = 0; i < qSize; i++) {
            int a = queries[i][0];
            int b = queries[i][1];
            int r = queries[i][2];
            int count = 0;
            for (int j = 0; j < size; j++) {
                int a1 = points[j][0];
                int b1 = points[j][1];
                if ((a-a1) * (a-a1) + (b-b1 )* (b-b1) <= r*r)
                    count++;
            }
            result[i] = count;
        }
        return result;
    }

    public static void main(String[] args) {
        QueriesOnNumberOfPointsInsideACircle Q = new QueriesOnNumberOfPointsInsideACircle();
        System.out.println(Arrays.toString(Q.countPoints(new int[][]{{1, 3}, {3, 3}, {5, 3}, {2, 2}},
                new int[][]{{2, 3, 1}, {4, 3, 1}, {1, 1, 2}})));
        System.out.println(Arrays.toString(Q.countPoints(new int[][]{{1,1},{2,2},{3,3},{4,4},{5,5}},
                new int[][]{{1,2,2},{2,2,2},{4,3,2},{4,3,3}})));
    }
}
