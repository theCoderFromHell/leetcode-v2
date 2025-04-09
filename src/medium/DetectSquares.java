package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DetectSquares {
    private HashMap<Integer, List<Integer>> pointsByX;
    private HashMap<Integer, List<Integer>> pointsByY;
    private HashMap<Point, Integer> pointCount;

    public DetectSquares() {
        this.pointsByX = new HashMap<>();
        this.pointsByY = new HashMap<>();
        this.pointCount = new HashMap<>();
    }

    public void add(int[] point) {
        List<Integer> yPoints = pointsByX.getOrDefault(point[0], new ArrayList<>());
        yPoints.add(point[1]);
        pointsByX.put(point[0], yPoints);

        List<Integer> xPoints = pointsByY.getOrDefault(point[1], new ArrayList<>());
        xPoints.add(point[0]);
        pointsByY.put(point[1], xPoints);
        Point newPoint = new Point(point[0], point[1]);
        pointCount.put(newPoint, pointCount.getOrDefault(newPoint, 0) + 1);
    }

    public int count(int[] point) {
        int x = point[0];
        int y = point[1];
        List<Integer> xPoints = pointsByY.getOrDefault(y, null);
        if (xPoints == null || xPoints.isEmpty())
            return 0;
        int count = 0;
        for (int xPoint : xPoints) {
            int dist = Math.abs(xPoint - x);
            if (dist == 0)
                continue;
            int down = y - dist;
            int up = y + dist;
            List<Integer> yPoints = pointsByX.getOrDefault(x, null);
            if (yPoints == null || yPoints.isEmpty())
                return 0;
            for (int yPoint : yPoints) {
                if (yPoint == up || yPoint == down)
                    count += pointCount.getOrDefault(new Point(xPoint, yPoint), 0);
            }
        }
        return count;
    }

    class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public static void main(String[] args) {
        DetectSquares D = new DetectSquares();
        D.add(new int[]{3,10});
        D.add(new int[]{11,2});
        D.add(new int[]{3,2});
        System.out.println(D.count(new int[]{11,10}));
        System.out.println(D.count(new int[]{14,8}));
        D.add(new int[]{11,2});
        System.out.println(D.count(new int[]{11,10}));
    }
}
