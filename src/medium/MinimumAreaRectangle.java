package medium;

import java.util.HashMap;
import java.util.HashSet;

public class MinimumAreaRectangle {
    public int minAreaRect(int[][] points) {
        int N = points.length;
        HashMap<Integer, HashSet<Integer>> pts = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int[] point = points[i];
            HashSet<Integer> yCoordinates = pts.getOrDefault(point[0], new HashSet<>());
            yCoordinates.add(point[1]);
            pts.put(point[0], yCoordinates);
        }
        int area = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            int[] pointA = points[i];
            for (int j = i+1; j < N; j++) {
                int[] pointB = points[j];
                if (pointA[0] == pointB[0] || pointA[1] ==pointB[1])
                    continue;
                if (pts.get(pointA[0]).contains(pointB[1]) && pts.get(pointB[0]).contains(pointA[1])) {
                    area = Math.min(Math.abs((pointA[0] - pointB[0]) *(pointA[1]-pointB[1])), area);
                }
            }
        }
        return (area == Integer.MAX_VALUE) ? 0 : area;
    }
}
