package medium;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class LineReflection {
    public boolean isReflected(int[][] points) {
        int N = points.length;
        int minimumX = Integer.MAX_VALUE;
        int maximumX = Integer.MIN_VALUE;
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < N; i++) {
            minimumX = Math.min(minimumX, points[i][0]);
            maximumX = Math.max(maximumX, points[i][0]);
            set.add(points[i][0] + "-" + points[i][1]);
        }
        int sum = minimumX + maximumX;
        for (int i = 0; i < N; i++) {
            int reflectionX = sum - points[i][0];
            if (!set.contains(reflectionX + "-" + points[i][1]))
                return false;
        }
        return true;
    }
}
